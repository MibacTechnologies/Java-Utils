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
    private final Map<Class<? extends IEvent>, Collection<EventHandler>> bindings;
    private final Set<EventListener> registeredListeners;

    private boolean debug = false;

    private static final EventHandler[] EMPTYHANDLERS = {};

    public EventExecutor() {
	bindings = new HashMap<Class<? extends IEvent>, Collection<EventHandler>>();
	registeredListeners = new HashSet<EventListener>();
    }

    public <T extends IEvent> T callEvent(final T event/* , final int i */) {
	final Collection<EventHandler> handlers = bindings
		.get(event.getClass());

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

	for (final EventHandler handler : handlers) {
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

    private EventHandler createEventHandler(final EventListener listener,
	    final Method method, final Event annotation) {
	return new EventHandler(listener, method, annotation);
    }

    public Map<Class<? extends IEvent>, Collection<EventHandler>> getBindings() {
	return new HashMap<Class<? extends IEvent>, Collection<EventHandler>>(
		bindings);
    }

    public EventHandler[] getListenersFor(final Class<? extends IEvent> clazz) {
	final Collection<EventHandler> handlers = bindings.get(clazz);
	if (handlers == null || handlers.isEmpty())
	    return EventExecutor.EMPTYHANDLERS; // No handlers so we return an
	// empty list
	return handlers.toArray(new EventHandler[handlers.size()]);
    }

    public Set<EventListener> getRegisteredListeners() {
	return new HashSet<EventListener>(registeredListeners);
    }

    public void registerListener(final EventListener listener) {
	if (debug)
	    Main.log("Register event listener: " + listener);

	if (registeredListeners.contains(listener)) {
	    if (debug)
		Main.log("Listener already registred: " + listener);
	    return;
	}

	registeredListeners.add(listener);

	final Method[] methods = listener.getClass().getDeclaredMethods();

	for (final Method method : methods) {
	    final Event annotation = method.getAnnotation(Event.class);
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

	    if (IEvent.class.isAssignableFrom(param)) {
		@SuppressWarnings("unchecked")
		// Java just doesn't understand that this actually is a safe
		// cast because of the above if-statement
		final Class<? extends IEvent> realParam = (Class<? extends IEvent>) param;

		if (!bindings.containsKey(realParam))
		    bindings.put(realParam, new TreeSet<EventHandler>());
		final Collection<EventHandler> eventHandlersForEvent = bindings
			.get(realParam);
		if (debug)
		    Main.log("Add listener method: " + method.getName()
			    + " for event " + realParam.getSimpleName());
		eventHandlersForEvent.add(createEventHandler(listener, method,
			annotation));
	    }
	}
    }

    public void removeListener(final EventListener listener) {
	for (final Entry<Class<? extends IEvent>, Collection<EventHandler>> ee : bindings
		.entrySet()) {
	    final Iterator<EventHandler> it = ee.getValue().iterator();
	    while (it.hasNext()) {
		final EventHandler curr = it.next();
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