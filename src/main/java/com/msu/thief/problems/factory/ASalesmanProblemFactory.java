package com.msu.thief.problems.factory;

import com.msu.thief.problems.SalesmanProblem;
import com.msu.util.Random;

public abstract class ASalesmanProblemFactory {

	public abstract SalesmanProblem create(int numOfCities, Random rand);
	
}