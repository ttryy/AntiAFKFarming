package de.ttryy.antiafk.menu;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ttryy.antiafk.main.AntiAfkPlugin;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractItem extends ItemStack {

	private @Getter @Setter UUID uuid;
	
	protected ArrayList<String> lore = new ArrayList<>();

	public AbstractItem(Material material) {
		super(material);
		AntiAfkPlugin.getInstance().getMenuManager().addItem(this);
	}

	public abstract void exec(Player player);

}
