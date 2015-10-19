package org.roy.loadx.invocation;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.roy.loadx.api.Job;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.job.JobImpl;

/**
 * To use Eclipse TCP/IP Monitor (domain set up required to support SSO): - configure /private/etc/hosts file with entry like: 127.0.0.1
 * localhost.qa01.cenx.localnet - use host localhost.qa01.cenx.localnet:1234 as balancer URL in LoadX - configure monitor to list on port 1234 and
 * forward to balancer01.qa01.cenx.localnet
 */
public class LoadX {
	public static void main(String[] args) {
		new LoadX().runJavaScript(args[0]);
	}

	/**
	 * Packages can be references in bulk using "withnew JavaImport(); with
	 */
	private void runJavaScript(String resourcePath) {

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("nashorn");

		String script;
		try {
			URL a = LoadX.class.getResource("/" + resourcePath);
			script = new String(Files.readAllBytes(Paths.get(a.toURI())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Job job = new JobImpl();

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

	private void runJob(Job job) {
		Scenario scenario = job.getScenario();
		scenario.start();
		scenario.stop();

	}
}