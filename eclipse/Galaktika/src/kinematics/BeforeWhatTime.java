package kinematics;

/**
 * This enum tells the get position method where a point in time is relative to other important points in time.
 * For example:
 * 	beforeStartVelocityCruisingDeltaTime: means that a point in time is before the time that velocity has reached max velocity
 * @author seandoyle
 *
 */
public enum BeforeWhatTime {
	beforeFirstStartAccelerationCruisingDeltaTime, beforeFirstEndAccelerationCruisingDeltaTime, beforeStartVelocityCruisingDeltaTime, beforeEndVelocityCruisingDeltaTime, beforeSecondStartAccelerationCruisingDeltaTime, beforeSecondEndAccelerationCruisingDeltaTime, beforeEndDeltaTime, afterEndDeltaTime
}