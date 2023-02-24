/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.logging.Logger;

import programmingtheiot.common.ConfigConst;

/**
 * Shell representation of class for student implementation.
 * 
 */
public class SystemMemUtilTask extends BaseSystemUtilTask
{
	Logger _Logger = Logger.getLogger(SystemMemUtilTask.class.getName());
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public SystemMemUtilTask()
	{
		super(ConfigConst.NOT_SET, ConfigConst.DEFAULT_TYPE_ID);
	}
	
	
	// public methods
	
	@Override
	public float getTelemetryValue()
	{
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		double memUtil = ((double) memUsage.getUsed() / (double) memUsage.getMax()) * 100.0d;
		_Logger.info("Memory utilized: " + memUtil);
		return (float)memUtil;
	}
	
}
