package org.roy.loadx.priv.engine;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import org.roy.loadx.priv.transaction.grapher.TransactionGrapher;

import spark.Spark;

public class WebServer {
  private final TransactionGrapher transactionGrapher;
  
  public WebServer(TransactionGrapher transactionGrapher) {
    this.transactionGrapher = transactionGrapher;
    
    staticFileLocation("/web");
    port(5555);
    get("/execution", (req, res) -> this.transactionGrapher.getJson());
  }
  
  public void stop() {
    Spark.stop();
  }
}