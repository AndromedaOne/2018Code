package kinematics;

import java.util.Vector;

public class Path extends TrajectoryPaths {

	Vector<Point> setpointVector = new Vector<Point>();

	public Vector<Point> getSetpointVector() {
		return setpointVector;
	}

	int setPointDimention = 0;

}
