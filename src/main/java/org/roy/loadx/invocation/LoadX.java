package org.roy.loadx.invocation;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.job.JobImpl;
import org.roy.loadx.job.ScenarioRunner;

/**
 * To use Eclipse TCP/IP Monitor (domain set up required to support SSO): - configure /private/etc/hosts file with entry like: 127.0.0.1
 * localhost.qa01.cenx.localnet - use host localhost.qa01.cenx.localnet:1234 as balancer URL in LoadX - configure monitor to list on port 1234 and
 * forward to balancer01.qa01.cenx.localnet
 */
public class LoadX {
	public static void main(String[] args) {
		new LoadX().runJavaScript(args[0]);
	}

	private void runJavaScript(String resourcePath) {

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("nashorn");

		String script;
		try {
			URL a = LoadX.class.getResource("/" + resourcePath);
			if (a == null) {
				throw new RuntimeException("invalid resource path: " + resourcePath);
			}
			script = new String(Files.readAllBytes(Paths.get(a.toURI())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		JobImpl job = new JobImpl();

		try {
			Object global = engine.eval("this");
			Object jsObject = engine.eval("Object");

			Invocable invocable = (Invocable) engine;

			invocable.invokeMethod(jsObject, "bindProperties", global, job);

			engine.eval(script);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		runJob(job);
	}

	private void runJobIntialize(JobImpl jobImpl) {
		JobInitializer jobInitializer = jobImpl.getJobInitializer();
		if (jobInitializer != null) {
			jobInitializer.initialize();
		}
	}

	private void runJobTerminate(JobImpl jobImpl) {
		JobInitializer jobInitializer = jobImpl.getJobInitializer();
		if (jobInitializer != null) {
			jobInitializer.terminate();
		}
	}

	private void runJob(JobImpl jobImpl) {
		runJobIntialize(jobImpl);

		ExecutorService executorService = Executors.newFixedThreadPool(jobImpl.getScenarioUserCount());

		for (int i = 0; i < jobImpl.getScenarioUserCount(); ++i) {
			executorService.execute(new ScenarioRunner(jobImpl.getScenario(), jobImpl.getScenarioIterationCount()));
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		runJobTerminate(jobImpl);
	}
}