package ru.asl.api.ejcore.items;

import java.util.ArrayList;
import java.util.Arrays;
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
import ru.asl.api.ejcore.value.util.ValueUtil;

// ISUv1
/**
 * <p>ItemStackUtil class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class ItemStackUtil {

	private static HashMap<String,ItemStack> itemsCache = new HashMap<>();

	/**
	 * <p>compareData.</p>
	 *
	 * @param i1 a {@link org.bukkit.inventory.ItemStack} object
	 * @param i2 a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	public static boolean compareData(ItemStack i1, ItemStack i2) {
		return ItemStackUtil.toString(i1).split(":")[1].equalsIgnoreCase(ItemStackUtil.toString(i2).split(":")[1]);
	}

	/**
	 * <p>compareDisplayName.</p>
	 *
	 * @param i1 a {@link org.bukkit.inventory.ItemStack} object
	 * @param i2 a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	public static boolean compareDisplayName(ItemStack i1, ItemStack i2) {
		return getDisplayName(i1).equals(getDisplayName(i2));
	}

	/**
	 * <p>isEquals.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param eq a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	public static boolean isEquals(ItemStack stack, ItemStack eq) {
		ItemStack one, sec;

		if (stack == null)
			one = new ItemStack(Material.AIR);
		else {
			one = stack.clone();
			if (one.getAmount() > 1) one.setAmount(1);
		}

		if (eq == null)
			sec = new ItemStack(Material.AIR);
		else {
			sec = eq.clone();
			if (sec.getAmount() > 1) sec.setAmount(1);
		}

		return ItemStackUtil.serialize(one).equals(ItemStackUtil.serialize(sec));
	}

	/**
	 * <p>toStack.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack toStack(String str) {
		return ItemStackUtil.deserialize(str);
	}

	/**
	 * <p>toString.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link java.lang.String} object
	 */
	public static String toString(ItemStack stack) {
		return ItemStackUtil.serialize(stack);
	}

	/**
	 * <p>getDisplayName.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getDisplayName(ItemStack stack) {
		if (!validate(stack, IStatus.HAS_META)) return stack.getType().name();
		if (validate(stack, IStatus.HAS_DISPLAYNAME))
			return stack.getItemMeta().getDisplayName();
		else
			return stack.getItemMeta().getLocalizedName();
	}

	/**
	 * <p>getLore.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link java.util.List} object
	 */
	public static List<String> getLore(ItemStack stack) {
		if (!validate(stack, IStatus.HAS_LORE)) return Arrays.asList("");
		else
			return stack.getItemMeta().getLore();
	}

	/**
	 * <p>setDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param damage a int
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	@SuppressWarnings("deprecation")
	public static ItemStack setDamage(ItemStack stack, int damage) {
		if (ServerVersion.isVersionAtLeast(ServerVersion.VER_1_13)) {
			stack.setDurability((short)damage);
			return stack;
		}

		if (ItemStackUtil.hasDurability(stack.getType())) {

			final ItemMeta itemMeta = stack.getItemMeta();

			((Damageable) itemMeta).setDamage(damage);

			stack.setItemMeta(itemMeta);
			return stack;
		}
		return stack;
	}

	/**
	 * <p>setFlags.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param flags a {@link org.bukkit.inventory.ItemFlag} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack setFlags(ItemStack stack, ItemFlag... flags) {
		if (validate(stack, IStatus.HAS_MATERIAL)) {
			final ItemMeta meta = stack.getItemMeta();
			meta.addItemFlags(flags);
			stack.setItemMeta(meta);
		}
		return stack;
	}

	/**
	 * <p>removeFlags.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param flags a {@link org.bukkit.inventory.ItemFlag} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack removeFlags(ItemStack stack, ItemFlag... flags) {
		if (validate(stack,IStatus.HAS_MATERIAL)) {
			final ItemMeta meta = stack.getItemMeta();
			for (final ItemFlag flag : flags)
				if (meta.hasItemFlag(flag))
					meta.removeItemFlags(flag);
		}
		return stack;
	}

	/**
	 * <p>getDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a int
	 */
	@SuppressWarnings("deprecation")
	public static int getDamage(ItemStack stack) {
		if (ServerVersion.isVersionAtLeast(ServerVersion.VER_1_13))
			return stack.getDurability();

		if (ItemStackUtil.hasDurability(stack.getType()))
			return ((Damageable)stack.getItemMeta()).getDamage();
		return 0;
	}

	/**
	 * <p>getFlagByName.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link org.bukkit.inventory.ItemFlag} object
	 */
	public static ItemFlag getFlagByName(String key) {
		for (final ItemFlag flag : ItemFlag.values())
			if (flag.name().equalsIgnoreCase(key)) return flag;
		return null;
	}

	/**
	 * <p>incrementDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static void incrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+1); }
	/**
	 * <p>incrementDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param value a int
	 */
	public static void incrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+value); }

	/**
	 * <p>decrementDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static void decrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-1); }
	/**
	 * <p>decrementDamage.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param value a int
	 */
	public static void decrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-value); }

	/**
	 * <p>validate.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param status a {@link ru.asl.api.ejcore.items.IStatus} object
	 * @return a boolean
	 */
	public static boolean validate(ItemStack stack, IStatus status) {
		if (stack != null && stack.getType() != Material.AIR) {
			if (status == IStatus.HAS_MATERIAL) return true;
		} else return false;

		if (stack.hasItemMeta()) {
			if (status == IStatus.HAS_META) return true;
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

	/**
	 * <p>setCustomModelData.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param data a int
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack setCustomModelData(ItemStack stack, int data) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_14))
			meta.setCustomModelData(data);

		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * <p>setUnbreakable.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param arg a boolean
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack setUnbreakable(ItemStack stack, boolean arg) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_11_2))
			meta.setUnbreakable(arg);
		//else
		//meta.spigot().setUnbreakable(true);

		stack.setItemMeta(meta);

		return stack;
	}

	/**
	 * <p>isHelmet.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isHelmet		(Material mat) { return mat.name().toUpperCase().contains("HELMET"); 	}
	/**
	 * <p>isChestplate.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isChestplate	(Material mat) { return mat.name().toUpperCase().contains("CHESTPLATE"); }
	/**
	 * <p>isLeggings.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isLeggings	(Material mat) { return mat.name().toUpperCase().contains("LEGGINGS"); 	}
	/**
	 * <p>isBoots.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isBoots		(Material mat) { return mat.name().toUpperCase().contains("BOOTS"); 	}
	/**
	 * <p>isPickaxe.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isPickaxe		(Material mat) { return mat.name().toUpperCase().contains("PICKAXE"); 	}
	/**
	 * <p>isAxe.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isAxe			(Material mat) { return mat.name().toUpperCase().contains("_AXE"); 		}
	/**
	 * <p>isHoe.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isHoe			(Material mat) { return mat.name().toUpperCase().contains("HOE"); 		}
	/**
	 * <p>isSpade.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isSpade		(Material mat) { return (mat.name().toUpperCase().contains("SPADE") || mat.toString().toUpperCase().contains("SHOVEL")); }
	/**
	 * <p>isFishingRod.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isFishingRod	(Material mat) { return mat.name().toUpperCase().contains("FISHING"); 	}
	/**
	 * <p>isFlintSteel.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isFlintSteel	(Material mat) { return mat.name().toUpperCase().contains("_STEEL"); 	}
	/**
	 * <p>isCarrotRod.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isCarrotRod	(Material mat) { return mat.name().toUpperCase().contains("CARROT_ON"); }
	/**
	 * <p>isRanged.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isRanged		(Material mat) { return mat.name().toUpperCase().contains("BOW"); 		}
	/**
	 * <p>isElytra.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isElytra		(Material mat) { return mat.name().toUpperCase().contains("ELYTRA"); 	}
	/**
	 * <p>isShield.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isShield		(Material mat) { return mat.name().toUpperCase().contains("SHIELD"); 	}
	/**
	 * <p>isSword.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isSword		(Material mat) { return mat.name().toUpperCase().contains("SWORD"); 	}

	/**
	 * <p>isArmor.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isArmor		(Material mat) {
		return ItemStackUtil.isBoots(mat) || ItemStackUtil.isHelmet(mat) || ItemStackUtil.isChestplate(mat) || ItemStackUtil.isLeggings(mat);
	}
	/**
	 * <p>isTool.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isTool		(Material mat) {
		return ItemStackUtil.isHoe(mat) || ItemStackUtil.isSpade(mat) || ItemStackUtil.isAxe(mat) || ItemStackUtil.isPickaxe(mat);
	}
	/**
	 * <p>isAnotherTool.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isAnotherTool	(Material mat) {
		return ItemStackUtil.isCarrotRod(mat) || ItemStackUtil.isFlintSteel(mat) || ItemStackUtil.isFishingRod(mat);
	}
	/**
	 * <p>isWeapon.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean isWeapon		(Material mat) {
		return ItemStackUtil.isSword(mat) || ItemStackUtil.isRanged(mat);
	}
	/**
	 * <p>hasDurability.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public static boolean hasDurability (Material mat) {
		return ItemStackUtil.isWeapon(mat) || ItemStackUtil.isArmor(mat) || ItemStackUtil.isAnotherTool(mat) || ItemStackUtil.isTool(mat);
	}

	private static ItemStack deserialize(String hash) {
		if (hash.equalsIgnoreCase("AIR:-1:0") || Material.getMaterial(hash.split("&")[0].split(":")[0]) == null) return new ItemStack(Material.AIR, 0);
		else {
			if (itemsCache.containsKey(hash))
				return itemsCache.get(hash);
			final String[] params = new String[5];

			StringBuffer buff = new StringBuffer();
			int id = 0;
			int idn = 0;

			final char[] charSet = hash.toCharArray();

			for (final char ch : charSet) {
				if (ch == '♥') { idn = 1; }
				if (ch == '♦') { idn = 2; }
				if (ch == '♣') { idn = 3; }
				if (ch == '♠') { idn = 4; }

				if (id != idn) {
					if (params[id] == null)
						params[id] = buff.toString();
					if (params[idn] == null)
						id = idn;
					buff = new StringBuffer();
					continue;
				}

				buff.append(ch);
			}
			params[id] = buff.toString();

			ItemStack item = new ItemStack(Material.AIR, 0);
			final int length = params.length;
			if (length == 0) {
				final String[] opt0 = hash.split(":");
				final Material mat = Material.getMaterial(opt0[0]);
				int amount = 1;
				int durability = 0;
				try {
					durability = ValueUtil.parseInteger(opt0[2]);
					amount = ValueUtil.parseInteger(opt0[1]);
					item = new ItemStack(mat, amount);
					ItemStackUtil.setDamage(item,durability);
				} catch (final NumberFormatException e) {
					item = new ItemStack(mat, amount);
					ItemStackUtil.setDamage(item,durability);
				}
				return item;
			}
			if (params[0] != null) {// Material:Amount:Durability:CustomModelData
				final String[] opt0 = params[0].split(":");
				final Material mat = Material.getMaterial(opt0[0]);
				int amount = 1;
				int durability = 0;
				int custommodeldata = 0;
				if (opt0.length > 1)
					try { amount = ValueUtil.parseInteger(opt0[1]); } catch (final NumberFormatException e) { }
				if (opt0.length > 2)
					try { durability = ValueUtil.parseInteger(opt0[2]); } catch (final NumberFormatException e) { }
				if (opt0.length > 3)
					try { custommodeldata = ValueUtil.parseInteger(opt0[3]); } catch (final NumberFormatException e) { }

				item = new ItemStack(mat, amount);
				item = ItemStackUtil.setDamage(item, durability);
				if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_14) && custommodeldata != 0) {
					final ItemMeta meta = item.getItemMeta();
					meta.setCustomModelData(custommodeldata);
					item.setItemMeta(meta);
				}
			}
			if (item != null) {
				ItemMeta meta = item.getItemMeta();
				if (params[1] != null) meta.setDisplayName(EText.c(params[1]));
				if (params[2] != null) {// ♦Lore◘Lore◘Lore
					final String[] opt2 = params[2].split("◘");
					final List<String> lore = new ArrayList<>();
					for (final String str : opt2)
						lore.add(EText.c(str));
					meta.setLore(lore);
				}
				if (params[3] != null) {// ♣Enchant:Level;Enchant:Level
					final String[] opt3 = params[3].split(";");
					for (final String ench : opt3) {
						final String[] splited = ench.split(":");
						if (EnchantAdapter.getByKey(splited[0]) == null) continue;
						if (splited.length == 2) try {
							meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), ValueUtil.parseInteger(splited[1]), true);
						} catch (final NumberFormatException e) {
							meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), 1, true);
						}
						else meta.addEnchant(EnchantAdapter.getByKey(splited[0]).toEnchant(), 1, true);
					}
				}
				if (params[4] != null) {// ♠ItemFlag;ItemFlag
					final String[] opt4 = params[4].split(";");
					for (final String flag : opt4) {
						if (flag.equalsIgnoreCase("unbreakable")) {
							item.setItemMeta(meta);
							item = ItemStackUtil.setUnbreakable(item, true);
							meta = item.getItemMeta();
							continue;
						}

						try {
							meta.addItemFlags(ItemFlag.valueOf(flag));
						} catch (final IllegalArgumentException e) {
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

	private static String serialize(ItemStack stack) {
		String hash = stack == null ? "AIR:0:0" : stack.getType().toString() + ":";
		if (stack != null) {
			final int typeHash = ItemStackUtil.getDamage(stack);
			hash = hash + stack.getAmount() + ":" + (typeHash > 0 ? typeHash : 0);
			if (stack.hasItemMeta()) {
				final ItemMeta meta = stack.getItemMeta();
				if (meta.hasDisplayName()) hash = hash + "♥" + meta.getDisplayName().replace('§', '&');
				boolean first = false;
				if (meta.hasLore()) {
					final List<String> lore = meta.getLore();
					if (lore.size() > 0) {
						hash = hash + "♦";
						for (final String str : lore)
							if (!first) { hash = hash + str.replace('§', '&'); first = true; }
							else hash = hash + "◘"+ str.replace('§', '&');
					}
				}
				first = false;
				if (meta.hasEnchants()) {
					final Map<Enchantment, Integer> enchants = meta.getEnchants();
					if (enchants.size() > 0) {
						hash = hash + "♣";
						for (final Enchantment ench : enchants.keySet()) {
							final EnchantAdapter e = EnchantAdapter.getByKey(ench);
							if (e == null) continue;
							if (!first) { hash = hash + e.getName() + ":" + enchants.get(ench); first = true; }
							else hash = hash + ";" + e.getName() + ":" + enchants.get(ench);
						}
					}
				}
				first = false;
				if (meta.getItemFlags() != null) {
					final Set<ItemFlag> flags = meta.getItemFlags();
					if (flags.size() > 0) {
						hash = hash + "♠";
						for (final ItemFlag flag : flags)
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

	/**
	 * <p>decreaseAmount.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void decreaseAmount(ItemStack stack) {
		stack.setAmount(stack.getAmount() - 1);
	}

}
