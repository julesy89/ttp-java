package com.msu.tsp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class StandardTour extends Tour<List<Integer>> {

	
	/**
	 * Create a Tour using a permutation vector
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public StandardTour(List<Integer> list) {
		super(list);
		Collections.rotate(obj, obj.indexOf(0));
	}

	
	@Override
	public StandardTour copy() {
		return new StandardTour(new ArrayList<Integer>(obj));
	}

	@Override
	public List<Integer> encode() {
		return obj;
	}




}