package org.aslstd.modules.player;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.value.util.MathUtil;
import org.aslstd.modules.MAttributes;
import org.aslstd.modules.attribute.BasicAttr;

public class PlayerUtils {

	/**
	 * <p>getBaseStatValue.</p>
	 *
	 * @param attr a {@link org.aslstd.modules.attribute.BasicAttr} object
	 * @return an array of {@link double} objects
	 */
	public static double[] getBaseStatValue(EPlayer player, BasicAttr attr) {
		double[] values = { 0.0d, 0.0d };
		switch(attr.getType()) {
		case PER_LEVEL:
			values[0] = player.getTempSettings().getAndScale(attr.getPath(), player.getLevel());
			break;
		case RANGE:
			values = player.getTempSettings().getRange(attr.getPath());
			break;
		case SINGLE:
			values[0] = player.getTempSettings().getValue(attr.getPath(), attr.getFirstValue());
			break;
		}
		return values;
	}

	/**
	 * <p>getDamage.</p>
	 *
	 * @param stat a {@link org.aslstd.modules.attribute.BasicAttr} object
	 * @return a double
	 */
	public static double getDamage(EPlayer player, BasicAttr stat) { // TODO
		if (!stat.getKey().contains("DAMAGE")) return 0d;

		final double[] damage = getStatValue(player, stat);
		return MathUtil.getRandomRange(damage[0], damage[1]);
	}

	/**
	 * <p>getStatValue.</p>
	 *
	 * @param stat a {@link org.aslstd.modules.attribute.BasicAttr} object
	 * @return an array of {@link double} objects
	 */
	public static double[] getStatValue(EPlayer player, BasicAttr stat) {
		final double[] values = getBaseStatValue(player, stat);
		final double[] multiplier = new double[] { 0D, 0D };

		for (final EquipSlot slot : EquipSlot.values()) {
			if (player.getTempSettings().hasRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey())) {
				final double[] val = player.getTempSettings().getRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey());
				values[0] = values[0] + val[0];
				values[1] = values[1] + val[1];
			}

			if (player.getTempSettings().hasRange("player.equip." + slot.name().toLowerCase() + "." +  stat.getKey() + "-multiplier")) {
				final double[] val = player.getTempSettings().getRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey() + "-multiplier");
				multiplier[0] = multiplier[0] + val[0];
				multiplier[1] = multiplier[1] + val[1];
			}
		}

		if (multiplier[1] < multiplier[0]) multiplier[1] = multiplier[0];

		return new double[] { values[0] + (values[0] * (multiplier[0] / 100)), values[1] + (values[1] * (multiplier[1] / 100)) };
	}

	public static void updateStats(EPlayer player) {
		final double defHealth = getStatValue(player, MAttributes.getWeaponAttributes().getByKey("MAX_HEALTH"))[0];
		final double classHealth = player.getTempSettings().getValue(EPlayer.CLASS_HEALTH, player.getLevel());

		final double maxHealth = defHealth + classHealth;
		player.changeMaxHealth(maxHealth >= 0 ? maxHealth : 1);

		final double speed = getStatValue(player, MAttributes.getWeaponAttributes().getByKey("SPEED"))[0];

		if ((speed >= 0)) player.getPlayer().setWalkSpeed((float) ((MathUtil.getPercentsOfValue(20, speed) / 100) >= 1.0f ? 1.0f : MathUtil.getPercentsOfValue(20, speed) / 100));
	}

}
