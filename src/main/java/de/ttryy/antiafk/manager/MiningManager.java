package de.ttryy.antiafk.manager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import de.ttryy.antiafk.listener.mining.BlockBreakListener;
import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.menu.inventorys.AntiAfkInventory;
import de.ttryy.antiafk.util.AntiAFKEvent;
import de.ttryy.antiafk.util.AntiAFKType;
import de.ttryy.antiafk.util.ColorUtil;

import lombok.Getter;

public class MiningManager extends AbstractManager {

	private @Getter AntiAfkPlugin plugin;

	private static String config = "mining";

	private @Getter int maxBlocks = 0;
	private @Getter List<Material> blockList = Lists.newArrayList();

	public MiningManager(AntiAfkPlugin plugin) {
		super();

		this.plugin = plugin;
		load();
	}

	@Override
	protected void checkPoints(Player player) {
		if (getAfkCounter().getIfPresent(player.getUniqueId()) == maxBlocks) {
			getAfkCounter().invalidate(player.getUniqueId());
			AntiAFKEvent event = new AntiAFKEvent(player, AntiAFKType.MINING);
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
		maxBlocks = plugin.getConfig().getInt(config + ".maxBlocks");
		List<String> blockStringList = plugin.getConfig().getStringList(config + ".blockList");
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
		
		blockStringList.forEach(string -> {
			if (Material.valueOf(string) != null) {
				blockList.add(Material.valueOf(string));
			}
		});

		BlockBreakListener blockListener = new BlockBreakListener(this);
		registeredListener.add(blockListener);
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
