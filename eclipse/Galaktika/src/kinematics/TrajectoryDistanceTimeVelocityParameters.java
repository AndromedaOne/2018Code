package kinematics;

public class TrajectoryDistanceTimeVelocityParameters {
	public double distance;
	public double maxVelocityDistance;
	public double velocityCoveredWhileAccelerationIsChangingFrom0ToMaxAcceleration;
	public double maxPossibleDistanceCoveredWhileAccelerating;
	
	public double maxAccelerationVelocity;
	public double timeAccelerationIsChanging;
	public double distanceCoveredWhileAccelerationIsIncreasing; 
	public double distanceCoveredWhileAccelerationIsDecreasing;
	
	public double velocityAfterCruisingAtMaxAcceleration;
	public double timeWhenAccelerationIsChangingFrom0ToMaxAcceleration;
	public double distanceCoveredWhileCruisingAtMaxAcceleration;
}
