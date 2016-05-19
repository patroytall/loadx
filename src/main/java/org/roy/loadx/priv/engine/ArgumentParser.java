package org.roy.loadx.priv.engine;

import com.beust.jcommander.Parameter;

public class ArgumentParser {
  @Parameter(names={"--web", "-w"}, description="Start web server")
  private boolean web;
  
  public boolean getWeb() {
    return web;
  }
}