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
    get("/execution", (req, res) -> getJson());
  }
  
  private String getJson() {
    try {
      return this.transactionGrapher.getJson();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  
  public void stop() {
    Spark.stop();
  }
}