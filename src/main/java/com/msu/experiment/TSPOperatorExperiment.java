package com.msu.experiment;

import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.moo.util.ObjectFactory;
import com.msu.scenarios.AThiefScenario;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.NearestNeighbourFactory;

public class TSPOperatorExperiment extends AExperiment {

	protected final String[] SCENARIOS = new String[] { "Bays29", "Berlin52", "Eil101", "D198" };

	@Override
	public void finalize() {
		new SingleObjectiveReport(0).print(this);
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new NearestNeighbourFactory());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);
		builder.setPopulationSize(1000);
		algorithms.add(builder.setCrossover(new PMXCrossover<Integer>()).setName("PMX").create());
		algorithms.add(builder.setCrossover(new CycleCrossover<Integer>()).setName("CX").create());
		algorithms.add(builder.setCrossover(new OrderedCrossover<Integer>()).setName("OX").create());
		algorithms.add(builder.setCrossover(new EdgeRecombinationCrossover<Integer>()).setName("ERC").create());
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
	}

	@Override
	protected void setOptima(List<IProblem> problems, Map<IProblem, NonDominatedSolutionSet> mOptima) {
		for (String s : SCENARIOS) {
			@SuppressWarnings("unchecked")
			AThiefScenario<SymmetricMap, Tour<?>> scenario = (AThiefScenario<SymmetricMap, Tour<?>>) ObjectFactory.create("com.msu.tsp.scenarios.impl." + s);
			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(scenario.getObject());
			tsp.setName(s);
			problems.add(tsp);
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			set.add(tsp.evaluate(scenario.getOptimal()));
			mOptima.put(tsp, set);
		}
	}

}