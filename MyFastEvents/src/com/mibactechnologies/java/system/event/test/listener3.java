package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventHandler;
import com.mibactechnologies.java.system.event.EventPriority;
import com.mibactechnologies.java.system.event.Listener;

public class listener3 implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onCustomEvent2(final CustomEvent2 e) {
	e.setCancelled(true);
	// Main.log(e.toString() + "<-- to string");
    }
}
