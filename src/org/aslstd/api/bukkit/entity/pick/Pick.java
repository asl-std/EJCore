package org.aslstd.api.bukkit.entity.pick;

import org.aslstd.api.ejcore.player.EPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Pick {

	public static EPlayer of(Player p) {
		return EPlayer.stash().get(p.getUniqueId());
	}

	public static UEntity of(LivingEntity e) {
		return new UEntity(e);
	}

}
