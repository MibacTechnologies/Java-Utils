package com.mibactechnologies.java.system.event;

public class Event {
    private final String name;

    protected Event() {
	this(null);
    }

    protected Event(final String name) {
	this.name = name;
    }

    /**
     * Convenience method for providing a user-friendly identifier. By default,
     * it is the event's class's {@linkplain Class#getSimpleName() simple name}.
     *
     * @return name of this event
     */
    public String getEventName() {
	return name == null ? getClass().getSimpleName() : name;
    }
}
