package com.mibactechnologies.java.system.event;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.mibactechnologies.java.system.event.test.Main;

public class EventExecutor {
    private final Map<Class<? extends Event>, Collection<EventHandlerAnnotation>> bindings;
    private final Set<Listener> registeredListeners;

    private boolean debug = false;

    private static final EventHandlerAnnotation[] EMPTYHANDLERS = {};

    public EventExecutor() {
	bindings = new HashMap<Class<? extends Event>, Collection<EventHandlerAnnotation>>();
	registeredListeners = new HashSet<Listener>();
    }

    public <T extends Event> T callEvent(final T event/* , final int i */) {
	final Collection<EventHandlerAnnotation> handlers = bindings.get(event
		.getClass());

	if (handlers == null) {
	    if (debug)
		Main.log("Event " + event.getClass().getSimpleName()
			+ " has no handlers.");
	    return event;
	}

	if (debug)
	    Main.log("Event " + event.getClass().getSimpleName() + " has "
		    + handlers.size() + " handlers.");

	final boolean cancellable = event instanceof Cancellable;
	boolean cancelled = cancellable ? ((Cancellable) event).isCancelled()
		: false;

	for (final EventHandlerAnnotation handler : handlers) {
	    cancelled = cancellable ? ((Cancellable) event).isCancelled()
		    : false;

	    if (!cancelled || cancelled
		    && handler.getAnnotation().ignoreCancelled())
		handler.execute(event);
	}

	return event;
    }

    public void clearListeners() {
	bindings.clear();
	registeredListeners.clear();
    }

    private EventHandlerAnnotation createEventHandler(final Listener listener,
	    final Method method, final EventHandler annotation) {
	return new EventHandlerAnnotation(listener, method, annotation);
    }

    public Map<Class<? extends Event>, Collection<EventHandlerAnnotation>> getBindings() {
	return new HashMap<Class<? extends Event>, Collection<EventHandlerAnnotation>>(
		bindings);
    }

    public EventHandlerAnnotation[] getListenersFor(
	    final Class<? extends Event> clazz) {
	final Collection<EventHandlerAnnotation> handlers = bindings.get(clazz);
	if (handlers == null || handlers.isEmpty())
	    return EventExecutor.EMPTYHANDLERS; // No handlers so we return an
	// empty list
	return handlers.toArray(new EventHandlerAnnotation[handlers.size()]);
    }

    public Set<Listener> getRegisteredListeners() {
	return new HashSet<Listener>(registeredListeners);
    }

    public void registerListener(final Listener listener) {
	if (debug)
	    Main.log("Register event listener: " + listener);

	if (registeredListeners.contains(listener)) {
	    if (debug)
		Main.log("Listener already registred: " + listener);
	    return;
	}

	registeredListeners.add(listener);

	final Method[] methods = listener.getClass().getDeclaredMethods();

	for (int i = 0; i < methods.length; i++) {
	    final Method method = methods[i];

	    final EventHandler annotation = method
		    .getAnnotation(EventHandler.class);
	    if (annotation == null)
		continue;

	    final Class<?>[] parameters = method.getParameterTypes();
	    if (parameters.length != 1) // all listener methods should only have
		// one parameter
		continue;

	    final Class<?> param = parameters[0];

	    if (!method.getReturnType().equals(void.class)) {
		if (debug)
		    Main.log("Ignoring method due to non-void return: "
			    + method.getName());
		continue;
	    }

	    if (Event.class.isAssignableFrom(param)) {
		@SuppressWarnings("unchecked")
		// Java just doesn't understand that this actually is a safe
		// cast because of the above if-statement
		final Class<? extends Event> realParam = (Class<? extends Event>) param;

		if (!bindings.containsKey(realParam))
		    bindings.put(realParam,
			    new TreeSet<EventHandlerAnnotation>());
		final Collection<EventHandlerAnnotation> eventHandlersForEvent = bindings
			.get(realParam);
		if (debug)
		    Main.log("Add listener method: " + method.getName()
			    + " for event " + realParam.getSimpleName());
		eventHandlersForEvent.add(createEventHandler(listener, method,
			annotation));
	    }
	}
    }

    public void removeListener(final Listener listener) {
	for (final Entry<Class<? extends Event>, Collection<EventHandlerAnnotation>> ee : bindings
		.entrySet()) {
	    final Iterator<EventHandlerAnnotation> it = ee.getValue()
		    .iterator();
	    while (it.hasNext()) {
		final EventHandlerAnnotation curr = it.next();
		if (curr.getListener() == listener)
		    it.remove();
	    }
	}
	registeredListeners.remove(listener);
    }

    public void setDebug(final boolean debug) {
	this.debug = debug;
    }
}