package de.ttryy.antiafk.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.ttryy.antiafk.main.AntiAfkPlugin;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractInventory {

	private @Getter @Setter UUID uuid;

	private @Getter final String menuName;
	private @Getter final Inventory inventory;
	protected final Player player;

	private @Getter Map<Integer, AbstractItem> content = new HashMap<>();

	public AbstractInventory(Player player, int size, String menuName) {
		this.player = player;
		this.menuName = menuName;
		this.inventory = Bukkit.createInventory(null, size, menuName);

		loadInventory();
	}
	
	public AbstractInventory(Player player, InventoryType type, String menuName) {
		this.player = player;
		this.menuName = menuName;
		this.inventory = Bukkit.createInventory(null, type, menuName);

		loadInventory();
	}

	private void loadInventory() {
		AntiAfkPlugin.getInstance().getMenuManager().addInventory(this);
		
		Bukkit.getScheduler().runTaskLater(AntiAfkPlugin.getInstance(), () -> {
			loadInventoryContent(content);
			putItemsInInventory();
			player.openInventory(inventory);
		}, 1);
	}

	protected abstract void loadInventoryContent(Map<Integer, AbstractItem> content);

	private void putItemsInInventory() {
		content.keySet().forEach(i -> {
			if(!(i > inventory.getSize())){
				inventory.setItem(i, content.get(i));
			}
		});
	}

}
