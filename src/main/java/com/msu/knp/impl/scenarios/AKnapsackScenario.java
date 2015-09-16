package com.msu.knp.impl.scenarios;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Pair;
import com.msu.thief.model.Item;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.scenarios.AScenario;

public abstract class AKnapsackScenario extends AScenario<Pair<List<Item>,Integer>, PackingList<?>> {

	protected abstract Integer[][] getItems();

	protected abstract Integer[] getOptimum();

	protected abstract Integer getMaxWeight();
	
	@Override
	public Pair<List<Item>, Integer> getObject() {
		List<Item> items = new ArrayList<Item>();
		for (Integer[] row : getItems()) {
			Item item = new Item(row[0], row[1]);
			items.add(item);
		}
		return Pair.create(items, getMaxWeight());
	}

	@Override
	public PackingList<?> getOptimal() {
		List<Boolean> b = new ArrayList<>();
		for (int i : getOptimum()) {
			if (i == 0 ) b.add(false);
			else b.add(true);
		}
		return new BooleanPackingList(b);
	}


}
