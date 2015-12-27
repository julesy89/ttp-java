package com.msu.thief.variable;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TTPMutation extends AbstractMutation<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractMutation<?> mTour;
	
	//! crossover for the packing plan
	protected AbstractMutation<?> mPackingPlan;

	
	public TTPMutation(AbstractMutation<?> mTour, AbstractMutation<?> mPackingPlan) {
		super();
		this.mTour = mTour;
		this.mPackingPlan = mPackingPlan;
	}

	
	
	@Override
	public Pair<IVariable,IVariable> mutate_(Pair<IVariable,IVariable> a, IProblem problem, MyRandom rand, IEvaluator eval) {
		IVariable tour = mTour.mutate(a.first, problem, rand);
		IVariable b = mPackingPlan.mutate(a.second, problem, rand);
		return Pair.create(tour, b);
	}
	
	
	
	
	
}
