package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.Cancellable;
import com.mibactechnologies.java.system.event.Event;

public class CustomEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Integer strangeity;
    private boolean longName;

    public CustomEvent() {
	cancelled = false;
	strangeity = Integer.MAX_VALUE;
    }

    @Override
    public String getEventName() {
	return "testEvent";
    }

    public int getStrangeity() {
	return strangeity;
    }

    @Override
    public boolean isCancelled() {
	return cancelled;
    }

    public boolean isThisTestWhichProbobalyWontWork() {
	return longName;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
	this.cancelled = cancelled;
    }

}
