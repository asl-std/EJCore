package ru.asl.api.bukkit.enchant;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import lombok.Getter;
import ru.asl.api.ejcore.utils.ServerVersion;

public final class EnchantAdapter {

	static { byName = new HashMap<>(); byKey = new HashMap<>(); }

	public static final EnchantAdapter ARROW_DAMAGE 				= new EnchantAdapter("ARROW_DAMAGE","power");

	public static final EnchantAdapter ARROW_FIRE 					= new EnchantAdapter("ARROW_FIRE","flame");

	public static final EnchantAdapter ARROW_INFINITE 				= new EnchantAdapter("ARROW_INFINITE","infinity");

	public static final EnchantAdapter ARROW_KNOCKBACK 				= new EnchantAdapter("ARROW_KNOCKBACK","punch");

	public static final EnchantAdapter BINDING_CURSE 				= new EnchantAdapter("BINDING_CURSE","binding_curse", ServerVersion.VER_1_11_0);

	public static final EnchantAdapter CHANNELING 					= new EnchantAdapter("CHANNELING","chanelling", ServerVersion.VER_1_13);

	public static final EnchantAdapter DAMAGE_ALL 					= new EnchantAdapter("DAMAGE_ALL","sharpness");

	public static final EnchantAdapter DAMAGE_ARTHROPODS 			= new EnchantAdapter("DAMAGE_ARTHROPODS","bane_of_arthropods");

	public static final EnchantAdapter DAMAGE_UNDEAD 				= new EnchantAdapter("DAMAGE_UNDEAD","smite");

	public static final EnchantAdapter DEPTH_STRIDER 				= new EnchantAdapter("DEPTH_STRIDER","depth_strider", ServerVersion.VER_1_8_0);

	public static final EnchantAdapter DIG_SPEED 					= new EnchantAdapter("DIG_SPEED","efficiency");

	public static final EnchantAdapter DURABILITY 					= new EnchantAdapter("DURABILITY","unbreaking");

	public static final EnchantAdapter FIRE_ASPECT 					= new EnchantAdapter("FIRE_ASPECT","fire_aspect");

	public static final EnchantAdapter FROST_WALKER 				= new EnchantAdapter("FROST_WALKER","frost_walker", ServerVersion.VER_1_9_0);

	public static final EnchantAdapter IMPALING 					= new EnchantAdapter("IMPALING","impaling", ServerVersion.VER_1_13);

	public static final EnchantAdapter KNOCKBACK 					= new EnchantAdapter("KNOCKBACK","knockback");

	public static final EnchantAdapter LOOT_BONUS_BLOCKS 			= new EnchantAdapter("LOOT_BONUS_BLOCKS","fortune");

	public static final EnchantAdapter LOOT_BONUS_MOBS 				= new EnchantAdapter("LOOT_BONUS_MOBS","looting");

	public static final EnchantAdapter LOYALTY 						= new EnchantAdapter("LOYALTY","loyalty", ServerVersion.VER_1_13);

	public static final EnchantAdapter LUCK 						= new EnchantAdapter("LUCK","luck_of_the_sea", ServerVersion.VER_1_7_2);

	public static final EnchantAdapter LURE 						= new EnchantAdapter("LURE","lure", ServerVersion.VER_1_7_2);

	public static final EnchantAdapter MENDING 						= new EnchantAdapter("MENDING","mending", ServerVersion.VER_1_9_0);

	public static final EnchantAdapter MULTISHOT 					= new EnchantAdapter("MULTISHOT","multishot", ServerVersion.VER_1_14);

	public static final EnchantAdapter OXYGEN 						= new EnchantAdapter("OXYGEN","respiration");

	public static final EnchantAdapter PIERCING 					= new EnchantAdapter("PIERCING","piercing", ServerVersion.VER_1_14);

	public static final EnchantAdapter PROTECTION_ENVIRONMENTAL 	= new EnchantAdapter("PROTECTION_ENVIRONMENTAL","protection");

	public static final EnchantAdapter PROTECTION_EXPLOSIONS 		= new EnchantAdapter("PROTECTION_EXPLOSIONS","blast_protection");

	public static final EnchantAdapter PROTECTION_FALL 				= new EnchantAdapter("PROTECTION_FALL","feather_falling");

	public static final EnchantAdapter PROTECTION_FIRE 				= new EnchantAdapter("PROTECTION_FIRE","fire_protection");

	public static final EnchantAdapter PROTECTION_PROJECTILE 		= new EnchantAdapter("PROTECTION_PROJECTILE","projectile_protection");

	public static final EnchantAdapter QUICK_CHARGE					= new EnchantAdapter("QUICK_CHARGE", "quick_charge", ServerVersion.VER_1_14);

	public static final EnchantAdapter RIPTIDE 						= new EnchantAdapter("RIPTIDE","riptide", ServerVersion.VER_1_13);

	public static final EnchantAdapter SILK_TOUCH 					= new EnchantAdapter("SILK_TOUCH","silk_touch");

	public static final EnchantAdapter SOUL_SPEED					= new EnchantAdapter("SOUL_SPEED", "soul_speed", ServerVersion.VER_1_16);

	public static final EnchantAdapter SWEEPING_EDGE 				= new EnchantAdapter("SWEEPING_EDGE","sweeping", ServerVersion.VER_1_11_1);

	public static final EnchantAdapter THORNS 						= new EnchantAdapter("THORNS","thorns");

	public static final EnchantAdapter VANISHING_CURSE 				= new EnchantAdapter("VANISHING_CURSE", "vanishing_curse", ServerVersion.VER_1_11_0);

	public static final EnchantAdapter WATER_WORKER 				= new EnchantAdapter("WATER_WORKER","aqua_affinity");

	private static final Map<String, EnchantAdapter> byName;
	private static final Map<String, EnchantAdapter> byKey;

	@Getter private String name;
	@Getter private String keyName;

	private EnchantAdapter(String name, String keyName) {
		this.name = name;
		this.keyName = keyName;
		register(name, keyName);
	}

	private EnchantAdapter(String name, String keyName, int version) {
		this.name = name;
		this.keyName = keyName;
		if (!ServerVersion.isVersionAtLeast(version))
			register(name, keyName);
	}

	private void register(String name, String keyName) {
		EnchantAdapter.byName.put(name, this);
		EnchantAdapter.byKey.put(keyName, this);
	}

	public Enchantment toEnchant() {
		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			return Enchantment.getByKey(NamespacedKey.minecraft(keyName));
		else
			return Enchantment.getByName(name);
	}

	public static EnchantAdapter getByKey(String key) {
		EnchantAdapter enchant = null;

		if (EnchantAdapter.byName.containsKey(key.toUpperCase()))
			enchant = EnchantAdapter.byName.get(key.toUpperCase());
		if (EnchantAdapter.byKey.containsKey(key.toLowerCase()))
			enchant = EnchantAdapter.byKey.get(key.toLowerCase());

		return enchant;
	}

	public static EnchantAdapter getByKey(Enchantment ench) {
		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			return EnchantAdapter.getByKey(ench.getKey().getKey());
		else
			return EnchantAdapter.byName.get(ench.getName());
	}

}
