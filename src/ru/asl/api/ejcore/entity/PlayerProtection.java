package ru.asl.api.ejcore.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

public class PlayerProtection {

	public static boolean canAttack(EPlayer attacker, LivingEntity target) {
		if (attacker == target) return false;

		if (target instanceof Tameable) {
			Tameable tame = (Tameable) target;
			if (tame.isTamed() && tame.getOwner() instanceof OfflinePlayer) {
				OfflinePlayer ofp = (OfflinePlayer) tame.getOwner();
				if (ofp.isOnline())
					return canAttack(attacker, ofp.getPlayer());
				else
					return false;
			}
		}

		return true;
	}


	public static boolean isAlly(EPlayer attacker, LivingEntity target) { return !canAttack(attacker,target); }

	public static List<LivingEntity> canAttack(EPlayer attacker, List<LivingEntity> targets) {
		List<LivingEntity> list = new ArrayList<>();
		for (LivingEntity ent : targets)
			if (canAttack(attacker,ent))
				list.add(ent);

		return list;
	}

	public static List<LivingEntity> cannotAttack(EPlayer attacker, List<LivingEntity> targets) {
		List<LivingEntity> list = new ArrayList<>();
		for (LivingEntity ent : targets)
			if (!canAttack(attacker,ent))
				list.add(ent);

		return list;
	}

}
