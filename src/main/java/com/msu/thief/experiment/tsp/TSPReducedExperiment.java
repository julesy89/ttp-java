package com.msu.thief.experiment.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.experiment.AbstractExperiment;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.problems.tsp.TSPExhaustiveFactory;
import com.msu.thief.problems.tsp.TSPVariable;
import com.msu.thief.problems.tsp.TravellingSalesmanProblem;

public class TSPReducedExperiment extends AbstractExperiment<TravellingThiefProblem> {

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {
		return new ArrayList<>(Arrays.asList(AlgorithmFactory.createNSGAII()));
	}

	@Override
	protected List<TravellingThiefProblem> getProblems() {
		List<TravellingThiefProblem> l = new ArrayList<TravellingThiefProblem>();
		com.msu.thief.model.Map small = new MapFactory(1000).create(1000);
		TravellingThiefProblem problem = new TravellingThiefProblem(small, new ItemCollection<Item>(), 0);
		problem.setProfitEvaluator(new ExponentialProfitEvaluator());
		problem.setName("TSPReducedProblem");
		l.add(problem);
		return l;
	}

	public static void main(String[] args) {
		new TSPReducedExperiment().run();
	}

	@Override
	public int getIterations() {
		return 10;
	}

	@Override
	public long getMaxEvaluations() {
		return 50000L;
	}

	@Override
	public <P extends IProblem> Map<IProblem, NonDominatedSolutionSet> getTrueFronts(List<P> problems) {
		Map<IProblem, NonDominatedSolutionSet> result = new HashMap<>();
		for (P p : problems) {
			TSPExhaustiveFactory fac = new TSPExhaustiveFactory();
			ExhaustiveSolver<TSPVariable, TravellingSalesmanProblem> exhaustive = new ExhaustiveSolver<>(fac);
			TravellingThiefProblem ttp = (TravellingThiefProblem) p;
			NonDominatedSolutionSet set = exhaustive.run(new TravellingSalesmanProblem(ttp.getMap()));
			set.getSolutions().get(0).getObjectives().add(0.0);
			result.put(p, set);
		}
		return result;
	}

}