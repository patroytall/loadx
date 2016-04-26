package org.roy.loadx;

public class Util {
	
	public static String findBoundaries(String leftBoundary, String rightBoundary, String str) {
		int leftPos = str.indexOf(leftBoundary);
		int rightPos = str.indexOf(rightBoundary);

		if (leftPos == -1) {
			throw new IllegalArgumentException(String.format("Left boundary '%s' does not exist in source '%s'!", leftBoundary, str));
		}

		if (rightPos == -1) {
			throw new IllegalArgumentException(String.format("Right boundary '%s' does not exist in source '%s'!", rightBoundary, str));
		}

		return str.substring(leftPos + leftBoundary.length(), rightPos);
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
