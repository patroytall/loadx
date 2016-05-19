package org.roy.loadx.priv;

public class Util {
	
	public static String findBoundaries(String leftBoundary, String rightBoundary, String str) {
		int leftPos = str.indexOf(leftBoundary);
		if (leftPos == -1) {
			throw new IllegalArgumentException(String.format("Left boundary %s does not exist in string: %s", leftBoundary, str));
		}

		int rightPos = str.indexOf(rightBoundary);
		if (rightPos == -1) {
			throw new IllegalArgumentException(String.format("Right boundary %s does not exist in string: %s", rightBoundary, str));
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
