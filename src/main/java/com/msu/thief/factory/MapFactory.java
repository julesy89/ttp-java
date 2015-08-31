package com.msu.thief.factory;

import java.awt.Point;
import java.util.ArrayList;

import com.msu.moo.util.Random;
import com.msu.thief.model.Map;

/**
 * This class is used to create a Map which only contains a cost matrix.
 * There is a Map with different points (2D with X and Y values) and the euclidean distance
 * between this points is used as a edge cost value.
 *
 */
public class MapFactory {
	
	
	public static Map create(int n, int maxValue, long seed) {
		 Random.getInstance().setSeed(seed);
		 return create(n, maxValue);
	}
	
	/**
	 * Create a map.
	 * @param n cities
	 * @param maxValue value of this grid (maxValue for either X and Y)
	 * @return Map with a cost matrix
	 */
	public static Map create(int n, int maxValue) {
		
		Random rnd = Random.getInstance();
		
		ArrayList<Point> cities = new  ArrayList<Point>(n);
		for (int i = 0; i < n; i++) {
			cities.add(new Point(rnd.nextInt(0, maxValue), rnd.nextInt(0, maxValue)));
		}
		
		Map m = new Map(n);
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double disX = (cities.get(i).getX() - cities.get(j).getX()) * (cities.get(i).getX() - cities.get(j).getX());
				double disY = (cities.get(i).getY() - cities.get(j).getY()) * (cities.get(i).getY() - cities.get(j).getY());
				m.set(i, j, Math.sqrt(disX + disY));
			}
		}
		return m;
	}
	
	
	

}
