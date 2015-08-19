package com.moo.problems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moo.problems.TravellingSalesmanProblem;
import com.moo.ttp.model.Map;


public class TravellingSalesmanProblemTest {
	
	private TravellingSalesmanProblem tsp;
	
	@Before
	public void setUp() {
		Map m = new Map(3).set(0, 1, 1).set(1, 2, 2).set(2, 0, 3);
		tsp = new TravellingSalesmanProblem(m);
	}

	@Test
	public void testEvaluateFunction() {
		assertEquals(6, (int) tsp.evaluate(new Integer[]{0,1,2}));
	}
	
	@Test (expected=RuntimeException.class) 
	public void testWrongSizeOfTour() throws RuntimeException {
		tsp.evaluate(new Integer[] {0});
	}
	
	@Test (expected=RuntimeException.class) 
	public void testNotAValidPermutation() throws RuntimeException {
		tsp.evaluate(new Integer[] {0,0,1});
	}
	
	@Test (expected=RuntimeException.class) 
	public void testValidPermuationButMissingCity() throws RuntimeException {
		tsp.evaluate(new Integer[] {0,2,3});
	}
	

}