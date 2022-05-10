package ru.asl.api.ejcore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.items.IStatus;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejcore.value.util.ValueUtil;

/**
 * Will be included in ItemStackUtil in future
 */
public final class BasicMetaAdapter { // Basic Lore Adapter

	private static Matcher matcher;

	public static @Nonnull String getStringValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, IStatus.HAS_LORE)) return "";
		return getStringValue(patt, stack.getItemMeta().getLore());
	}

	public static @Nonnull String getStringValue(Pattern patt, List<String> lore) {
		String value = "";
		if (contains(lore, patt)) {
			matcher = patt.matcher(EText.e(lore.toString()).toLowerCase());
			if (matcher.find())
				value = matcher.group(1);
		}

		return value == null ? "" : value;
	}

	public static ItemStack addLore(ItemStack stack, String... strings) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();
		List<String> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.getLore();

		for (final String str : strings)
			lore.add(EText.c(str));

		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setLore(ItemStack stack, List<String> lore) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL) || lore.isEmpty()) return stack;
		final ItemMeta meta = stack.getItemMeta();
		meta.setLore(lore);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setLore(ItemStack stack, int index, String loreString) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();
		List<String> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.getLore();

		if (lore.size()-1 > index)
			lore.set(index, EText.c(loreString));
		else
			lore.add(EText.c(loreString));

		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * @see ItemStackUtil#setFlags(ItemStack, ItemFlag...)
	 */
	@Deprecated
	public static ItemStack setHideFlags(ItemStack stack, ItemFlag... flags) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		for (final ItemFlag flag : flags)
			if (meta.hasItemFlag(flag))
				continue;
			else
				meta.addItemFlags(flag);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack addEnchant(ItemStack stack, Enchantment ench, int lvl, boolean ignoreLvl) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		if (meta.hasEnchant(ench))
			meta.removeEnchant(ench);

		meta.addEnchant(ench, lvl, ignoreLvl);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setDisplayName(ItemStack stack, String display) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();
		if (display != null)
			meta.setDisplayName(EText.c(display));
		else
			meta.setDisplayName(null);
		stack.setItemMeta(meta);
		return stack;
	}

	public static String getStringValue(Pattern patt, String loreString) {
		String value = "";

		if (contains(loreString, patt)) {
			matcher = patt.matcher(EText.e(loreString).toLowerCase());
			if (matcher.find()) value = matcher.group(1);
		}

		return value;
	}

	public static String getLore(String key, String value) {
		return EText.c(key + ": " + value);
	}

	public static double getDoubleValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, IStatus.HAS_LORE)) return 0.0D;
		return getDoubleValue(patt, stack.getItemMeta().getLore());
	}

	public static double getDoubleValue(Pattern patt, List<String> lore) {
		String value = "0.0D";

		if (contains(lore, patt)) {
			matcher = patt.matcher(EText.e(lore.toString()).toLowerCase());
			if (matcher.find())
				value = matcher.group(1);
		}

		return ValueUtil.parseDouble(value.replaceAll("%", ""));
	}

	public static boolean contains(String loreString, Pattern patt) { return patt.matcher(EText.e(loreString).toLowerCase()).find(); }
	public static boolean contains(List<String> lore, Pattern patt) { return patt.matcher(EText.e(lore.toString()).toLowerCase()).find(); }
	public static boolean contains(ItemStack item, Pattern patt) {
		if (!ItemStackUtil.validate(item, IStatus.HAS_LORE)) return false;
		return contains(item.getItemMeta().getLore(), patt);
	}

	public static int indexOf(List<String> lore, Pattern patt) {
		Matcher m;

		for (int l = 0 ; l < lore.size() ; l++) {
			m = patt.matcher(EText.e(lore.get(l).toLowerCase()));

			if (m.find()) return l;
		}

		return -1;
	}

	public static int indexOf(List<String> lore, String check) {
		for (int i = 0 ; i < lore.size() ; i++) {
			if (EText.e(lore.get(i)).startsWith(EText.e(check)))
				return i;
		}

		return -1;
	}

	public static boolean isPercent(List<String> lore, Pattern patt) {
		matcher = patt.matcher(lore.toString().toLowerCase());
		if (matcher.find())
			if (matcher.group(1) != null)
				return matcher.group(1).contains("%");
		return false;
	}

}
