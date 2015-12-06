package com.github.lmind.timemachine;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class TimeMachinePremain {

	public static void premain(String options, Instrumentation ins) {
		
		try {
			String path = System.getenv("TIME_MACHINA_PATH");
			if (path == null) {
				path = "time-machine.jar";
			}
			
			ins.appendToBootstrapClassLoaderSearch(new JarFile(path));
			ins.addTransformer(new TimeMachineTransformer(System.currentTimeMillis(), System.nanoTime()), true);
			ins.retransformClasses(System.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
