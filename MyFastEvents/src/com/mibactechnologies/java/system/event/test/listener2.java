package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.Event;
import com.mibactechnologies.java.system.event.EventListener;
import com.mibactechnologies.java.system.event.EventPriority;

public class listener2 implements EventListener {
    @Event(priority = EventPriority.NORMAL)
    public void CustomEventTest1(final CustomEvent e) {
	// /e.setCancelled(true);
	Main.log(e.isCancelled() + "!");
    }
}
