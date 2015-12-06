package com.github.lmind.timemachine;

public class TimeMachine {

	public static void travel(long time) {
		TimeMachineSystem.setOffset(time - TimeMachineSystem.originalTime());
	}
	
	public static void recovery() {
		TimeMachineSystem.start();
		TimeMachineSystem.setOffset(0l);
	}
	
	public static void timeStart() {
		TimeMachineSystem.start();
	}
	
	public static void timeStop() {
		TimeMachineSystem.stop();
	}
	
	public static void timeStopAt(long time) {
		TimeMachineSystem.setMax(time);
	}
}
