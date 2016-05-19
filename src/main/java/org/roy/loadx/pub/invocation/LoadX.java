package org.roy.loadx.pub.invocation;

import org.roy.loadx.priv.engine.Engine;

/*
 * To use Eclipse TCP/IP Monitor: 
 * - configure local hosts file with entry like: 127.0.0.1 localhost.remotedomain.com 
 * - use host localhost.remotedomain.com:1234 as balancer URL in LoadX 
 * - configure monitor to listen on port 1234 and forward to host.remotedomain.com
 */
public class LoadX {
  public static void main(String[] args) {
    new Engine().run(args);
  }
}