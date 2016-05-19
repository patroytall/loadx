package org.roy.loadx.priv.engine;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;
import spark.Spark;

public class WebServer {
  public WebServer() {
    staticFileLocation("/web");
    port(5555);
    get("/", (req, res) -> "Hello World");
  }
  
  public void stop() {
    Spark.stop();
  }
}