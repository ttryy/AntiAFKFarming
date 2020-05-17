package de.ttryy.antiafk.menu.inventorys;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.manager.AbstractManager;
import de.ttryy.antiafk.menu.AbstractInventory;
import de.ttryy.antiafk.menu.AbstractItem;
import de.ttryy.antiafk.menu.items.AntiAfkItem;

import lombok.Setter;

public class AntiAfkInventory extends AbstractInventory {

	private AbstractManager manager;

	private @Setter boolean clicked = false;

	public AntiAfkInventory(AbstractManager manager, Player player) {
		super(player, InventoryType.HOPPER, manager.getInventoryTitle());
		
		this.manager = manager;
		run();
	}

	@Override
	protected void loadInventoryContent(Map<Integer, AbstractItem> content) {
		for(int i = 0; i < getInventory().getSize(); i++){
			content.put(i, new AntiAfkItem(this, manager.getItemMaterial(), manager.getItemDisplayName(), manager.getItemLore()));
		}
	}

	private void run() {
		AntiAfkPlugin.getInstance().getNeedToRespond().put(player.getUniqueId(), manager.isForce_interaction());
		if (manager.isRespond()) {
			Bukkit.getScheduler().runTaskLater(AntiAfkPlugin.getInstance(), () -> {
				
				if (!clicked && AntiAfkPlugin.getInstance().getNeedToRespond().containsKey(player.getUniqueId())) {
					AntiAfkPlugin.getInstance().getNeedToRespond().remove(player.getUniqueId());
					player.closeInventory();
					manager.getCommands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							command.replace("!PLAYER!", player.getName())));
				}

			}, manager.getTime());
		}

	}

}
