package kr.nexg.esm.common.util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class ProcessUtil {
	public static Map<String, Long> diskUsageMonitor(String path) {
		File file = null;
		Map<String, Long> map = new HashMap<>();
		try {
			file = new File(path);
			long total = file.getTotalSpace();
//			long used = file.getUsableSpace();
			long used = FileUtils.sizeOfDirectory(file);
			
			map.put("total", total);
			map.put("used", used);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static double cpuUsagePercentMonitor() {
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
		double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad();
        
        return cpuLoad * 100;
	}
	
	public static Map<String, Long> memoryUsageMonitor() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        
        long heapMax = heapMemoryUsage.getMax();
        long heapUsed = heapMemoryUsage.getMax();
        long heapAvail = heapMemoryUsage.getMax() - heapMemoryUsage.getUsed();
        
        long nonHeapMax = nonHeapMemoryUsage.getMax();
        long nonHeapUsed = nonHeapMemoryUsage.getMax();
        long nonHeapAvail = nonHeapMemoryUsage.getMax() - nonHeapMemoryUsage.getUsed();

        Map<String, Long> map = new HashMap<>();
        map.put("heapMax", heapMax);
        map.put("heapUsed", heapUsed);
        map.put("heapAvail", heapAvail);
        map.put("nonHeapMax", nonHeapMax);
        map.put("nonHeapUsed", nonHeapUsed);
        map.put("nonHeapAvail", nonHeapAvail);
        
        return map;
	}
}
