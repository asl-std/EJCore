package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.aslstd.modules.MAttributes;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;
import org.aslstd.modules.player.PlayerUtils;
import org.bukkit.entity.Player;

/**
 * <p>CriticalChance class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("unused")
public final class CriticalChance extends BasicAttr implements ListeningCombat {
	/**
	 * <p>Constructor for CriticalChance.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param base a double
	 * @param perLevel a double
	 */
	public CriticalChance(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel);
	}

	private final String T1 = "tier1",
			T2 = "tier2",
			T3 = "tier3",
			MC = "max-chance",
			C = "value",
			D = "divider";

	private double getMC(String tier) { return settings.get(tier+"."+MC); }
	private long   getC (String tier) { return (long) settings.get(tier+"."+C);  }
	private double getD (String tier) { return settings.get(tier+"."+D);  }

	/** {@inheritDoc} */
	@Override
	public void initCustomSettings() {
		settings.setCustom("tier1-max-chance", statCfg.getDouble(toString() +".settings.tier1.max-chance", 10.0D, true));
		settings.setCustom("tier1-cooldown", statCfg.getLong(toString() +".settings.tier1.value", 0, true));
		settings.setCustom("tier1-increase-divider", statCfg.getDouble(toString() + ".settings.tier1.increase-divider", 1.0D, true));

		settings.setCustom("tier2-max-chance", statCfg.getDouble(toString() + ".settings.tier2.max-chance", 20.0D, true));
		settings.setCustom("tier2-cooldown", statCfg.getLong(toString() + ".settings.tier2.value", 0, true));
		settings.setCustom("tier2-increase-divider", statCfg.getDouble(toString() + ".settings.tier2.increase-divider", 1.0D, true));

		settings.setCustom("tier3-max-chance", statCfg.getDouble(toString() + ".settings.tier3.max-chance", 100.0D, true));
		settings.setCustom("tier3-cooldown", statCfg.getLong(toString() + ".settings.tier3.value", 0, true));
		settings.setCustom("tier3-increase-divider", statCfg.getDouble(toString() + ".settings.tier3.increase-divider", 1.0D, true));
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY || e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final double value = PlayerUtils.getStatValue(rpg, this)[0];
		double damage = e.getDamage();
		boolean crit = false;

		/*if (value <= getMC(T1))
			if (BasicStat.isTrue(value, 100)) {
				rpg.addCooldown(C, getC(T1)*1000);
				crit = true;
			}

		double tier2val = (value-(getMC(T1)))/getD(T2);

		if (tier2val <= getMC(T2))
			if (BasicStat.isTrue(getMC(T1)+tier2val, 100)) {
				damage = damage * (1+(BasicStat.CRITICALDAMAGE.getCurrent((LivingEntity) e.getAttacker())/100));
				rpg.addCooldown(C, getC(T2)*1000);
				crit = true;
			}

		double tier3val = (tier2val-getMC(T2))/getD(T3);

		if (tier3val <= getMC(T3))
			if (BasicStat.isTrue(getMC(T2)+tier3val, 100)) {
				damage = damage * (1+(BasicStat.CRITICALDAMAGE.getCurrent((LivingEntity) e.getAttacker())/100));
				rpg.addCooldown(C, getC(T3)*1000);
				crit = true;
			}*/

		if (NumUtil.isTrue(value, 100)) {
			damage = damage * (PlayerUtils.getStatValue(rpg, MAttributes.getWeaponAttributes().getByKey("CRITICAL_DAMAGE"))[0]/100);
			crit = true;
		}

		if (crit) e.setDamage(damage);
	}

}
