package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.EventHandler;
import com.mibactechnologies.java.system.event.Listener;
import com.mibactechnologies.java.system.event.EventPriority;

public class listener2 implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void CustomEventTest1(final CustomEvent e) {
	// /e.setCancelled(true);
	Main.log(e.isCancelled() + "!");
    }
}
