package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.msu.io.reader.SalesmanProblemReader;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.tsp.TravellingSalesmanProblem;

/**
 * This test is to prove that that the algorithm is working correctly on the
 * TSPLIB instances where the optimum is known!
 * 
 * It's a parameter test and cold easy be adapted to new scenarios.
 */
@RunWith(value = Parameterized.class)
public class LinKernighanHeuristicTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
	}
	
	protected String pathToFile = null;

	public LinKernighanHeuristicTest(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	@Parameters(name = "scenario {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //{ "resources/bays29.tsp" }, 
			{"resources/berlin52.tsp" }, { "resources/eil101.tsp" }, { "resources/d198.tsp" } });
	}

	@Test
	public void testCorrectness() {
		LinKernighanHeuristic lkh = new LinKernighanHeuristic();
		
		TravellingSalesmanProblem problem = new SalesmanProblemReader().read(pathToFile);
		NonDominatedSolutionSet set = lkh.run(new Evaluator(problem));
		
		assertEquals(1, set.getSolutions().size());
		assertEquals(problem.getOptimum().get(0).getObjectives(0), set.getSolutions().get(0).getObjectives(0), 0.01);
	}

}
