package Utilities;

import java.util.ArrayDeque;

public class SpikeDetector {
	
	protected ArrayDeque<Double> window;
	protected int windowSize;
	protected double sdevThreshold;
	protected double supressionWeight;
	protected double mean;
	protected double sdev;
	public SpikeDetector(int windowSize, double sdevThreshold, double supressionWeight) {
		
		window = new ArrayDeque<>();
		this.windowSize = windowSize;
		this.sdevThreshold = sdevThreshold;
		this.supressionWeight = supressionWeight;
		mean = 0;
		sdev = 0;
	}
	
	public double update(double point) {
		if (window.size() < windowSize) {
			add(point);
			return 0;
		}else {
			if (Math.abs(mean - point)/sdev > sdevThreshold) {
				double outlierness = (point - mean) / sdev;
				add(window.getLast() * (1-supressionWeight) + point * supressionWeight);
				return outlierness;
			}else {
				add(point);
				return 0;
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
		for (Double x: window) {
			double oldm = m;
			n++;
			m += (x - m) / n;
			S += (x - m) * (x - oldm);
			
		}
		mean = m;
		sdev = (n<=1)?Double.NaN:Math.sqrt(S / (n-1));
	}

}
