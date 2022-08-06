package ru.aslcraft.api.ejcore.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

/**
 * <p>PlayerProtection class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class PlayerProtection {

	/**
	 * <p>canAttack.</p>
	 *
	 * @param attacker a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 * @param target a {@link org.bukkit.entity.LivingEntity} object
	 * @return a boolean
	 */
	public static boolean canAttack(EPlayer attacker, LivingEntity target) {
		if (attacker.getPlayer() == target) return false;

		if (target instanceof Tameable) {
			final Tameable tame = (Tameable) target;
			if (tame.isTamed() && tame.getOwner() instanceof OfflinePlayer) {
				final OfflinePlayer ofp = (OfflinePlayer) tame.getOwner();
				if (ofp.isOnline())
					return canAttack(attacker, ofp.getPlayer());
				else
					return false;
			}
		}

		return true;
	}


	/**
	 * <p>isAlly.</p>
	 *
	 * @param attacker a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 * @param target a {@link org.bukkit.entity.LivingEntity} object
	 * @return a boolean
	 */
	public static boolean isAlly(EPlayer attacker, LivingEntity target) { return !canAttack(attacker,target); }

	/**
	 * <p>canAttack.</p>
	 *
	 * @param attacker a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 * @param targets a {@link java.util.List} object
	 * @return a {@link java.util.List} object
	 */
	public static List<LivingEntity> canAttack(EPlayer attacker, List<LivingEntity> targets) {
		final List<LivingEntity> list = new ArrayList<>();
		for (final LivingEntity ent : targets)
			if (canAttack(attacker,ent))
				list.add(ent);

		return list;
	}

	/**
	 * <p>cannotAttack.</p>
	 *
	 * @param attacker a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 * @param targets a {@link java.util.List} object
	 * @return a {@link java.util.List} object
	 */
	public static List<LivingEntity> cannotAttack(EPlayer attacker, List<LivingEntity> targets) {
		final List<LivingEntity> list = new ArrayList<>();
		for (final LivingEntity ent : targets)
			if (!canAttack(attacker,ent))
				list.add(ent);

		return list;
	}

}
