package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.api.bukkit.value.util.ValueUtil;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;
import org.aslstd.modules.player.PlayerUtils;
import org.bukkit.entity.Player;

/**
 * <p>Blocking class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Blocking extends BasicAttr implements ListeningCombat {
	/**
	 * <p>Constructor for Blocking.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param base a double
	 * @param perLevel a double
	 */
	public Blocking(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel);
	}

	private final String T1 = "tier1",
			T2 = "tier2",
			T3 = "tier3",
			MC = "max-chance",
			V = "block-percent",
			D = "divider";

	private double getMC(String tier) { return settings.get(tier+"."+MC); }
	private long   getV (String tier) { return (long) settings.get(tier+"."+V);  }
	private double getD (String tier) { return settings.get(tier+"."+D);  }

	/** {@inheritDoc} */
	@Override
	public void initCustomSettings() {

		settings.setCustom("tier1-max-chance",statCfg.getDouble(toString() + ".settings.tier1.max-chance", 14.0D, true));
		settings.setCustom("tier1-block-percent", statCfg.getLong(toString() + ".settings.tier1.value", 15, true));
		settings.setCustom("tier1-increase-divider", statCfg.getDouble(toString() + ".settings.tier1.increase-divider", 1.0D, true));

		settings.setCustom("tier2-max-chance", statCfg.getDouble(toString() + ".settings.tier2.max-chance", 20.0D, true));
		settings.setCustom("tier2-block-percent", statCfg.getLong(toString() + ".settings.tier2.value", 30, true));
		settings.setCustom("tier2-increase-divider", statCfg.getDouble(toString() + ".settings.tier2.increase-divider", 2.1D, true));

		settings.setCustom("tier3-max-chance", statCfg.getDouble(toString() + ".settings.tier3.max-chance", 33.0D, true));
		settings.setCustom("tier3-block-percent", statCfg.getLong(toString() + ".settings.tier3.value", 45, true));
		settings.setCustom("tier3-increase-divider", statCfg.getDouble(toString() + ".settings.tier3.increase-divider", 3.3D, true));

	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.ENTITY_TO_PLAYER && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getReceiver());

		final double value = PlayerUtils.getStatValue(rpg, this)[0];
		double damage = e.getDamage();
		boolean changed = false;

		if (value <= getMC(T1))
			if (ValueUtil.isTrue(value, 100)) {
				damage = damage * (1.0-getV(T1)/100);
				changed = true;
			}

		final double tier2val = (value-getMC(T1))/getD(T2);

		if (tier2val <= getMC(T2)-getMC(T1))
			if (ValueUtil.isTrue(getMC(T1)+tier2val, 100)) {
				damage = damage * (1.0-getV(T2)/100);
				changed = true;
			}

		final double tier3val = (tier2val-getMC(T2))/getD(T3);

		if (tier3val <= getMC(T3)-getMC(T2))
			if (ValueUtil.isTrue(getMC(T2)+tier3val, 100)) {
				damage = damage * (1.0-getV(T3)/100);
				changed = true;
			}

		if (changed)
			if (damage <= 0)
				e.setCancelled(true);
	}

}
