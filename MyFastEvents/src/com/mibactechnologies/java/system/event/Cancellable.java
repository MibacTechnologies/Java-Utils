package com.mibactechnologies.java.system.event;

public interface Cancellable {
    public boolean isCancelled();

    public void setCancelled(boolean cancelled);
}
