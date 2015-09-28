package com.msu.experiment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.UniformCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Pair;
import com.msu.scenarios.AThiefScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.RandomFactory;


public class KNPOperatorExperiment extends AExperiment {

	
	protected final String[] SCENARIOS = new String[] { 
			"KNP_13_0020_1000_1", 
			"KNP_13_0200_1000_1", 
			"KNP_13_1000_1000_1",
			"KNP_13_2000_1000_1"
			};
	
	@Override
	public void finalize() {
		new SingleObjectiveReport(1).print(this);
	}

	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new TTPVariableFactory(new RandomFactory(), new EmptyPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>()))
				.setName("SPX").create());
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new UniformCrossover<>()))
				.setName("UX").create());
		
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new HalfUniformCrossover<>()))
				.setName("HUX").create());
	}

	

	@Override
	protected void setProblems(List<IProblem> problems) {
	}
	
	
	@Override
	protected void setOptima(List<IProblem> problems, Map<IProblem, NonDominatedSolutionSet> mOptima) {
		for (String s : SCENARIOS) {
			@SuppressWarnings("unchecked")
			AThiefScenario<Pair<List<Item>,Integer>, PackingList<?>> scenario = 
			(AThiefScenario<Pair<List<Item>,Integer>, PackingList<?>>) ObjectFactory.create("com.msu.scenarios.knp." + s);
			
			Pair<List<Item>, Integer> obj = scenario.getObject();

			// add all to the first city
			ItemCollection<Item> items = new ItemCollection<>();
			for (Item i : obj.first)
				items.add(0, i);

			ThiefProblem problem = new ThiefProblem(new SymmetricMap(1), items, obj.second);
			problem.setName(s);
			problems.add(problem);
			
			Tour<?> t = new StandardTour(Arrays.asList(0));
			Solution sol = problem.evaluate(new TTPVariable(Pair.create(t, scenario.getOptimal())));
			NonDominatedSolutionSet set = new NonDominatedSolutionSet(Arrays.asList(sol));
			mOptima.put(problem, set);
			
		}
	}
	
	


}