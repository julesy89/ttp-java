package com.msu.scenarios.thief.bonyadi;

import com.msu.knp.model.Item;
import com.msu.scenarios.AThiefScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class PublicationScenario extends AThiefScenario<ThiefProblem, Object>{

	
	@Override
	public ThiefProblem getObject() {
		
		SymmetricMap m = new SymmetricMap(4)
				.set(0, 1, 5)
				.set(0, 2, 6)
				.set(0, 3, 6)
				.set(1, 2, 5)
				.set(1, 3, 6)
				.set(2, 3, 4);
		
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(10, 3));
		items.add(2, new Item(4, 1));
		items.add(2, new Item(4, 1));
		items.add(1, new Item(2, 2));
		items.add(2, new Item(3, 3));
		items.add(3, new Item(2, 2));
		
		ThiefProblem problem = new ThiefProblem(m, items, 3);
		problem.setProfitEvaluator(new ExponentialProfitEvaluator());
		return problem;
		
	}


}