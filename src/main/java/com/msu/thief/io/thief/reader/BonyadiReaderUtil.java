package com.msu.thief.io.thief.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public abstract class BonyadiReaderUtil{

	static final Logger logger = Logger.getLogger(BonyadiReaderUtil.class);

	public static int parseNumOfCities(BufferedReader br) throws NumberFormatException, IOException {
		int numOfCities = Integer.valueOf(br.readLine());
		logger.info(String.format("Parsed number of cities: %s", numOfCities));
		return numOfCities;
	}

	public static int parseNumOfItems(BufferedReader br) throws NumberFormatException, IOException {
		int numOfItems = Integer.valueOf(br.readLine());
		logger.info(String.format("Parsed number of items: %s", numOfItems));
		return numOfItems;
	}

	public static int parseMaximalWeight(BufferedReader br) throws NumberFormatException, IOException {
		int maxWeight = Integer.valueOf(br.readLine());
		logger.info(String.format("Parsed maximal weight of knapsack: %s", maxWeight));
		return maxWeight;
	}

	public static double parseMaxVelocity(BufferedReader br) throws NumberFormatException, IOException {
		double vmax = Double.valueOf(br.readLine());
		logger.info(String.format("Parsed maximal velocity: %s", vmax));
		return vmax;
	}

	public static double parseMinVelocity(BufferedReader br) throws NumberFormatException, IOException {
		double vmin = Double.valueOf(br.readLine());
		logger.info(String.format("Parsed minimal velocity: %s", vmin));
		return vmin;
	}

	public static double parseDroppingConstant(BufferedReader br) throws NumberFormatException, IOException {
		double droppingConstant = Double.valueOf(br.readLine());
		logger.info(String.format("Parsed dropping constant: %s", droppingConstant));
		return droppingConstant;
	}

	public static double parseSingleObjectiveWeight(BufferedReader br) throws NumberFormatException, IOException {
		double R = Double.valueOf(br.readLine());
		logger.info(String.format("Parsed SingleObjectiveWeight: %s", R));
		return R;
	}
	
	public static double parseDroppingRate(BufferedReader br) throws NumberFormatException, IOException {
		double droppingRate = Double.valueOf(br.readLine());
		logger.info(String.format("Parsed dropping rate: %s", droppingRate));
		return droppingRate;
	}

	public static SymmetricMap parseMap(BufferedReader br, int numOfCities) throws NumberFormatException, IOException {
		SymmetricMap map = new SymmetricMap(numOfCities);
		logger.info("Started to parse distance matrix.");
		for (int i = 0; i < numOfCities; i++) {
			String[] distance = br.readLine().split(" ");
			for (int j = 0; j < distance.length; j++) {
				map.set(i, j, Double.valueOf(distance[j]));
			}
		}
		logger.info("Finished parsing map.");
		return map;
	}

	public static ItemCollection<Item> parseItems(BufferedReader br, int numOfItems, int numOfCities) throws NumberFormatException, IOException {
		ItemCollection<Item> items = new ItemCollection<>();

		// parse all the items
		logger.info("Starting to parse items.");
		String[] weights = br.readLine().split(" ");
		String[] values = br.readLine().split(" ");
		Boolean[][] mItems = new Boolean[numOfItems][numOfCities];
		for (int i = 0; i < numOfItems; i++) {
			String[] b = br.readLine().split(" ");
			for (int j = 0; j < b.length; j++) {
				if (b[j].equals("1"))
					mItems[i][j] = true;
				else
					mItems[i][j] = false;
			}
		}

		for (int j = 0; j < numOfCities; j++) {
			for (int i = 0; i < numOfItems; i++) {
				if (mItems[i][j]) {
					Item item = new Item(Double.valueOf(values[i]), Double.valueOf(weights[i]));
					items.add(j, item);
				}
			}
		}
		logger.info("Finished to parse items.");
		return items;

	}
	
	public static String parseName(String pathToFile) {
		String name = new File(pathToFile).getName();
		return name.substring(0, name.length() - 4);
	}

}
