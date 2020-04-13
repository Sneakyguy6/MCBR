package net.sneak.mcbr.enchant;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnchantInventory {
	private Inventory template;
	private Map<Material, List<Enchantment>> enchantments;
	
	public void buildInventory(Player p) {
		Inventory inventory = Bukkit.createInventory(p, this.template.getSize(), "Enchant");
		
	}
	
	private void copyTemplate(Inventory in) {
		for(int i = 0; i < in.getSize(); i++)
			in.setItem(i, in.getItem(i));
	}
}
