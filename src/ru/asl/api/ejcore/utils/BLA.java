package ru.asl.api.ejcore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejcore.items.ValidateStatus;
import ru.asl.api.ejcore.value.ValueUtil;
/**
 * Will be included in ItemStackUtil in future
 */
public class BLA { // Basic Lore Adapter

	private static Matcher matcher;

	public static String getStringValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, ValidateStatus.HAS_LORE)) return "";
		return BLA.getStringValue(patt, stack.getItemMeta().getLore());
	}

	public static String getStringValue(Pattern patt, List<String> lore) {
		String value = "";

        if (BLA.contains(lore, patt)) {
			BLA.matcher = patt.matcher(EText.e(lore.toString()).toLowerCase());
			if (BLA.matcher.find()) value = BLA.matcher.group(1);
        }

		return value;
	}

	public static ItemStack addLore(ItemStack stack, String... strings) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemMeta meta = stack.getItemMeta();
		List<String> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.getLore();

		for (String str : strings)
			lore.add(EText.c(str));

		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setLore(ItemStack stack, int index, String loreString) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemMeta meta = stack.getItemMeta();
		List<String> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.getLore();

		if (lore.size()-1 > index)
			lore.set(index, EText.c(loreString));
		else
			lore.add(EText.c(loreString));
		return stack;
	}

	/**
	 * @see ItemStackUtil#setFlags(ItemStack, ItemFlag...)
	 */
	@Deprecated
	public static ItemStack setHideFlags(ItemStack stack, ItemFlag... flags) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemMeta meta = stack.getItemMeta();

		for (ItemFlag flag : flags)
			if (meta.hasItemFlag(flag))
				continue;
			else
				meta.addItemFlags(flag);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack addEnchant(ItemStack stack, Enchantment ench, int lvl, boolean ignoreLvl) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemMeta meta = stack.getItemMeta();

		if (meta.hasEnchant(ench))
			meta.removeEnchant(ench);

		meta.addEnchant(ench, lvl, ignoreLvl);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setDisplayName(ItemStack stack, String display) {
		if (!ItemStackUtil.validate(stack, ValidateStatus.HAS_MATERIAL)) return stack;
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(EText.c(display));
		stack.setItemMeta(meta);
		return stack;
	}

	public static String getStringValue(Pattern patt, String loreString) {
		String value = "";

        if (BLA.contains(loreString, patt)) {
			BLA.matcher = patt.matcher(EText.e(loreString).toLowerCase());
			if (BLA.matcher.find()) value = BLA.matcher.group(1);
        }

		return value;
	}

	public static String getLore(String key, String value) {
		return EText.c(key + ": " + value);
	}

	public static double getDoubleValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, ValidateStatus.HAS_LORE)) return 0.0D;
		return BLA.getDoubleValue(patt, stack.getItemMeta().getLore());
	}

	public static double getDoubleValue(Pattern patt, List<String> lore) {
		String value = "0.0D";

		if (BLA.contains(lore, patt)) {
			BLA.matcher = patt.matcher(EText.e(lore.toString()).toLowerCase());
			if (BLA.matcher.find()) value = BLA.matcher.group(1);
		}

		return ValueUtil.parseDouble(value.replaceAll("%", ""));
	}


	public static boolean contains(String loreString, Pattern patt) { return patt.matcher(EText.e(loreString).toLowerCase()).find(); }
	public static boolean contains(List<String> lore, Pattern patt) { return patt.matcher(EText.e(lore.toString()).toLowerCase()).find(); }
	public static boolean contains(ItemStack item, Pattern patt) {
		if (!ItemStackUtil.validate(item, ValidateStatus.HAS_LORE)) return false;
		return BLA.contains(item.getItemMeta().getLore(), patt);
	}

	public static int getIndexOf(List<String> lore, Pattern pattern) {
		int j = lore.size();

		if (BLA.contains(lore, pattern))
			for (int s = 0; s < j; s++) {
				Matcher matcher = pattern.matcher(lore.get(s).toLowerCase());
				if (matcher.find()) return s;
			}
		return -1;
	}

	public static boolean isPercent(List<String> lore, Pattern patt) {
		BLA.matcher = patt.matcher(lore.toString().toLowerCase());
		if (BLA.matcher.find())
			if (BLA.matcher.group(1) != null)
				return BLA.matcher.group(1).contains("%");
		return false;
	}

}
