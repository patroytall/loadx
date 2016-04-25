package org.roy.loadx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtil {

	static private final String LEFT_BOUNDARY = "ABC";
	static private final String RIGHT_BOUNDARY = "XYZ";
	
	@Test
	public void testFindBoundariesWithValidBoundariesInMiddle() {
		String src = String.format("00fv%sfoobar%s123", LEFT_BOUNDARY, RIGHT_BOUNDARY);
		assertEquals("foobar", Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, src));
	}

	@Test
	public void testFindBoundariesWithValidBoundariesOnLeftEdge() {
		String src = String.format("%sfoobar%s123", LEFT_BOUNDARY, RIGHT_BOUNDARY);
		assertEquals("foobar", Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, src));
	}

	@Test
	public void testFindBoundariesWithValidBoundariesOnRightEdge() {
		String src = String.format("00123%sfoobar%s", LEFT_BOUNDARY, RIGHT_BOUNDARY);
		assertEquals("foobar", Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, src));
	}

	@Test
	public void testFindBoundariesWithValidBoundariesOnBothEdges() {
		String src = String.format("%sfoobar%s", LEFT_BOUNDARY, RIGHT_BOUNDARY);
		assertEquals("foobar", Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, src));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFindBoundariesWithBoundariesMissing() {
		String src = String.format("abcfoobarxyz");
		Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, src);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindBoundariesWithNullSource() {
		Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindBoundariesWithEmptySource() {
		Util.findBoundaries(LEFT_BOUNDARY, RIGHT_BOUNDARY, "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFindBoundariesWithNullLeftBoundary() {
		Util.findBoundaries(null, RIGHT_BOUNDARY, "foo");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindBoundariesWithNullRightBoundary() {
		Util.findBoundaries(LEFT_BOUNDARY, null, "foo");
	}
}
