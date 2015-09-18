package com.msu.thief.model.tour.factory;

import java.util.List;

import com.msu.algorithms.LinKernighanHeuristic;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;
import com.msu.moo.util.Util;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;

public class StandardTourMutateOptimumFactory<P extends ICityProblem> extends ATourFactory<P> {

	P problem = null;
	Tour<?> optimum = null;

	@Override
	public Tour<?> next(P p) {
		if (problem != p) optimum = null;
		if (optimum == null) {
			LinKernighanHeuristic lkh = new LinKernighanHeuristic();
			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(p.getMap());
			NonDominatedSolutionSet set = lkh.run(new Evaluator<TravellingSalesmanProblem>(tsp));
			if (set.size() != 1)
				throw new RuntimeException("LinKernighanHeuristic error solving tour!");
			optimum = (Tour<?>) set.getSolutions().get(0).getVariable();
		}
		
		if (Random.getInstance().nextDouble() < 0.2) return optimum;
		else {
			List<Integer> l = optimum.encode();
			int a = Random.getInstance().nextInt(0, l.size()-1);
			int b = Random.getInstance().nextInt(0, l.size()-1);
			Util.swap(l, a, b);
			return new StandardTour(l);
		}
		
	}

}
