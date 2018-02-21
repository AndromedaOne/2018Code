package Utilities;

import java.util.ArrayDeque;

public class SpikeDetector {
	
	protected ArrayDeque<Double> window;
	protected int windowSize;
	protected double sdevThreshold;
	protected double supressionWeight;
	public double mean;
	public double sdev;
	public double min;
	public double max;
	public SpikeDetector(int windowSize, double sdevThreshold, double supressionWeight) {
		
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
		if (window.size() < windowSize) {
			add(point);
			return new Signal(0, 0);
		}else {
			
			
			int signChanged = 0;
			if(point > 0 && window.getLast() <= 0) {
				signChanged = -1;
			}else if(point < 0 && window.getLast() >= 0) {
				signChanged = 1;
			}
			if (Math.abs(mean - point)/sdev > sdevThreshold) {
				double outlierness = (point - mean) / sdev;
				add(window.getLast() * (1-supressionWeight) + point * supressionWeight);
				return new Signal(outlierness, signChanged);
			}else {
				add(point);
				return new Signal(0, signChanged);
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
