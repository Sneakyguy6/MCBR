package net.sneak.mcbr.loot.table;

import org.bukkit.inventory.ItemStack;

public class LootItem {
	private ItemStack material;
	private int quantity;
	private int weight;

	public LootItem(ItemStack material, int quantity, int weight) {
		this.material = material;
		this.quantity = quantity;
		this.weight = weight;
	}

	public ItemStack getMaterial() {
		return material;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getWeight() {
		return weight;
	}
}
