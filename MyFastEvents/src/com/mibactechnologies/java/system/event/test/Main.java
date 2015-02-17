package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventExecutor;
import com.mibactechnologies.java.system.event.Listener;

public class Main implements Listener {
    private static EventExecutor eventExec;

    public Main() {
	final long totalStart = System.nanoTime();
	Main.eventExec = new EventExecutor();
	Main.eventExec.setDebug(true);
	Main.eventExec.registerListener(this);
	Main.eventExec.registerListener(new listener1());
	Main.eventExec.registerListener(new listener2());
	Main.eventExec.registerListener(new listener3());
	final long start = System.nanoTime();
	Main.eventExec.callEvent(new CustomEvent());
	final long end = System.nanoTime();
	long took = end - start;
	Main.log("Run time (ms): " + took / 1000000 + "."
		+ (took + "").substring(1));

	took = end - totalStart;
	Main.log("Total run time (ms): " + took / 1000000 + "."
		+ (took + "").substring(1));
    }

    public static void log(final String s) {
	System.out.println(s);
    }

    public static void log(final String s, final Exception e) {
	System.out.println(s);
	e.printStackTrace();
    }

    public static void main(final String[] args) {
	new Main();
    }
}
