/**
 * This class is part of the Programming the Internet of Things project.
 * 
 * It is provided as a simple shell to guide the student and assist with
 * implementation for the Programming the Internet of Things exercises,
 * and designed to be modified by the student as needed.
 */ 

package programmingtheiot.gda.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import programmingtheiot.common.ConfigConst;
import programmingtheiot.common.ConfigUtil;
import programmingtheiot.common.IDataMessageListener;
import programmingtheiot.common.ResourceNameEnum;
import programmingtheiot.data.SystemPerformanceData;

/**
 * Shell representation of class for student implementation.
 * 
 */
public class SystemPerformanceManager
{
	// private var's
	private ScheduledExecutorService schedExecSvc = null;
	private SystemCpuUtilTask sysCpuUtilTask = null;
	private SystemMemUtilTask sysMemUtilTask = null;

	private String locationID = ConfigConst.GATEWAY_DEVICE;
	private Runnable taskRunner = null;
	private boolean isStarted = false;
	private int pollRate = ConfigConst.DEFAULT_POLL_CYCLES;

	private static final Logger _Logger =
		Logger.getLogger(SystemPerformanceManager.class.getName());
	
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public SystemPerformanceManager()
	{
		this.pollRate = ConfigUtil.getInstance().getInteger(ConfigConst.GATEWAY_DEVICE, ConfigConst.POLL_CYCLES_KEY, ConfigConst.DEFAULT_POLL_CYCLES);
	
		if(this.pollRate <= 0) {
			this.pollRate = ConfigConst.DEFAULT_POLL_CYCLES;
		}

		this.locationID = ConfigUtil.getInstance().getProperty(ConfigConst.GATEWAY_DEVICE, ConfigConst.DEVICE_LOCATION_ID_KEY, ConfigConst.GATEWAY_DEVICE);
		this.schedExecSvc = Executors.newScheduledThreadPool(1);
		this.sysCpuUtilTask = new SystemCpuUtilTask();
		this.sysMemUtilTask = new SystemMemUtilTask();

		this.taskRunner = () -> {
			this.handleTelemetry();
		};
	}
	
	
	// public methods
	
	public void handleTelemetry()
	{
		float cpuUtil = this.sysCpuUtilTask.getTelemetryValue();
		float memUtil = this.sysMemUtilTask.getTelemetryValue();
		_Logger.info("CPU util: " + cpuUtil + "\nMemory util: " + memUtil);
	}
	
	public void setDataMessageListener(IDataMessageListener listener)
	{
	}
	
	public void startManager()
	{
		if(!isStarted) {
			ScheduledFuture<?> futureTask = this.schedExecSvc.scheduleAtFixedRate(this.taskRunner, 0L, this.pollRate, TimeUnit.SECONDS);
			this.isStarted = true;
		}
		_Logger.info("System Performance Manager started.");
	}
	
	public void stopManager()
	{
		this.schedExecSvc.shutdown();
		_Logger.info("System Performance Manager stopped.");
	}
	
}
