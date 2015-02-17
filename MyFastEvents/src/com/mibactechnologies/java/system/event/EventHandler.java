package com.mibactechnologies.java.system.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mibactechnologies.java.system.event.test.Main;

public class EventHandler implements Comparable<EventHandler> {
    private final EventListener listener;
    private final Method method;
    private final Event annotation;

    public EventHandler(final EventListener listener, final Method method,
	    final Event annotation) {
	this.listener = listener;
	this.method = method;
	this.annotation = annotation;
    }

    /*
     * @Override public int compareTo(final EventHandler other) { // Because we
     * are using a TreeSet to store EventHandlers in, compareTo // should never
     * return "equal". int annotation = this.annotation.priority() -
     * other.annotation.priority(); if (annotation == 0) annotation =
     * listener.hashCode() - other.listener.hashCode(); return annotation == 0 ?
     * hashCode() - other.hashCode() : annotation; }
     */

    @Override
    public int compareTo(final EventHandler other) {
	final int diff = annotation.priority().getIndex()
		- other.annotation.priority().getIndex();
	return diff == 0 ? 1 : diff;
    }

    public void execute(final IEvent event) throws IllegalStateException {
	try {
	    method.invoke(listener, event);
	} catch (final IllegalAccessException e1) {
	    Main.log("Exception when performing EventHandler " + listener
		    + " for event " + event.toString(), e1);
	    throw new IllegalStateException("Unable to call " + method, e1);
	} catch (final IllegalArgumentException e1) {
	    Main.log("Exception when performing EventHandler " + listener
		    + " for event " + event.toString(), e1);
	    throw new IllegalStateException("Unable to call " + method, e1);
	} catch (final InvocationTargetException e1) {
	    Main.log("Exception when performing EventHandler " + listener
		    + " for event " + event.toString(), e1);
	    throw new IllegalStateException("Unable to call " + method, e1);
	}
    }

    public Event getAnnotation() {
	return annotation;
    }

    public EventListener getListener() {
	return listener;
    }

    public Method getMethod() {
	return method;
    }

    public EventPriority getPriority() {
	return annotation.priority();
    }

    public boolean isIgnoringCancelled() {
	return annotation.ignoreCancelled();
    }

    @Override
    public String toString() {
	return "(EventHandler " + listener + ": " + method.getName() + ")";
    }
}