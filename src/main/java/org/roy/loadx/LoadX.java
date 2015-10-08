package org.roy.loadx;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * To use Eclipse TCP/IP Monitor (domain set up required to support SSO): - configure /private/etc/hosts file with entry like: 127.0.0.1
 * localhost.qa01.cenx.localnet - use host localhost.qa01.cenx.localnet:1234 as balancer URL in LoadX - configure monitor to list on port 1234 and
 * forward to balancer01.qa01.cenx.localnet
 */
public class LoadX {
	private Test test;

	public static void main(String[] args) {
		new LoadX().runJavaScript();
	}

	private void runJavaScript() {

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("nashorn");

		String script;
		try {
			URL a = LoadX.class.getResource("/test.js");
			script = new String(Files.readAllBytes(Paths.get(a.toURI())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			Object global = engine.eval("this");
			Object jsObject = engine.eval("Object");

			Invocable invocable = (Invocable) engine;

			invocable.invokeMethod(jsObject, "bindProperties", global, this);

			String str = "" + "var imports = new JavaImporter();";
			str += "with(imports) {";
			str += script;
			str += "}";

			engine.eval(str);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		test.start();
		test.stop();
	}

	public void setTest(Test test) {
		this.test = test;
	}
}