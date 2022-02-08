package ru.asl.api.varsystem;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ru.asl.api.varsystem.triggers.OnBuff;
import ru.asl.api.varsystem.triggers.OnDamage;
import ru.asl.api.varsystem.triggers.OnItemEquip;
import ru.asl.api.varsystem.triggers.OnSkill;

public abstract class Trigger {
	/** Срабатывает когда цель атакует другая цель. (Моб, Игрок, Энтити) */
	public static final Trigger ON_DAMAGE = new OnDamage("onDamage");
	/** Срабатывает когда игрок надевает предмет в любой слот экипировки. (Включая основную руку)*/
	public static final Trigger ON_ITEM_EQUIP = new OnItemEquip("onItemEquip");
	/** Срабатывает когда цель использует или является целью любого навыка. (Пассивные навыки игнорируются) */
	public static final Trigger ON_SKILL = new OnSkill("onSkill");
	/** Срабатывает когда цель накладывает или получает бафф/дебафф.*/
	public static final Trigger ON_BUFF = new OnBuff("onBuff");

	@Getter private final String key;
	@Getter private final List<Condition> allowedConditions;

	protected Trigger(String key) {
		this.key = key;
		allowedConditions = new ArrayList<>();
	}

}
