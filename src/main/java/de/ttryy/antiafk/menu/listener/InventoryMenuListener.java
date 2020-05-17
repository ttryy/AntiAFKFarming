package de.ttryy.antiafk.menu.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.menu.AbstractInventory;
import de.ttryy.antiafk.menu.AbstractItem;

public class InventoryMenuListener implements Listener {

	private AntiAfkPlugin plugin;

	public InventoryMenuListener(AntiAfkPlugin plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventory(InventoryClickEvent event) {
		if (plugin.getMenuManager().containsInventory(event.getInventory())) {

			event.setCancelled(true);

			Inventory inventory = event.getInventory();

			ItemStack clickedItem;
			try {
				clickedItem = event.getClickedInventory().getItem(event.getSlot());
			} catch (Exception e) {
				return;
			}

			if (plugin.getMenuManager().containsItem(clickedItem)) {
				AbstractItem item = plugin.getMenuManager().getItem(clickedItem);
				item.exec((Player) event.getWhoClicked());
				inventory.setItem(event.getRawSlot(), item);
			}

		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (plugin.getMenuManager().containsInventory(event.getInventory())) {

			if (plugin.getNeedToRespond().containsKey(event.getPlayer().getUniqueId()) && plugin.getNeedToRespond().get(event.getPlayer().getUniqueId())) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						event.getPlayer().openInventory(event.getInventory());

					}
				}, 1);
				return;
			}

			AbstractInventory invMenu = plugin.getMenuManager().getInventory(event.getInventory());

			plugin.getMenuManager().removeItems(invMenu);
			plugin.getMenuManager().removeInventory(invMenu);

		}

	}

}
