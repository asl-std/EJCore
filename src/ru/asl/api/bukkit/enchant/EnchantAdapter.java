package ru.asl.api.bukkit.enchant;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import ru.asl.api.ejcore.resolver.ServerVersion;

public class EnchantAdapter {

	static { 	byName = new HashMap<>(); byKey = new HashMap<>(); }

	public static final EnchantAdapter PROTECTION_ENVIRONMENTAL 	= new EnchantAdapter("PROTECTION_ENVIRONMENTAL","protection");

	public static final EnchantAdapter PROTECTION_FIRE 				= new EnchantAdapter("PROTECTION_FIRE","fire_protection");

	public static final EnchantAdapter PROTECTION_FALL 				= new EnchantAdapter("PROTECTION_FALL","feather_falling");

	public static final EnchantAdapter PROTECTION_EXPLOSIONS 		= new EnchantAdapter("PROTECTION_EXPLOSIONS","blast_protection");

	public static final EnchantAdapter PROTECTION_PROJECTILE 		= new EnchantAdapter("PROTECTION_PROJECTILE","projectile_protection");

	public static final EnchantAdapter OXYGEN 						= new EnchantAdapter("OXYGEN","respiration");

	public static final EnchantAdapter WATER_WORKER 				= new EnchantAdapter("WATER_WORKER","aqua_affinity");

	public static final EnchantAdapter THORNS 						= new EnchantAdapter("THORNS","thorns");

	public static final EnchantAdapter DEPTH_STRIDER 				= new EnchantAdapter("DEPTH_STRIDER","depth_strider", ServerVersion.VER_1_7_9);

	public static final EnchantAdapter FROST_WALKER 				= new EnchantAdapter("FROST_WALKER","frost_walker", ServerVersion.VER_1_7_9);

	public static final EnchantAdapter BINDING_CURSE 				= new EnchantAdapter("BINDING_CURSE","binding_curse", ServerVersion.VER_1_10_2);

	public static final EnchantAdapter DAMAGE_ALL 					= new EnchantAdapter("DAMAGE_ALL","sharpness");

	public static final EnchantAdapter DAMAGE_UNDEAD 				= new EnchantAdapter("DAMAGE_UNDEAD","smite");

	public static final EnchantAdapter DAMAGE_ARTHROPODS 			= new EnchantAdapter("DAMAGE_ARTHROPODS","bane_of_arthropods");

	public static final EnchantAdapter KNOCKBACK 					= new EnchantAdapter("KNOCKBACK","knockback");

	public static final EnchantAdapter FIRE_ASPECT 					= new EnchantAdapter("FIRE_ASPECT","fire_aspect");

	public static final EnchantAdapter LOOT_BONUS_MOBS 				= new EnchantAdapter("LOOT_BONUS_MOBS","looting");

	public static final EnchantAdapter SWEEPING_EDGE 				= new EnchantAdapter("SWEEPING_EDGE","sweeping", ServerVersion.VER_1_10_2);

	public static final EnchantAdapter DIG_SPEED 					= new EnchantAdapter("DIG_SPEED","efficiency");

	public static final EnchantAdapter SILK_TOUCH 					= new EnchantAdapter("SILK_TOUCH","silk_touch");

	public static final EnchantAdapter DURABILITY 					= new EnchantAdapter("DURABILITY","unbreaking");

	public static final EnchantAdapter LOOT_BONUS_BLOCKS 			= new EnchantAdapter("LOOT_BONUS_BLOCKS","fortune");

	public static final EnchantAdapter ARROW_DAMAGE 				= new EnchantAdapter("ARROW_DAMAGE","power");

	public static final EnchantAdapter ARROW_KNOCKBACK 				= new EnchantAdapter("ARROW_KNOCKBACK","punch");

	public static final EnchantAdapter ARROW_FIRE 					= new EnchantAdapter("ARROW_FIRE","flame");

	public static final EnchantAdapter ARROW_INFINITE 				= new EnchantAdapter("ARROW_INFINITE","infinity");

	public static final EnchantAdapter LUCK 						= new EnchantAdapter("LUCK","luck_of_the_sea", ServerVersion.VER_1_6_4);

	public static final EnchantAdapter LURE 						= new EnchantAdapter("LURE","lure", ServerVersion.VER_1_6_4);

	public static final EnchantAdapter LOYALTY 						= new EnchantAdapter("LOYALTY","loyalty", ServerVersion.VER_1_12_2);

	public static final EnchantAdapter IMPALING 					= new EnchantAdapter("IMPALING","impaling", ServerVersion.VER_1_12_2);

	public static final EnchantAdapter RIPTIDE 						= new EnchantAdapter("RIPTIDE","riptide", ServerVersion.VER_1_12_2);

	public static final EnchantAdapter CHANNELING 					= new EnchantAdapter("CHANNELING","chanelling", ServerVersion.VER_1_12_2);

	public static final EnchantAdapter MENDING 						= new EnchantAdapter("MENDING","mending", ServerVersion.VER_1_8_8);

	public static final EnchantAdapter VANISHING_CURSE 				= new EnchantAdapter("VANISHING_CURSE", "vanishing_curse", ServerVersion.VER_1_10_2);

	private static final Map<String, EnchantAdapter> byName;
	private static final Map<String, EnchantAdapter> byKey;

	private String name;
	public	String getName() { return name; }
	private String toResolve;

	public EnchantAdapter(String name, String toResolve) {
		this.name = name;
		this.toResolve = toResolve;
		EnchantAdapter.byName.put(name, this);
		EnchantAdapter.byKey.put(toResolve, this);
	}

	public EnchantAdapter(String name, String toResolve, int version) {
		this.name = name;
		this.toResolve = toResolve;
		if (ServerVersion.isVersionAtMost(version)) {
			EnchantAdapter.byName.put(name, this);
			EnchantAdapter.byKey.put(toResolve, this);
		}
	}

	public Enchantment toEnchant() {
		return Enchantment.getByKey(NamespacedKey.minecraft(toResolve));
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
		return EnchantAdapter.getByKey(ench.getKey().getKey());
	}

}
