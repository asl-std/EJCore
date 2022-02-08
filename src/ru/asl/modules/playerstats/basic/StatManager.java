package ru.asl.modules.playerstats.basic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.asl.core.Core;
import ru.asl.core.configs.EConfig;
import ru.asl.modules.playerstats.Absorption;
import ru.asl.modules.playerstats.Blocking;
import ru.asl.modules.playerstats.CriticalChance;
import ru.asl.modules.playerstats.Dodge;
import ru.asl.modules.playerstats.ExperienceBonus;
import ru.asl.modules.playerstats.Knockback;
import ru.asl.modules.playerstats.Lifesteal;
import ru.asl.modules.playerstats.MaxHealth;
import ru.asl.modules.playerstats.PhysicalDamage;
import ru.asl.modules.playerstats.RangedDamage;
import ru.asl.modules.playerstats.Reflect;

public class StatManager {
	public final BasicStat
	MAX_HEALTH 			= new MaxHealth			("MAX_HEALTH", 			"player.health"				 , 20.0, 0.0),
	HEALTH_REGEN 		= new BasicStat			("HEALTH_REGEN", 		"player.health-regen"		 , 1.0, 0.0)
	{
		@Override
		public void initCustomSettings() {
			final EConfig cfg = Core.getCfg();

			if (cfg.contains("health-regen")) {
				double base = 20.0;
				if (cfg.contains("health-regen.base")) {
					base = cfg.getDouble("health-regen.base");
					cfg.set("health-regen.base", null);
				}
				double cost = 0.0;
				if (cfg.contains("health-regen.cost-value")) {
					cost = cfg.getDouble("health-regen.cost-value");
					cfg.set("health-regen.cost-value", null);
				}
				double perLevel = 0.0;
				if (cfg.contains("health-regen.per-level")) {
					perLevel = cfg.getDouble("health-regen.per-level");
					cfg.set("health-regen.per-level", null);
				}
				String visualName = getVisualName();
				if (cfg.contains("health-regen.visual-name")) {
					visualName = cfg.getString("health-regen.visual-name");
					cfg.set("health-regen.visual-name", null);
				}
				boolean isEnabled = true;
				if (cfg.contains("health-regen.is-enabled")) {
					isEnabled = cfg.getBoolean("health-regen.is-enabled");
					cfg.set("health-regen.is-enabled", null);
				}

				cfg.set("health-regen", null);

				statCfg.set("health-regen.is-enabled", isEnabled);
				statCfg.set("health-regen.base", base);
				statCfg.set("health-regen.per-level", perLevel);
				statCfg.set("health-regen.visual-name", visualName);
				statCfg.set("health-regen.cost-value", cost);
			}

		}
	},
	MAX_HUNGER			= new BasicStat			("MAX_HUNGER", 			"player.hunger-max"			 , 20.0, 0.0)
	{
		@Override
		public void initCustomSettings() {
			final EConfig cfg = Core.getCfg();

			if (cfg.contains("max-hunger")) {
				double base = 20.0;
				if (cfg.contains("max-hunger.base")) {
					base = cfg.getDouble("max-hunger.base");
					cfg.set("max-hunger.base", null);
				}
				double cost = 0.0;
				if (cfg.contains("max-hunger.cost-value")) {
					cost = cfg.getDouble("max-hunger.cost-value");
					cfg.set("max-hunger.cost-value", null);
				}
				double perLevel = 0.0;
				if (cfg.contains("max-hunger.per-level")) {
					perLevel = cfg.getDouble("max-hunger.per-level");
					cfg.set("max-hunger.per-level", null);
				}
				String visualName = getVisualName();
				if (cfg.contains("max-hunger.visual-name")) {
					visualName = cfg.getString("max-hunger.visual-name");
					cfg.set("max-hunger.visual-name", null);
				}
				boolean isEnabled = true;
				if (cfg.contains("max-hunger.is-enabled")) {
					isEnabled = cfg.getBoolean("max-hunger.is-enabled");
					cfg.set("max-hunger.is-enabled", null);
				}

				cfg.set("max-hunger", null);

				statCfg.set("max-hunger.is-enabled", isEnabled);
				statCfg.set("max-hunger.base", base);
				statCfg.set("max-hunger.per-level", perLevel);
				statCfg.set("max-hunger.visual-name", visualName);
				statCfg.set("max-hunger.cost-value", cost);
			}
		}
	},

	MANA 				= new BasicStat			("MANA", 				"player.mana"				 , 0.0, 0.0),
	MANA_REGEN			= new BasicStat			("MANA_REGEN", 			"player.mana-regen"			 , 0.0, 0.0),
	FIST_DAMAGE			= new BasicStat			("FIST_DAMAGE",			"player.damage.fist"		 , 1.0, 1.0) {
		@Override
		public void initCustomSettings() {
			type = StatType.RANGE;
		}
	},
	EXP_BONUS 			= new ExperienceBonus	("EXP_BONUS", 			"player.exp-bonus"			 , 1.0, 0.0),
	SPEED 				= new BasicStat			("SPEED", 				"player.speed"				 , 100.0, 0.0),

