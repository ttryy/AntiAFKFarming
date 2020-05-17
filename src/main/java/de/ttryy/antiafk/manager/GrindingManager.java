package de.ttryy.antiafk.manager;

import de.ttryy.antiafk.listener.grinding.EntityDeathListener;
import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.menu.inventorys.AntiAfkInventory;
import de.ttryy.antiafk.util.AntiAFKEvent;
import de.ttryy.antiafk.util.AntiAFKType;
import de.ttryy.antiafk.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import lombok.Getter;

public class GrindingManager extends AbstractManager {

	private @Getter
	AntiAfkPlugin plugin;

	private static String config = "grinding";

	private @Getter int maxMob = 0;

	public GrindingManager(AntiAfkPlugin plugin) {
		super();
		this.plugin = plugin;

		load();
	}

	@Override
	protected void checkPoints(Player player) {
		if (getAfkCounter().getIfPresent(player.getUniqueId()) == maxMob) {
			getAfkCounter().invalidate(player.getUniqueId());
			AntiAFKEvent event = new AntiAFKEvent(player, AntiAFKType.GRINDING);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				new AntiAfkInventory(this, player);
			}
		}
	}

	/**
	 * Load all config data and register needed Events
	 */
	private void load() {
		maxMob = plugin.getConfig().getInt(config + ".maxMobs");
		inventoryTitle = ColorUtil.alternateChatColor(plugin.getConfig().getString(config + ".inventory_title"));
		itemDisplayName = ColorUtil.alternateChatColor(plugin.getConfig().getString(config + ".item_displayname"));
		itemLore = plugin.getConfig().getStringList(config + ".item_lore");
		Material material = Material.valueOf(plugin.getConfig().getString(config + ".item_material"));
		itemMaterial = material == null ? Material.PAPER : material;
		respond = plugin.getConfig().getBoolean(config + ".respond");
		force_interaction = respond ? true : plugin.getConfig().getBoolean(config + ".force_interaction");
		commands = plugin.getConfig().getStringList(config + ".commands");
		time = plugin.getConfig().getInt(config + ".time");

		for(int i = 0; i < itemLore.size(); i++){
			String lore = itemLore.get(i);
			itemLore.remove(i);
			itemLore.add(ColorUtil.alternateChatColor(lore));
		}
		
		EntityDeathListener entityListener = new EntityDeathListener(this);
		registeredListener.add(entityListener);
	}

	/**
	 * Returns a boolean indicating if the manager is enabled in config
	 * 
	 * @return if the Manager is enabled in config
	 */
	public static boolean isEnabled() {
		return AntiAfkPlugin.getInstance().getConfig().getBoolean(config + ".enabled");
	}

}
