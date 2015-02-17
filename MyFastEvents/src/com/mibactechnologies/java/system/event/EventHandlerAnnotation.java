package com.mibactechnologies.java.system.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mibactechnologies.java.system.event.test.Main;

public class EventHandlerAnnotation implements
	Comparable<EventHandlerAnnotation> {
    private final Listener listener;
    private final Method method;
    private final EventHandler annotation;

    public EventHandlerAnnotation(final Listener listener,
	    final Method method, final EventHandler annotation) {
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
    public int compareTo(final EventHandlerAnnotation other) {
	final int diff = annotation.priority().getIndex()
		- other.annotation.priority().getIndex();
	return diff == 0 ? 1 : diff;
    }

    public void execute(final Event event) throws IllegalStateException {
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

    public EventHandler getAnnotation() {
	return annotation;
    }

    public Listener getListener() {
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