package com.mibactechnologies.java.system.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indication that a method should handle an {@link IEvent}<br>
 * The method needs to return {@link Void} and expect exactly one parameter of a
 * type that implements {@link IEvent}.<br>
 * Only methods in classes that implements {@link EventListener} should use this
 * annotation.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Event {
    boolean ignoreCancelled() default false;

    EventPriority priority() default EventPriority.NORMAL;

    // int priority() default 50;
}