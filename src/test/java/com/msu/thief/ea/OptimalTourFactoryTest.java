package com.msu.thief.ea;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.problems.variable.Tour;

public class OptimalTourFactoryTest extends Operator {
	
	
	@Test
	public void testFactory() {
		TourOptimalFactory fac = new TourOptimalFactory(thief);
		Tour t =  fac.next(rand);
		assertTrue(t.equals(Tour.createFromString("0,1,2,3")) || t.equals(Tour.createFromString("0,3,2,1")));
	}
	
	
	
	
	

}
