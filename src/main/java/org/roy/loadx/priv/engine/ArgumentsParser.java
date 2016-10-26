package org.roy.loadx.priv.engine;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentsParser {
  private final CommandLine commandLine;
  
  public ArgumentsParser(String[] arguments) {
    Options options = new Options();
    options.addOption("w", false, "start web server");
    CommandLineParser parser = new DefaultParser();
    try {
      commandLine = parser.parse( options, arguments);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
  
  public boolean getWebServer() {
    return commandLine.hasOption("w");
  }
}