package de.ttryy.antiafk.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class AntiAFKEvent extends Event implements Cancellable{

	private final @Getter Player player;
	private final @Getter AntiAFKType type;
	private static HandlerList handlers = new HandlerList();
	private @Getter @Setter boolean cancelled;
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
