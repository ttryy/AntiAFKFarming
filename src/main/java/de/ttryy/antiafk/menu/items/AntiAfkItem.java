package de.ttryy.antiafk.menu.items;

import java.util.List;

import de.ttryy.antiafk.menu.inventorys.AntiAfkInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.menu.AbstractItem;

public class AntiAfkItem extends AbstractItem{

	private AntiAfkInventory inventory;
	
	public AntiAfkItem(AntiAfkInventory inventory, Material material, String displayName, List<String> lore) {
		super(material);

		ItemMeta meta = getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		setItemMeta(meta);
		
		this.inventory = inventory;
	}

	@Override
	public void exec(Player player) {
		if(AntiAfkPlugin.getInstance().getNeedToRespond().containsKey(player.getUniqueId())){
			AntiAfkPlugin.getInstance().getNeedToRespond().remove(player.getUniqueId());
		}
		inventory.setClicked(true);
		player.closeInventory();
	}
	
	

}
