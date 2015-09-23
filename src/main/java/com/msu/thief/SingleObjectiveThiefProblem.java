package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class SingleObjectiveThiefProblem extends ThiefProblem {

	protected double R = 1;
	
	public SingleObjectiveThiefProblem() {
		this.evalProfit = new NoDroppingEvaluator();
	}

	public SingleObjectiveThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight, double R) {
		super(map, items, maxWeight);
		this.evalProfit = new NoDroppingEvaluator();
		this.R = R;
	}

	@Override
	protected List<Double> evaluate_(TTPVariable variable) {

		// check for the correct input before using evaluator
		Pair<Tour<?>, PackingList<?>> pair = variable.get();
		
		List<Integer> tour = pair.first.encode();
		Collections.rotate(tour, tour.indexOf(0));
		pair.first = new StandardTour(tour);
		
		checkTour(pair.first);
		checkPackingList(pair.second);
		

		// use the evaluators to calculate the result
		double time = evalTime.evaluate(pair);
		// check if the maximal weight constraint is violated
		if (evalTime.getWeight() > getMaxWeight()) {
			return new ArrayList<Double>(Arrays.asList(Double.MAX_VALUE));
		}

		double profit = evalProfit.evaluate(evalTime.getItemMap());
		double value = profit - R * time;

		// return the negative because we minimize all!
		return new ArrayList<>(Arrays.asList(-value));
	}

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}

}
