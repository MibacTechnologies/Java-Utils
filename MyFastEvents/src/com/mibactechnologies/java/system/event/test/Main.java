package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventExecutor;
import com.mibactechnologies.java.system.event.Listener;

public class Main implements Listener {
    private static EventExecutor eventExec;

    public Main() {
	final long totalStart = System.nanoTime();
	Main.eventExec = new EventExecutor();
	// Main.eventExec.setDebug(true);
	Main.eventExec.registerListener(this);
	Main.eventExec.registerListener(new listener1());
	Main.eventExec.registerListener(new listener2());
	Main.eventExec.registerListener(new listener3());
	final long start = System.nanoTime();
	Main.eventExec.callEvent(new CustomEvent());
	final long end = System.nanoTime();
	long took = end - start;
	System.out.println("Run time (event calling): " + took / 1000000 + "."
		+ (took + "").substring(1) + " [ms]");

	took = end - totalStart;
	System.out.println("Total run time: " + took / 1000000 + "."
		+ (took + "").substring(1) + " [ms]");
    }

    public static void log(final String s, final Exception e) {
	System.out.println(s);
	e.printStackTrace();
    }

    public static void main(final String[] args) {
	// final PrintStream ps = new PrintStream(System.out) {
	// @Override
	// public void println(final String x) {
	// super.println(x);
	// }
	// };

	// System.setOut(ps);

	// int cycles = 100000;
	// final long times[] = new long[cycles];

	// while (cycles > 0) {
	// final long start = System.nanoTime();
	new Main();
	// times[100000 - cycles] = System.nanoTime() - start;
	// cycles--;
	// System.out.println("Cycles left: " + cycles);
	// }

	// final long max = LongStream.of(times).max().getAsLong();
	// final long min = LongStream.of(times).min().getAsLong();
	// final double avg = LongStream.of(times).average().getAsDouble();

	// System.out.println("Max time: " + max / 1000000 + "." + (max +
	// "").substring(1)
	// + " [ms]");
	// System.out.println("Min time: " + min / 1000000 + "." + (min +
	// "").substring(1)
	// + " [ms]");
	// System.out.println("Avg time: " + avg / 1000000 + " [ms]");
    }
}
