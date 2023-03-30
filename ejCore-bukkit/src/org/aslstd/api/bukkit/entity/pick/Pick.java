package org.aslstd.api.bukkit.entity.pick;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Pick {

	public static UPlayer of(Player p) {
		return new UPlayer(p);
	}

	public static UEntity of(LivingEntity e) {
		return new UEntity(e);
	}


}
