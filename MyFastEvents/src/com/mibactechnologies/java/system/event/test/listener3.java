package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.Event;
import com.mibactechnologies.java.system.event.EventListener;
import com.mibactechnologies.java.system.event.EventPriority;

public class listener3 implements EventListener {
    @Event(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onCustomEvent2(final CustomEvent2 e) {
	e.setCancelled(true);
	Main.log(e.toString() + "<-- to string");
    }
}
