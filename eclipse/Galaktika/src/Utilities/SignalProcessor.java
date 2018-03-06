package Utilities;

import java.util.ArrayDeque;

public class SignalProcessor {

	protected ArrayDeque<Double> window;
	protected int windowSize;
	protected double sdevThreshold;
	protected double supressionWeight;
	protected double mean;
	protected double sdev;
	protected double min;
	protected double max;
	public SignalProcessor(int windowSize, double sdevThreshold, double supressionWeight) {

		window = new ArrayDeque<>();
		this.windowSize = windowSize;
		this.sdevThreshold = sdevThreshold;
		this.supressionWeight = supressionWeight;
		mean = 0;
		sdev = 0;
		min = Double.NaN;
		max = Double.NaN;
	}

	public Signal update(double point) {
		int signChanged = 0;
		if(window.size() == 0) {
			signChanged = 0;
		}else if(point > 0 && window.getLast() <= 0) {
			signChanged = -1;
		}else if(point < 0 && window.getLast() >= 0) {
			signChanged = 1;
		}
		
		double sdevs = (point == mean)? 0.0: (point - mean) / sdev;
		
		if (window.size() < windowSize) {
			add(point);
			return new Signal(false, sdevs, point, signChanged, mean, sdev, min, max);
		}else {
			if (Math.abs(sdevs) > sdevThreshold) {
				double weightedPoint = window.getLast() * (1-supressionWeight) + point * supressionWeight;
				add(weightedPoint);
				return new Signal(true, sdevs, weightedPoint, signChanged, mean, sdev, min, max);
			}else {
				add(point);
				return new Signal(false, sdevs, point, signChanged, mean, sdev, min, max);
			}
		}
	}


	private void add(double point) {
		window.addLast(point);
		if (window.size() > windowSize) {
			window.removeFirst();
		}
		double m = 0;
		double S = 0;
		double n = 0;
		min = Double.POSITIVE_INFINITY;
		max = Double.NEGATIVE_INFINITY;
		for (Double x: window) {
			double oldm = m;
			n++;
			m += (x - m) / n;
			S += (x - m) * (x - oldm);
			if (x < min) min = x;
			if (x > max) max = x;

		}
		mean = m;
		sdev = (n<=1)?Double.NaN:Math.sqrt(S / (n-1));
	}
	
}
