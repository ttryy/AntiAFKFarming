package de.ttryy.antiafk.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.ttryy.antiafk.listener.AbstractListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import lombok.Getter;

public abstract class AbstractManager {

	private @Getter Cache<UUID, Integer> afkCounter;
	protected ArrayList<AbstractListener> registeredListener;

	protected @Getter String inventoryTitle = new String();
	protected @Getter String itemDisplayName = new String();
	protected @Getter List<String> itemLore = Lists.newArrayList();
	protected @Getter Material itemMaterial = Material.PAPER;

	protected @Getter boolean force_interaction = false;
	protected @Getter boolean respond = true;
	protected @Getter List<String> commands = Lists.newArrayList();
	protected @Getter int time = 200;

	public AbstractManager() {
		afkCounter = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES).build();
		registeredListener = Lists.newArrayList();
	}

	/**
	 * Add one point to the player
	 * 
	 * @param player
	 *            player that get's an point
	 */
	public void addPoint(Player player) {
		int oldAFKCount = 0;
		oldAFKCount = afkCounter.getIfPresent(player.getUniqueId()) == null ? 0 : afkCounter.getIfPresent(player.getUniqueId());

		int newAFKCount = oldAFKCount == 0 ? 1 : oldAFKCount + 1;

		afkCounter.asMap().put(player.getUniqueId(), newAFKCount);
		
		checkPoints(player);
	}

	/**
	 * Checks if the player reached the maximum amount
	 */
	protected abstract void checkPoints(Player player);
	
	/**
	 * Unload all Events registered by this manager
	 */
	public void unregister() {
		registeredListener.forEach(listener -> listener.unregister());
	}

}
