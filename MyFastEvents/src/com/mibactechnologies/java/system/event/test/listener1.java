package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.Event;
import com.mibactechnologies.java.system.event.EventListener;

public class listener1 implements EventListener {
    @Event(ignoreCancelled = true)
    public void CustomEventTest1(final CustomEvent e) {
	System.out.println(e.getStrangeity() + "<-- strangeity : bool -->"
		+ e.isThisTestWhichProbobalyWontWork());
    }
}
