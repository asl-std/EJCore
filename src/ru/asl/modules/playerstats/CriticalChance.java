package ru.asl.modules.playerstats;

import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.api.bukkit.events.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.value.util.ValueUtil;
import ru.asl.core.Core;
import ru.asl.modules.playerstats.basic.BasicAttr;
import ru.asl.modules.playerstats.basic.interfaze.ListeningCombat;

@SuppressWarnings("unused")
public final class CriticalChance extends BasicAttr implements ListeningCombat {
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

	@Override
	public void initCustomSettings() {
		settings.setCustom("tier1-max-chance",statCfg.getDouble("stats-name." + toString() +".unique.tier1.max-chance", 10.0D, true));
		settings.setCustom("tier1-cooldown", statCfg.getLong("stats-name." + toString() +".unique.tier1.value", 0, true));
		settings.setCustom("tier1-increase-divider", statCfg.getDouble("stats-name." + toString() +".unique.tier1.increase-divider", 1.0D, true));

		settings.setCustom("tier2-max-chance", statCfg.getDouble("stats-name." + toString() +".unique.tier2.max-chance", 20.0D, true));
		settings.setCustom("tier2-cooldown", statCfg.getLong("stats-name." + toString() +".unique.tier2.value", 0, true));
		settings.setCustom("tier2-increase-divider", statCfg.getDouble("stats-name." + toString() +".unique.tier2.increase-divider", 1.0D, true));

		settings.setCustom("tier3-max-chance", statCfg.getDouble("stats-name." + toString() +".unique.tier3.max-chance", 100.0D, true));
		settings.setCustom("tier3-cooldown", statCfg.getLong("stats-name." + toString() +".unique.tier3.value", 0, true));
		settings.setCustom("tier3-increase-divider", statCfg.getDouble("stats-name." + toString() +".unique.tier3.increase-divider", 1.0D, true));
	}

	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY || e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final double value = rpg.getStatValue(this)[0];
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

		if (ValueUtil.isTrue(value, 100)) {
			damage = damage * (rpg.getStatValue(Core.getStats().CRITICAL_DAMAGE)[0]/100);
			crit = true;
		}

		if (crit) e.setDamage(damage);
	}

}
