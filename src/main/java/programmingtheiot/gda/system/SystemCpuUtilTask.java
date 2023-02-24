/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.lang.management.ManagementFactory;

import programmingtheiot.common.ConfigConst;


/**
 * Shell representation of class for student implementation.
 * 
 */
public class SystemCpuUtilTask extends BaseSystemUtilTask
{
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public SystemCpuUtilTask()
	{
		super(ConfigConst.SYSTEM_PERF_MSG, ConfigConst.SYSTEM_PERF_TYPE);
	}
	
	
	// public methods
	
	@Override
	public float getTelemetryValue()
	{
		return (float)ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
	}
	
}
