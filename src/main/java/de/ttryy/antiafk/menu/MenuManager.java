package de.ttryy.antiafk.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.ttryy.antiafk.menu.listener.InventoryMenuListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.ttryy.antiafk.main.AntiAfkPlugin;

import lombok.Getter;

public class MenuManager {
	
	@SuppressWarnings("unused")
	private AntiAfkPlugin plugin;
	
	private @Getter Map<UUID, AbstractInventory> menuInventorys = new HashMap<>();
	private @Getter Map<UUID, AbstractItem> menuItems = new HashMap<>();
	
	public MenuManager(AntiAfkPlugin plugin) {
		this.plugin = plugin;
		
		new InventoryMenuListener(plugin);
	}
	
	public void addItem( AbstractItem menuItem ){
		UUID uuid = UUID.randomUUID();
		menuItems.put(uuid, menuItem);
		menuItem.setUuid(uuid);
	}

	public boolean containsItem(ItemStack item) {
		for ( ItemStack menuItem : menuItems.values() ) {
			if ( menuItem.equals( item ) ) {
				return true;
			}
		}
		
		return false;

	}

	public AbstractItem getItem(ItemStack item) {
		for(AbstractItem items : menuItems.values()){
			if(((ItemStack) items).equals(item)){
				return items;
			}
		}
		
		return null;
	}
	
	public void removeItem( AbstractItem button ) {
		if ( menuItems.containsValue( button ) ) {
			menuItems.remove( button.getUuid() );
		}
	}
	
	public void addInventory( AbstractInventory menuInv ){
		UUID uuid = UUID.randomUUID();
		menuInventorys.put(uuid, menuInv);
		menuInv.setUuid(uuid);
	}

	public boolean containsInventory(Inventory inventory) {
		for(AbstractInventory invs : menuInventorys.values()){
			if(invs.getInventory().equals(inventory)){
				return true;
			}
		}
		return false;
	}

	public AbstractInventory getInventory(Inventory inventory) {
		for(AbstractInventory invs : menuInventorys.values()){
			if(invs.getInventory().equals(inventory)){
				return invs;
			}
		}
		
		return null;
	}

	public void removeItems(AbstractInventory invMenu) {
		for(AbstractItem item : invMenu.getContent().values()){
			removeItem(item);
		}
		
	}

	public void removeInventory( AbstractInventory invMenu ) {
		if ( menuInventorys.containsValue( invMenu ) ) {
			menuInventorys.remove( invMenu.getUuid() );
		}
	}

	
	

}
