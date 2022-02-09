package ru.asl.modules.playerattr.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Priority {
	AFTER_DAMAGE_CALCULATING(16),
	BEFORE_DAMAGE_CALCULATING(1);

	@Getter private int priority;

	public static int before(BasicAttr stat) {
		return stat.getPriority()-1;
	}

	public static int after(BasicAttr stat) {
		return stat.getPriority()+1;
	}

}
