package com.github.lmind.timemachine;


public class TimeMachineSystem {
	
	static {
		System.currentTimeMillis();
	}
	
	public static long currentTimeMillis(long startTimeMillis, long startNanoTime) {
		TimeMachineSystem.startTimeMillis = startTimeMillis;
		TimeMachineSystem.startNanoTime = startNanoTime;
		
		if (stop) {
			return last;
		}
		
		long r = originalTime() + offset;
		if (max != Long.MAX_VALUE && r > max) {
			r = max;
		}
		
		last = r;
		return r;
	}
	
	private static volatile boolean stop;
	
	private static volatile long stopTime;
	
	private static volatile long last;
	
	private static volatile long max = Long.MAX_VALUE;
	
	private static volatile long offset;
	
	private static volatile long startTimeMillis;
	
	private static volatile long startNanoTime;
	
	public static long getOffset() {
		return offset;
	}

	public static void setOffset(long value) {
		offset = value;
	}
	
	public static void setMax(long value) {
		max = value;
	}

	public static long originalTime() {
		return startTimeMillis + (System.nanoTime() - startNanoTime) / 1000000;
	}
	
	public static void start() {
		offset = stopTime - originalTime();
		stop = false;
	}
	
	public static void stop() {
		System.currentTimeMillis();
		stop = true;
		stopTime = System.currentTimeMillis();
	}
}
