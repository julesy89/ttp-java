package com.msu.thief.ea;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefMutation implements IMutation<TTPVariable> {


	//! crossover for the tour
	protected IMutation<Tour> mTour = null;
	
	//! crossover for the packing plan
	protected IMutation<Pack> mPack = null;

	
	public ThiefMutation(IMutation<Tour> mTour, IMutation<Pack> mPack) {
		super();
		this.mTour = mTour;
		this.mPack = mPack;
	}
	
	
	@Override
	public void mutate(TTPVariable a, MyRandom rand) {
		
		double probTour = rand.nextDouble();
		double probPack = rand.nextDouble();
		
		// if both are lower and no mutation would occur -> do at least one mutation
		if (probTour > 0.5 && probPack > 0.5) {
			if (rand.nextDouble() < 0.5) probTour = 0;
			else probPack = 0;
		}
		

		if (mTour != null && probTour < 0.5) {
			mTour.mutate(a.getTour(), rand);
		}
		
		if (mPack != null && probPack < 0.5) {
			mPack.mutate(a.getPack(), rand);
		}

		
	}


	
	
}
