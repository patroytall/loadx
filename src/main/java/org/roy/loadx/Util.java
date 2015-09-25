package org.roy.loadx;

public class Util {
	public static String findBoundaries(String leftBoundary, String rightBoundary, String str) {
		int leftPos = str.indexOf(leftBoundary) + leftBoundary.length();
		int rightPos = str.indexOf(rightBoundary, leftPos);
		return str.substring(leftPos, rightPos);
	}
}
