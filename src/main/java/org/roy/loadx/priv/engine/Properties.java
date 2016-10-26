package org.roy.loadx.priv.engine;

public class Properties {
  public static boolean getWebserver() {
    return "true".equalsIgnoreCase(System.getProperty("webserver"));
  }
}