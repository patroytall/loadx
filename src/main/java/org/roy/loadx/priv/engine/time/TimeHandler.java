package org.roy.loadx.priv.engine.time;

public class TimeHandler {
  private final TimeProvider timeProvider;
  private final long constructionTimeNano;
  
  public TimeHandler(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
    constructionTimeNano = this.timeProvider.nanoTime();
  }
  
  public TimeProvider getTimeProvider() {
    return timeProvider;
  }
  
  /**
   * @return Time since the construction of this time handler in nanos.
   */
  public long getRelativeNanoTime() {
    return timeProvider.nanoTime() - constructionTimeNano;
  }
  
  public static double nanoTimeToMillis(long nanoTime) {
    return nanoTime / 1e6;
  }
}
