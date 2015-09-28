package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.AThiefScenario;
import com.msu.scenarios.tsp.Bays29;
import com.msu.scenarios.tsp.Berlin52;
import com.msu.scenarios.tsp.D198;
import com.msu.scenarios.tsp.Eil101;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;




/**
 * This test is to prove that that the algorithm is working correctly on the
 * TSPLIB instances where the optimum is known!
 * 
 * It's a parameter test and cold easy be adapted to new scenarios.
 */
@RunWith(value = Parameterized.class)
public class LinKernighanHeuristicTest {

	protected double optimal;
	AThiefScenario<SymmetricMap, Tour<?>> scenario;
	

	public LinKernighanHeuristicTest(double optimal, AThiefScenario<SymmetricMap, Tour<?>> scenario) {
		super();
		this.optimal = optimal;
		this.scenario = scenario;
	}

	@Parameters(name = "optimal {0}, scenario {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { 2020.0, new Bays29() },
			{ 7542.0, new Berlin52()}, { 15780.0, new D198()}, { 629.0, new Eil101()} });
	}

	@Test
	public void testCorrectness() {
		LinKernighanHeuristic lkh = new LinKernighanHeuristic();
		SymmetricMap map = scenario.getObject();
		NonDominatedSolutionSet set = lkh.run(new Evaluator<ICityProblem>(new TravellingSalesmanProblem(map)));
		assertEquals(1, set.getSolutions().size());
		assertEquals(optimal, set.getSolutions().get(0).getObjectives(0), 0.1);
	}

}
