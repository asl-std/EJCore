package org.aslstd.api.bukkit.entity.pick;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import lombok.Getter;

public class UPlayer extends UEntity {

	@Getter protected Player player;

	public UPlayer(Player player) {
		super(player);
		this.player = player;

		defaults();
	}

	private void defaults() {
		player.setHealthScaled(true);
	}

	@Override public String displayName() { return player.getDisplayName(); }

	public String name() { return player.getName(); }

	public void send(String message) { EText.send(player, message); }

	public void execute(Consumer<Player> function) { function.accept(player); }

	public void dispatch(String command) { Bukkit.dispatchCommand(player, command); }

	@Override public ItemStack hand() { return player.getInventory().getItemInMainHand(); }

	@Override public ItemStack offhand() { return player.getInventory().getItemInOffHand(); }

	@Override public ItemStack head() { return player.getInventory().getHelmet(); }

	@Override public ItemStack body() { return player.getInventory().getChestplate(); }

	@Override public ItemStack leggs() { return player.getInventory().getLeggings(); }

	@Override public ItemStack foots() { return player.getInventory().getBoots(); }

	@Override
	protected void healthChanged(double newValue) {
		if (Core.config().ONE_HP_BAR)
			player.setHealthScale(newValue / Core.config().HEALTH_PER_BAR * 20.0D);
		else
			player.setHealthScale(20.0D);
	}

}
