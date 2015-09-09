package com.msu.thief.factory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.problems.TravellingThiefProblem;

public class BonyadiFactoryTest {
	
	protected TravellingThiefProblem ttp;
	
	
	@Before
    public void setUp() {
		BonyadiFactory fac = new BonyadiFactory();
		ttp = fac.create("../ttp-bonyadi/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
    }
	
	
	@Test
	public void testNumOfCitiesCorrect() {
		assertEquals(52, ttp.numOfCities());
	}
	
	
	@Test
	public void testMaxWeight() {
		assertEquals(4046, ttp.getMaxWeight());
	}
	
	@Test
	public void testDistancesNotZero() {
		assertTrue(0 != ttp.getMap().get(0, 1));
	}
	
	@Test
	public void testHasItems() {
		assertTrue(ttp.getItems().size() != 0);
	}
	

}