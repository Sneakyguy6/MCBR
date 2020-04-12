package net.sneak.mcbr.loot.table;

import org.bukkit.Material;

public class LootItem {
	private Material material;
	private int quantity;
	private int weight;

	public LootItem(Material material, int quantity, int weight) {
		this.material = material;
		this.quantity = quantity;
		this.weight = weight;
	}

	public Material getMaterial() {
		return material;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getWeight() {
		return weight;
	}
}
