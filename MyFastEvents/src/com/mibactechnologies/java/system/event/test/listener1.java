package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventHandler;
import com.mibactechnologies.java.system.event.Listener;

public class listener1 implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void CustomEventTest1(final CustomEvent e) {
	System.out.println(e.getStrangeity() + "<-- strangeity : bool -->"
		+ e.isThisTestWhichProbobalyWontWork());
    }
}
