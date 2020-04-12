package net.sneak.mcbr.loot.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import net.sneak.mcbr.loot.ChestScanner;

public class LootTable {
	private List<LootItem> items;
	private int totalWeight;
	public LootTable() {
		this.items = new ArrayList<LootItem>();
		this.totalWeight = 0;
	}
	
	public void generateTable() {
		for(int x = -3; x <= 3; x =+ 6) {
			for(int z = -3; z <= 7; z += 2) {
				Chest c = (Chest) Bukkit.getWorlds().get(0).getBlockAt(x, 2, z).getState();
				Sign s = (Sign) Bukkit.getWorlds().get(0).getBlockAt((x < 0)? -2:2, 2, z).getState();
				String[] pString = s.getLine(0).split(" ");
				if(s.getLine(2).equals("binomial")) {
					String[] nString = s.getLine(1).split(" ");
					BinomialDistribution b = new BinomialDistribution(Integer.parseInt(nString[nString.length - 1]), Double.parseDouble(pString[pString.length - 1]));
					for(ItemStack i : c.getBlockInventory()) {
						for(int n = 1; n <= b.getNumberOfTrials(); n++) {
							this.items.add(new LootItem(i.getType(), n, this.totalWeight));
							this.totalWeight += (int) Math.round(b.probability(n) * 1000);
						}
					}
				} else {
					double p = Double.parseDouble(pString[pString.length - 1]);
					for(ItemStack i : c.getBlockInventory()) {
						this.items.add(new LootItem(i.getType(), 1, this.totalWeight));
						this.totalWeight += (int) Math.round(p * 1000);
					}
				}
			}
		}
	}
	
	public void generateLoot() {
		for(Location l : ChestScanner.locate()) {
			Chest c = (Chest) Bukkit.getWorlds().get(0).getBlockAt(l).getState();
			c.getInventory().clear();
			ItemStack[] itemsToAdd = new ItemStack[5];
			Random rng = new Random();
			for(int i = 0; i < 5; i++) {
				int rNumber = rng.nextInt(this.totalWeight + 1);
				int index = -1;
				while(this.items.get(index + 1).getWeight() < rNumber)
					index++;
				itemsToAdd[i] = new ItemStack(this.items.get(index).getMaterial(), this.items.get(index).getQuantity());
			}
			c.getInventory().addItem(itemsToAdd);
		}
	}
}
