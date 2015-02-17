package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventExecutor;
import com.mibactechnologies.java.system.event.EventListener;

public class Main implements EventListener {
    private static EventExecutor eventExec;
    private final long start;
    private long took;

    public Main() {
	Main.eventExec = new EventExecutor();
	Main.eventExec.registerListener(this);
	Main.eventExec.registerListener(new listener1());
	Main.eventExec.registerListener(new listener2());
	Main.eventExec.registerListener(new listener3());
	start = System.currentTimeMillis();
	Main.eventExec.callEvent(new CustomEvent());
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

    public long getTook() {
	return took;
    }
}
