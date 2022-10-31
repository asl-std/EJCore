package org.aslstd.api.bukkit.items;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.bukkit.utils.BasicMetaAdapter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;

public class Soulbound {

	public static final String SOULBOUND_NBT = "SOULBOUND_UUID";

	@Getter @Setter private static boolean soulboundRegistered = false;

	private Player player;

	private List<ItemStack> items;

	private boolean addLore;

	private Soulbound(Player p) {
		player = p;
		items = new ArrayList<>();
	}

	public static Soulbound forPlayer(Player p) {
		return new Soulbound(p);
	}

	public Soulbound acceptItem(ItemStack item) {
		items.add(item); return this;
	}

	public Soulbound acceptItems(ItemStack... items) {
		for (final ItemStack it : items)
			this.items.add(it);
		return this;
	}

	public Soulbound addLore(boolean val) {
		addLore = val; return this;
	}

	public List<ItemStack> build() {
		final List<ItemStack> result = new ArrayList<>();
		synchronized(items) {
			for (ItemStack item : items) {
				final NBTItem ni = new NBTItem(item);

				ni.setUUID(SOULBOUND_NBT, player.getUniqueId());

				item = ni.getItem();

				if (addLore) // !TODO ADD LANGUAGE SYSTEM.
					BasicMetaAdapter.addLore(item, "");
			}
		}
		return result;
	}

	public void buildAndGive() {
		final List<ItemStack> result = build();

		synchronized(result) {
			result.forEach(i -> InventoryUtil.addItem(i, player));
		}
	}

}
