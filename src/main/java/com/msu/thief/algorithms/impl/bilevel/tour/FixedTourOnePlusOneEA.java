package com.msu.thief.algorithms.impl.bilevel.tour;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AFixedTourSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;


/**
 * Same idea like OnePlusOneEA.
 * But it solves only the problem for a fixed tour. It is used for the bilevel approach.
 */
public class FixedTourOnePlusOneEA extends AFixedTourSingleObjectiveAlgorithm {

	//! best packing list found so far
	protected Pack best = new Pack();
	
	
	public FixedTourOnePlusOneEA() {
		super();
	}


	public FixedTourOnePlusOneEA(Pack best) {
		super();
		this.best = best;
	}


	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		
		PackBitflipMutation mutation = new PackBitflipMutation(problem.getProblem());
		
		NonDominatedSolutionSet<Pack> set = new NonDominatedSolutionSet<>();
		set.add(evaluator.evaluate(problem, best));

		while (evaluator.hasNext()) {
			
			Pack next = best.copy();
			mutation.mutate(next, rand);
			
			// check if this solution is better
			Solution<Pack> s = evaluator.evaluate(problem, next);
			boolean isBetter = set.add(s);
			
			if (isBetter) best = next;
			
			evaluator.notify(set.getSolutions());
			
		}
		Solution<Pack> s =  evaluator.evaluate(problem, best);
		
		return s;
	}
	


}
