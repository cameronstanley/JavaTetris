package com.cameronstanley.javatetris.client.controller;

import org.lwjgl.Sys;

/**
 * Utility for time resolution using LWJGL system utilites.
 * 
 * @author Cameron
 */
public class Timer {

	/**
	 * A holder for time in milliseconds used to compute delta.
	 */
	private long lastTime;
	
	/**
	 * Creates a timer with the last time placeholder set to 0.
	 */
	public Timer() {
		lastTime = 0;
	}
	
	/**
	 * Initializes the last recorded time so that when {@link Timer#getDelta()}
	 * is called the correct elapsed time is reported.
	 */
	public void init() {
		lastTime = getTime();
	}
	
	/**
	 * Calculates the time in milliseconds since {@link Timer#init()} or 
	 * {@link Timer#getDelta()} was called. 
	 * 
	 * @return The elapsed time in milliseconds.
	 */
	public long getDelta() {
		long currentTime = getTime();
		long delta = currentTime - lastTime;
		lastTime = currentTime;
		return delta;
	}
	
	/**
	 * Gets the current system time using LWJGL's high resolution timer.
	 * 
	 * @return The current time in milliseconds.
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
}
