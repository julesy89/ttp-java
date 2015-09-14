package com.msu.thief.experiment.knp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.factory.AlgorithmFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public abstract class AbstractKNPExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	protected abstract Integer[][] getItems();

	protected abstract Integer[] getOptimum();

	protected abstract Integer getMaxWeight();


	public void visualize() {
		for (IAlgorithm<TravellingThiefProblem> a : algorithms) {
			Collection<NonDominatedSolutionSet> sets = expResult.get(problem, a);
			for(NonDominatedSolutionSet s : sets) {
				System.out.println(s.getSolutions().get(0).getObjectives().get(1));
			}
		}
	}

	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = AlgorithmFactory.createNSGAIIBuilder();
		NSGAII<TTPVariable, TravellingThiefProblem> nsga = builder.create();
		nsga.setPopulationSize(1000);
		return nsga;
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		ItemCollection<Item> items = new ItemCollection<Item>();

		for (Integer[] row : getItems()) {
			Item item = new Item(row[0], row[1]);
			items.add(0, item);
		}

		TravellingThiefProblem problem = new TravellingThiefProblem(new Map(1), items, getMaxWeight());
		problem.setName(this.getClass().getSimpleName());
		return problem;
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		List<Boolean> best = new ArrayList<>();
		for (Integer i : getOptimum()) {
			best.add(i == 1);
		}
		PackingList<?> l = new BooleanPackingList(new ArrayList<Boolean>(best));

		Tour<?> t = new StandardTour(new ArrayList<>(Arrays.asList(0)));
		Solution s = problem.evaluate(new TTPVariable(Pair.create(t, l)));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		System.out.println(set.getSolutions().get(0).getObjectives());
		return set;
	}

}