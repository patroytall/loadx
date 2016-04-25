package org.roy.loadx;

public class Util {
	
	public static String findBoundaries(String leftBoundary, String rightBoundary, String str) {
		// no specifics given as to what to do in these cases, assuming an exception should be thrown
		if (str == null) {
			throw new IllegalArgumentException("Source string cannot be null!");
		} else if (leftBoundary == null) {
			throw new IllegalArgumentException("Left boundary cannot be null nor zero-length!");
		} else if (rightBoundary == null) {
			throw new IllegalArgumentException("Right boundary cannot be null nor zero-length!");
		} else {
			int leftPos = str.indexOf(leftBoundary);
			int rightPos = str.indexOf(rightBoundary);

			// no specifics given as to what happens in these cases, assuming an exception better than IOB is appropriate
			if (leftPos == -1) {
				throw new IllegalArgumentException(String.format("Left boundary '%s' does not exist in source '%s'!", leftBoundary, str));
			}
			
			if (rightPos == -1) {
				throw new IllegalArgumentException(String.format("Right boundary '%s' does not exist in source '%s'!", rightBoundary, str));
			}
			
			return str.substring(leftPos + leftBoundary.length(), rightPos);
		}
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
