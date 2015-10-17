package com.msu.thief.variable.pack.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Random;
import com.msu.problems.IPackingProblem;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;

public class RandomPackingListFactory extends APackingPlanFactory {


	@Override
	public PackingList<?> next(IProblem p) {
		Random rnd = Random.getInstance();
		double pickingProb = rnd.nextDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < ((IPackingProblem) p).numOfItems(); i++) {
			b.add(rnd.nextDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}