	PVP_DAMAGE_MODIFIER = new BasicStat			("PVP_DAMAGE_MODIFIER", "player.modifier.pvp.damage" , 100.0, 0.0),
	PVE_DAMAGE_MODIFIER = new BasicStat			("PVE_DAMAGE_MODIFIER", "player.modifier.pve.damage" , 100.0, 0.0),

	PVP_DEFENCE_MODIFIER= new BasicStat			("PVP_DEFENCE_MODIFIER","player.modifier.pvp.defence", 100.0, 0.0),
	PVE_DEFENCE_MODIFIER= new BasicStat			("PVE_DEFENCE_MODIFIER","player.modifier.pve.defence", 100.0, 0.0),

	PHYSICAL_DAMAGE 	= new PhysicalDamage	("PHYSICAL_DAMAGE", 	"player.damage.physical"	 , 0.0, 0.0),
	RANGED_DAMAGE		= new RangedDamage		("RANGED_DAMAGE", 		"player.damage.ranged"		 , 0.0, 0.0),
	//MAGICAL_DAMAGE 	= new MagicalDamage		("MAGICAL_DAMAGE", 		"player.damage.magical"		 , 0.0, 0.0),
	//SPELL_POWER 		= new BasicStat			("SPELL_POWER", 		"player.damage.spell"		 , 1.0, 0.0),

	PHYSICAL_DEFENCE 	= new BasicStat			("PHYSICAL_DEFENCE", 	"player.defence.physical"	 , 0.0, 0.0),
	PROJECTILE_DEFENCE	= new BasicStat			("PROJECTILE_DEFENCE",	"player.defence.projectile"	 , 0.0, 0.0),
	//MAGICAL_DEFENCE 	= new BasicStat			("MAGICAL_DEFENCE", 	"player.defence.magical"	 , 0.0, 0.0),
	//SPELL_RESIST		= new BasicStat			("SPELL_RESIST",		"player.defence.spell"		 , 1.0, 0.0),

	CRITICAL_CHANCE 	= new CriticalChance	("CRITICAL_CHANCE", 	"player.rpg.critical-chance" , 0.0, 0.0),
	CRITICAL_DAMAGE 	= new BasicStat			("CRITICAL_DAMAGE", 	"player.rpg.critical-damage" , 130.0, 0.0),

	ABSORPTION 			= new Absorption		("ABSORB", 				"player.rpg.absoprtion"		 , 0.0, 0.0),
	BLOCKING 			= new Blocking			("BLOCKING", 			"player.rpg.blocking"		 , 0.0, 0.0),
	DODGE 				= new Dodge				("DODGE", 				"player.rpg.dodge"			 , 0.0, 0.0),
	KNOCKBACK 			= new Knockback			("KNOCKBACK", 			"player.rpg.knockback"		 , 0.0, 0.0),
	REFLECT 			= new Reflect			("REFLECT", 			"player.rpg.reflect"		 , 0.0, 0.0),

	LIFESTEAL 			= new Lifesteal			("LIFESTEAL", 			"player.effect.lifesteal"	 , 0.0, 0.0);
	//STUN 				= new Stun				("STUN", 				"player.effect.stun"		 , 0.0, 0.0),
	//ROOT 				= new Root				("ROOT", 				"player.effect.root"		 , 0.0, 0.0);

	private final Map<String, BasicStat> stats = new ConcurrentHashMap<>();

	public  final Collection<BasicStat> getRegistered() { return stats.values(); }

	private List<BasicStat> sortedList = new ArrayList<>();

	private int size = -1;

	public final  List<BasicStat> getSortedList() {
		if (size == stats.size()) { return sortedList; }

		final ArrayList<BasicStat> list = new ArrayList<>(stats.values());

		list.sort(Comparator.comparingInt(BasicStat::getPriority));

		sortedList = list;
		return sortedList;
	}

	private final void register(BasicStat stat) {
		if (stat != null && !stats.containsKey(stat.getKey())) {
			stat.setUniquePosition(stats.size());
			stats.put(stat.getKey().toUpperCase(), stat);
		}
	}

	public final void register() {
		if (!stats.isEmpty()) { return; }
		int pos = 0;
		for (final Field f : StatManager.class.getFields()) {
			if (!f.isAccessible())
				f.setAccessible(true);
			if (BasicStat.class.isAssignableFrom(f.getType())) {
				try {
					register((BasicStat) f.get(this));
					((BasicStat) f.get(this)).setPriority(pos);
					pos++;
				} catch (final Exception e) { e.printStackTrace(); }
			}
		}
	}

	public final BasicStat getByKey(String key) {
		if (stats.containsKey(key.toUpperCase()))
			return stats.get(key.toUpperCase());

		return null;
	}

	public final void reloadStats() {
		for (final BasicStat stat : getRegistered()) {
			stat.initCustomSettings();

			stat.getVisualName();
			stat.getCostValue();
			stat.initializeBasicValues(stat.defBase, stat.defPerLevel);
		}
	}

}
