package com.msu.thief.util.rounding;

public class RoundingNearestInt implements IRounding{


	@Override
	public double execute(double d) {
		return Math.round(d);
	}

}
