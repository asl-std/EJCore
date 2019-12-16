package ru.asl.api.ejcore.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import ru.asl.api.bukkit.enchant.EnchantAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.api.ejcore.value.ValueUtil;

public class ItemStackUtil {

	private static HashMap<String,ItemStack> itemsCache = new HashMap<>();

	public static boolean compareData(ItemStack i1, ItemStack i2) {
		String toCompare_1 = ItemStackUtil.toString(i1);
		String toCompare_2 = ItemStackUtil.toString(i2);

		if (toCompare_1.split(":")[1].equalsIgnoreCase(toCompare_2.split(":")[1])) return true;
		return false;
	}

	public static boolean isEquals(ItemStack stack, ItemStack eq) {
		if (stack == null || eq == null) return false;
		ItemStack one = stack.clone();
		ItemStack sec = eq.clone();

		one.setAmount(1);
		sec.setAmount(1);

		return ItemStackUtil.deserialise(one).equals(ItemStackUtil.deserialise(sec));
	}

	public static ItemStack toStack(String str) {
		return ItemStackUtil.serialise(str);
	}

	public static String toString(ItemStack stack) {
		return ItemStackUtil.deserialise(stack);
	}

	public static String getDisplayName(ItemStack stack) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_DISPLAYNAME)) return "";
		if (stack.getItemMeta().hasDisplayName()) return stack.getItemMeta().getDisplayName();
		return "";
	}

	public static ItemStack setDamage(ItemStack stack, int damage) {
		if (ItemStackUtil.hasDurability(stack.getType())) {
			ItemStack clone = stack.clone();
			ItemMeta itemMeta = clone.getItemMeta();
			((Damageable) itemMeta).setDamage(damage);
			clone.setItemMeta(itemMeta);
			return clone;
		}
		return stack;
	}

	public static ItemStack setFlags(ItemStack stack, ItemFlag... flags) {
		if (validate(stack, ValidateStatus.HAS_MATERIAL)) {
			ItemMeta meta = stack.getItemMeta();
			meta.addItemFlags(flags);
			stack.setItemMeta(meta);
		}
		return stack;
	}

	public static ItemStack removeFlags(ItemStack stack, ItemFlag... flags) {
		if (validate(stack,ValidateStatus.HAS_MATERIAL)) {
			ItemMeta meta = stack.getItemMeta();
			for (ItemFlag flag : flags)
				if (meta.hasItemFlag(flag))
					meta.removeItemFlags(flag);
		}
		return stack;
	}

	public static int getDamage(ItemStack stack) {
		if (ItemStackUtil.hasDurability(stack.getType()))
			return ((Damageable)stack.getItemMeta()).getDamage();
		return 0;
	}

	public static ItemFlag getFlagByName(String key) {
		for (ItemFlag flag : ItemFlag.values())
			if (flag.name().equalsIgnoreCase(key)) return flag;
		return null;
	}

	public static void incrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+1); }
	public static void incrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+value); }

	public static void decrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-1); }
	public static void decrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-value); }

	@SuppressWarnings("deprecation")
	public static boolean validate(ItemStack stack, ValidateStatus status) {
		if (stack != null) {
			if (status == ValidateStatus.HAS_MATERIAL) return true;
		} else return false;
		if (stack.hasItemMeta()) {
			if (status == ValidateStatus.HAS_ITEMMETA) return true;
		} else return false;

		switch (status) {
			case HAS_DISPLAYNAME:
				return stack.getItemMeta().hasDisplayName();
			case HAS_DURABILITY:
				return ItemStackUtil.hasDurability(stack.getType());
			case HAS_ENCHANTS:
				return stack.getItemMeta().hasEnchants();
			case HAS_LORE:
				return stack.getItemMeta().hasLore();
			case IS_UNBREAKABLE:
				if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_11_2))
					return stack.getItemMeta().isUnbreakable();
				//else
					//return stack.getItemMeta().spigot().isUnbreakable();
			default:
				return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static ItemStack setUnbreakable(ItemStack stack) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemStack clone = stack.clone();
		ItemMeta meta = clone.getItemMeta();

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_11_2))
			meta.setUnbreakable(true);
		//else
			//meta.spigot().setUnbreakable(true);

		clone.setItemMeta(meta);

		return clone;
	}

	public static boolean isHelmet		(Material mat) { return mat.name().toUpperCase().contains("HELMET"); 	}
	public static boolean isChestplate	(Material mat) { return mat.name().toUpperCase().contains("CHESTPLATE"); }
	public static boolean isLeggings	(Material mat) { return mat.name().toUpperCase().contains("LEGGINGS"); 	}
	public static boolean isBoots		(Material mat) { return mat.name().toUpperCase().contains("BOOTS"); 	}
	public static boolean isPickaxe		(Material mat) { return mat.name().toUpperCase().contains("PICKAXE"); 	}
	public static boolean isAxe			(Material mat) { return mat.name().toUpperCase().contains("_AXE"); 		}
	public static boolean isHoe			(Material mat) { return mat.name().toUpperCase().contains("HOE"); 		}
	public static boolean isSpade		(Material mat) { return (mat.name().toUpperCase().contains("SPADE") || mat.toString().toUpperCase().contains("SHOVEL")); }
	public static boolean isFishingRod	(Material mat) { return mat.name().toUpperCase().contains("FISHING"); 	}
	public static boolean isFlintSteel	(Material mat) { return mat.name().toUpperCase().contains("_STEEL"); 	}
	public static boolean isCarrotRod	(Material mat) { return mat.name().toUpperCase().contains("CARROT_ON"); }
	public static boolean isRanged		(Material mat) { return mat.name().toUpperCase().contains("BOW"); 		}
	public static boolean isElytra		(Material mat) { return mat.name().toUpperCase().contains("ELYTRA"); 	}
	public static boolean isShield		(Material mat) { return mat.name().toUpperCase().contains("SHIELD"); 	}
	public static boolean isSword		(Material mat) { return mat.name().toUpperCase().contains("SWORD"); 	}

	public static boolean isArmor		(Material mat) {
		return ItemStackUtil.isBoots(mat) || ItemStackUtil.isHelmet(mat) || ItemStackUtil.isChestplate(mat) || ItemStackUtil.isLeggings(mat);
	}
	public static boolean isTool		(Material mat) {
		return ItemStackUtil.isHoe(mat) || ItemStackUtil.isSpade(mat) || ItemStackUtil.isAxe(mat) || ItemStackUtil.isPickaxe(mat);
	}
	public static boolean isAnotherTool	(Material mat) {
		return ItemStackUtil.isCarrotRod(mat) || ItemStackUtil.isFlintSteel(mat) || ItemStackUtil.isFishingRod(mat);
	}
	public static boolean isWeapon		(Material mat) {
		return ItemStackUtil.isSword(mat) || ItemStackUtil.isRanged(mat);
	}
	public static boolean hasDurability (Material mat) {
		return ItemStackUtil.isWeapon(mat) || ItemStackUtil.isArmor(mat) || ItemStackUtil.isAnotherTool(mat) || ItemStackUtil.isTool(mat);
	}

	private static ItemStack serialise(String hash) {
		if (hash.equalsIgnoreCase("AIR:-1:0") || Material.getMaterial(hash.split("&")[0].split(":")[0]) == null) return new ItemStack(Material.AIR, 0);
		else {
			if (itemsCache.containsKey(hash))
				return itemsCache.get(hash);
			String[] params = new String[5];

			StringBuffer buff = new StringBuffer();
			int sBuff = 0;
			int first = 0;

			/*int[] pv = {hash.indexOf("@"), hash.indexOf("$"), hash.indexOf("%"), hash.indexOf("‼")};

			for (int val : pv)
				if (val != -1)
					if (first == -1)
						first = val;
					else
						if (val < first)
							first = val;*/


			char[] charSet = hash.substring(first).toCharArray();

			for (char ch : charSet)
				switch(ch) {
					case '♥':
						if (sBuff == 0  && params[1] == null) {
							buff = new StringBuffer();
							sBuff = 1;
						}
						else
							if (sBuff != 1) {
								params[sBuff] = buff.toString();
								buff = new StringBuffer();
								sBuff = 1;
							}
						break;
					case '♦':
						if (sBuff == 0  && params[2] == null) {
							buff = new StringBuffer();
							sBuff = 2;
						}
						else
							if (sBuff != 2) {
								params[sBuff] = buff.toString();
								buff = new StringBuffer();
								sBuff = 2;
							}
						break;
					case '♣':
						if (sBuff == 0  && params[3] == null) {
							buff = new StringBuffer();
							sBuff = 3;
						}
						else
							if (sBuff != 3) {
								params[sBuff] = buff.toString();
								buff = new StringBuffer();
								sBuff = 3;
							}
						break;
					case '♠':
						if (sBuff == 0 && params[4] == null) {
							buff = new StringBuffer();
							sBuff = 4;
						}
						else
							if (sBuff != 4) {
								params[sBuff] = buff.toString();
								buff = new StringBuffer();
								sBuff = 4;
							}
						break;
					default:
						buff.append(ch);
						params[sBuff] = buff.toString();
						break;
				}

			ItemStack item = null;
			int length = params.length;
			if (length == 0) {
				String[] opt0 = hash.split(":");
				Material mat = Material.getMaterial(opt0[0]);
				int amount = 1;
				int durability = 0;
				try {
					durability = ValueUtil.parseInteger(opt0[2]).intValue();
					amount = ValueUtil.parseInteger(opt0[1]).intValue();
					item = new ItemStack(mat, amount);
					ItemStackUtil.setDamage(item,durability);
				} catch (NumberFormatException e) {
					item = new ItemStack(mat, amount);
					ItemStackUtil.setDamage(item,durability);
				}
				return item;
			}
			if (params[0] != null) {// Material:Amount:Durability:CustomModelData
				String[] opt0 = params[0].split(":");
				Material mat = Material.getMaterial(opt0[0]);
				int amount = 1;
				int durability = 0;
				int custommodeldata = 0;
				if (opt0.length > 1)
					try { amount = ValueUtil.parseInteger(opt0[1]).intValue(); } catch (NumberFormatException e) { }
				if (opt0.length > 2)
					try { durability = ValueUtil.parseInteger(opt0[2]).intValue(); } catch (NumberFormatException e) { }
				if (opt0.length > 3)
					try { custommodeldata = ValueUtil.parseInteger(opt0[3]).intValue(); } catch (NumberFormatException e) { }

				item = new ItemStack(mat, amount);
				item = ItemStackUtil.setDamage(item, durability);
				if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13) && custommodeldata != 0) {
					ItemMeta meta = item.getItemMeta();
					meta.setCustomModelData(custommodeldata);
					item.setItemMeta(meta);
				}
			}
			if (item != null) {
				ItemMeta meta = item.getItemMeta();
				if (params[1] != null) meta.setDisplayName(EText.c(params[1]));
				if (params[2] != null) {// ♦Lore◘Lore◘Lore
					String[] opt2 = params[2].split("◘");
					List<String> lore = new ArrayList<>();
					for (String str : opt2)
						lore.add(EText.c(str));
					meta.setLore(lore);
				}
				if (params[3] != null) {// ♣Enchant:Level;Enchant:Level
					String[] opt3 = params[3].split(";");
					for (String ench : opt3) {
						String[] splited = ench.split(":");
						if (EnchantAdapter.getByKey(splited[0]) == null) continue;
						if (splited.length == 2) try {
							meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), ValueUtil.parseInteger(splited[1]).intValue(), true);
						} catch (NumberFormatException e) {
							meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), 1, true);
						}
						else meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), 1, true);
					}
				}
				if (params[4] != null) {// ♠ItemFlag;ItemFlag
					String[] opt4 = params[4].split(";");
					for (String flag : opt4) {
						if (flag.equalsIgnoreCase("unbreakable")) {
							item.setItemMeta(meta);
							item = ItemStackUtil.setUnbreakable(item);
							meta = item.getItemMeta();
							continue;
						}

						try {
							if (ItemFlag.valueOf(flag) == null) continue;
							meta.addItemFlags(ItemFlag.valueOf(flag));
						} catch (IllegalArgumentException e) {
							continue;
						}
					}
				}
				item.setItemMeta(meta);
				itemsCache.put(hash,item);
				return item;
			} else return new ItemStack(Material.AIR, 0);
		}
	}

	private static String deserialise(ItemStack stack) {
		String hash = stack == null ? "AIR:-1:0" : stack.getType().toString() + ":";
		if (stack != null) {
			int typeHash = ItemStackUtil.getDamage(stack);
			hash = hash + (typeHash > 0 ? typeHash : 0) + ":" + stack.getAmount();
			if (stack.hasItemMeta()) {
				ItemMeta meta = stack.getItemMeta();
				if (meta.hasDisplayName()) hash = hash + "♥" + meta.getDisplayName().replace('§', '&');
				boolean first = false;
				if (meta.hasLore()) {
					List<String> lore = meta.getLore();
					if (lore.size() > 0) {
						hash = hash + "♦";
						for (String str : lore)
							if (!first) { hash = hash + str.replace('§', '&'); first = true; }
							else hash = hash + "◘"+ str.replace('§', '&');
					}
				}
				first = false;
				if (meta.hasEnchants()) {
					Map<Enchantment, Integer> enchants = meta.getEnchants();
					if (enchants.size() > 0) {
						hash = hash + "♣";
						for (Enchantment ench : enchants.keySet())
							if (!first) { hash = hash + EnchantAdapter.getByKey(ench).getName() + ":" + enchants.get(ench); first = true; }
							else hash = hash + ";" + EnchantAdapter.getByKey(ench).getName() + ":" + enchants.get(ench);
					}
				}
				first = false;
				if (meta.getItemFlags() != null) {
					Set<ItemFlag> flags = meta.getItemFlags();
					if (flags.size() > 0) {
						hash = hash + "♠";
						for (ItemFlag flag : flags)
							if (!first) { hash = hash + flag.toString(); first = true; }
							else hash = hash + ";" + flag.toString();
					}
					if (meta.isUnbreakable())
						hash = hash + ";unbreakable";
				}
				first = false;
			}
		}
		return hash;
	}

	public void decreaseAmount(ItemStack stack) {
		stack.setAmount(stack.getAmount() - 1);
	}

}
