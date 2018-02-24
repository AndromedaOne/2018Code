package Utilities;

import java.util.ArrayDeque;

public class SignalProcessor {

	protected ArrayDeque<Double> window;
	protected int windowSize;
	protected double sdevThreshold;
	protected double supressionWeight;
	protected double mean;
	protected double sdev;
	protected double skewness;
	protected double kurtosis;
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
			return new Signal(false, sdevs, point, signChanged, mean, sdev, skewness, kurtosis, min, max);
		}else {
			if (Math.abs(sdevs) > sdevThreshold) {
				double weightedPoint = window.getLast() * (1-supressionWeight) + point * supressionWeight;
				add(weightedPoint);
				return new Signal(true, sdevs, weightedPoint, signChanged, mean, sdev, skewness, kurtosis, min, max);
			}else {
				add(point);
				return new Signal(false, sdevs, point, signChanged, mean, sdev, skewness, kurtosis, min, max);
			}
		}
	}
/**
 * algorithm taken from https://www.johndcook.com/blog/skewness_kurtosis/
 * @param point
 */

	private void add(double point) {
		window.addLast(point);
		if (window.size() > windowSize) {
			window.removeFirst();
		}
		double n = 0;
		double M1 = 0;
		double M2 = 0;
		double M3 = 0;
		double M4 = 0;
		min = Double.POSITIVE_INFINITY;
		max = Double.NEGATIVE_INFINITY;
		for (Double x: window) {
			double n1 = n;
			n++;
			double delta = x - M1;
		    double delta_n = delta / n;
		    double delta_n2 = delta_n * delta_n;
		    double term1 = delta * delta_n * n1;
		    M1 += delta_n;
		    M4 += term1 * delta_n2 * (n*n - 3*n + 3) + 6 * delta_n2 * M2 - 4 * delta_n * M3;
		    M3 += term1 * delta_n * (n - 2) - 3 * delta_n * M2;
		    M2 += term1;
			if (x < min) min = x;
			if (x > max) max = x;

		}
		mean = M1;
		sdev = (n<=1)?Double.NaN:Math.sqrt(M2 / (n-1));
		skewness = Math.sqrt(n) * M3/ Math.pow(M2, 1.5);
		kurtosis = n*M4 / (M2*M2) - 3.0; 
	}
	/*
	public static void main(String[] args) throws Exception {
		double[] a1 = {1,2,3,4,5,3.14};
		SignalProcessor s = new SignalProcessor(3, 100, 0);
		Signal signal = null;
		for (double x: a1) {
			signal = s.update(x);
			System.out.println(signal);
		}
		if (!(Math.abs(signal.mean - 4.046666666666667) < 1e-6)) throw new Exception();
		if (!(Math.abs(signal.sdev - 0.9308777220093589) < 1e-6)) throw new Exception();
	}
	*/
}
