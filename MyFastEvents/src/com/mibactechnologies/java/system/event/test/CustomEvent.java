package com.mibactechnologies.java.system.event.test;

import com.mibactechnologies.java.system.event.Cancellable;
import com.mibactechnologies.java.system.event.IEvent;

public class CustomEvent implements IEvent, Cancellable {
    private boolean cancelled;

    public CustomEvent() {
	cancelled = false;
    }

    public int getStrangeity() {
	return Integer.MAX_VALUE;
    }

    @Override
    public boolean isCancelled() {
	return cancelled;
    }

    public boolean isThisTestWhichProbobalyWontWork() {
	return true;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
	this.cancelled = cancelled;
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + ", cancelled=" + cancelled;
    }
}
