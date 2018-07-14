package kinematics;

public interface MotionProfilingController {
	void disable();
	
	void enable();
	
	void run();
	
	boolean onTarget();

	void setSetpoint(double setpoint);
	
	
}
