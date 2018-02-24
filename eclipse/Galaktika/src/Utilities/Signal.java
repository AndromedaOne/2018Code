package Utilities;

public class Signal {
	//true if the point was an outlier
	public boolean isOutlier;
	//# of sdevs the point was away from the mean (whether it was an outlier or not)
	public double sdevs;
	//the value of the weighted point added to the pipe (equal to the point put in if the point is an inlier)
	public double pointAdded;
	//whether or not the sign changed
	public int signChanged;
	
	//the new values of these statistics after the point is added
	public double mean;
	public double sdev;
	public double skewness;
	public double kurtosis;
	public double min;
	public double max;
	
	public Signal(boolean isOutlier, double sdevs, double pointAdded, int signChanged, double mean, double sdev,
			double skewness, double kurtosis, double min, double max) {
		super();
		this.isOutlier = isOutlier;
		this.sdevs = sdevs;
		this.pointAdded = pointAdded;
		this.signChanged = signChanged;
		this.mean = mean;
		this.sdev = sdev;
		this.skewness = skewness;
		this.kurtosis = kurtosis;
		this.min = min;
		this.max = max;
	}

	@Override
	public String toString() {
		return "Signal [isOutlier=" + isOutlier + ", sdevs=" + sdevs + ", pointAdded=" + pointAdded + ", signChanged="
				+ signChanged + ", mean=" + mean + ", sdev=" + sdev + ", skewness=" + skewness + ", kurtosis="
				+ kurtosis + ", min=" + min + ", max=" + max + "]";
	} 
	
	

}
