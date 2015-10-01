package com.msu.io;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.io.reader.JsonThiefReader;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;

public class ThiefSingleObjectiveReaderTest {

	protected static ThiefProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		problem = new JsonThiefReader().read("resources/SO_Boniady_Publication.ttp");
	}
	
	
	@Test
	public void testResultIsNotNull() {
		assertNotNull(problem);
	}
	

	@Test
	public void testNumOfCities() {
		assertEquals(4, problem.numOfCities());
	}

	@Test
	public void testVelocity() {
		assertEquals(0.1, problem.getMinSpeed(), 0.01);
		assertEquals(1, problem.getMaxSpeed(), 0.01);
	}
	
	@Test
	public void testR() {
		assertEquals(1.0, ((SingleObjectiveThiefProblem)problem).getR(), 0.01);
	}

	@Test
	public void testDistanceFirstAndSecond() {
		assertEquals(5.0, problem.getMap().get(0, 1), 0.01);
	}
	
	@Test
	public void testNumberOfItems() {
		assertEquals(6, problem.numOfItems());
	}
	
	
	
	
	
}
