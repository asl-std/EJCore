package org.aslstd.api.bukkit.entity.pick;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UEntity {

	@Getter
	protected LivingEntity entity;

	public String displayName() { return entity.getCustomName() != null ? entity.getCustomName() : entity.getType().name(); }

	public ItemStack hand() { return entity.getEquipment().getItemInMainHand(); }

	public ItemStack offhand() { return entity.getEquipment().getItemInOffHand(); }

	public ItemStack head() { return entity.getEquipment().getHelmet(); }

	public ItemStack body() { return entity.getEquipment().getChestplate(); }

	public ItemStack leggs() { return entity.getEquipment().getLeggings(); }

	public ItemStack foots() { return entity.getEquipment().getBoots(); }


}
