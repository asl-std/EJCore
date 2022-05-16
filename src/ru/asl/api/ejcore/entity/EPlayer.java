package ru.asl.api.ejcore.entity;

import ru.asl.core.Core;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import ru.asl.api.ejcore.entity.interfaze.EJPlayer;
import ru.asl.api.ejcore.equip.EquipInventory;
import ru.asl.api.ejcore.equip.EquipSlot;
import ru.asl.api.ejcore.events.EPlayerRegisteredEvent;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejcore.value.DoubleSettings;
import ru.asl.api.ejcore.value.StringSettings;
import ru.asl.api.ejcore.value.util.MathUtil;
import ru.asl.api.ejcore.value.util.ValueUtil;
import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.modules.attributes.BasicAttr;
/**
 * Paths for Settings:<br>
 *     player:<br>
 *     • health-max<br>
 *     • health-regen<br>
 *     • hunger-max<br>
 *     • hunger-current<br>
 *     • speed<br>
 *     • level<br>
 *     class:<br>
 *     • name<br>
 *     • attributes:<br>
 *         ♦ strength<br>
 *         ♦ dexterity<br>
 *         ♦ intelligence<br>
 *         ♦ endurance<br>
 *     • health:<br>
 *     • health-regen:<br>
 *     • mana:<br>
 *     • mana-regen:<br>
 *     • stamina:<br>
 *     • stamina-regen:<br>
 *     equip:<br><br>
 *
 * Является внутренним классом игрока который облегчает обработку данных между плагинами и игроком<br>
 * Для получения объекта этого класса из игрока надо использовать {@link ru.asl.api.ejcore.entity.EPlayer#getEJPlayer(Player)}<br>
 * При необходимости понадобится сконверировать полученный EJPlayer в EPlayer, пример:<br><br>
 *
 * public void a(Player p) {<br>
 *     EJPlayer ejp = EPlayer.getEPlayer(p);<br>
 *     EPlayer ep;<br><br>
 *
 *     if (ejp instanceof EPlayer) {<br>
 *         ep = (EPlayer) ejp;<br>
 *     }<br><br>
 *
 *     ep.changeMaxHealth(50);<br>
 *
 * }<br><br>
 *
 * В начале класса описаны константы путей переменных в {@link ru.asl.api.ejcore.entity.EPlayer#tempSettings}
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class EPlayer implements EJPlayer {

	/** Constant <code>LEVEL="player.level"</code> */
	/** Constant <code>HEALTH_MAX="player.health-max"</code> */
	/** Constant <code>HEALTH_REGEN="player.health-regen"</code> */
	/** Constant <code>HUNGER_MAX="player.hunger-max"</code> */
	/** Constant <code>HUNGER_CURRENT="player.hunger-current"</code> */
	/** Constant <code>CLASS_HEALTH="player.classes.health-max"</code> */
	/** Constant <code>CLASS_HEALTH_REGEN="player.classes.health-regen"</code> */
	/** Constant <code>CLASS_MANA="player.classes.mana-max"</code> */
	/** Constant <code>CLASS_MANA_REGEN="player.classes.mana-regen"</code> */
	/** Constant <code>CLASS_STAMINA="player.classes.stamina-max"</code> */
	/** Constant <code>CLASS_STAMINA_REGEN="player.classes.stamina-regen"</code> */
	/** Constant <code>CLASS_NAME="player.classes.class-name"</code> */
	/** Constant <code>CLASS_STRENGTH="player.classes.attributes.strength"</code> */
	/** Constant <code>CLASS_DEXTERITY="player.classes.attributes.dexterity"</code> */
	/** Constant <code>CLASS_INTELLIGENCE="player.classes.attributes.intelligence"</code> */
	/** Constant <code>CLASS_ENDURANCE="player.classes.attributes.endurance"</code> */
	/** Constant <code>RACE_HEALTH="player.race.health-max"</code> */
	/** Constant <code>RACE_HEALTH_REGEN="player.race.health-regen"</code> */
	/** Constant <code>RACE_MANA="player.race.mana-max"</code> */
	/** Constant <code>RACE_MANA_REGEN="player.race.mana-regen"</code> */
	/** Constant <code>RACE_STAMINA="player.race.stamina-max"</code> */
	/** Constant <code>RACE_STAMINA_REGEN="player.race.stamina-regen"</code> */
	/** Constant <code>RACE_NAME="player.race.race-name"</code> */
	/** Constant <code>RACE_STRENGTH="player.race.attributes.strength"</code> */
	/** Constant <code>RACE_DEXTERITY="player.race.attributes.dexterity"</code> */
	/** Constant <code>RACE_INTELLIGENCE="player.race.attributes.intelligence"</code> */
	/** Constant <code>RACE_ENDURANCE="player.race.attributes.endurance"</code> */
	/** Constant <code>PARTY_ID="player.party"</code> */
	/** Constant <code>GUILD_ID="player.guild"</code> */
	public static final String
	LEVEL 				= "player.level",
	HEALTH_MAX 			= "player.health-max",
	HEALTH_REGEN 		= "player.health-regen",
	HUNGER_MAX 			= "player.hunger-max",
	HUNGER_CURRENT 		= "player.hunger-current",

	CLASS_HEALTH 		= "player.classes.health-max",
	CLASS_HEALTH_REGEN 	= "player.classes.health-regen",
	CLASS_MANA 			= "player.classes.mana-max",
	CLASS_MANA_REGEN 	= "player.classes.mana-regen",
	CLASS_STAMINA 		= "player.classes.stamina-max",
	CLASS_STAMINA_REGEN = "player.classes.stamina-regen",
	CLASS_NAME 			= "player.classes.class-name",
	CLASS_STRENGTH 		= "player.classes.attributes.strength",
	CLASS_DEXTERITY 	= "player.classes.attributes.dexterity",
	CLASS_INTELLIGENCE 	= "player.classes.attributes.intelligence",
	CLASS_ENDURANCE 	= "player.classes.attributes.endurance",

	RACE_HEALTH			= "player.race.health-max",
	RACE_HEALTH_REGEN	= "player.race.health-regen",
	RACE_MANA			= "player.race.mana-max",
	RACE_MANA_REGEN		= "player.race.mana-regen",
	RACE_STAMINA		= "player.race.stamina-max",
	RACE_STAMINA_REGEN	= "player.race.stamina-regen",
	RACE_NAME			= "player.race.race-name",
	RACE_STRENGTH		= "player.race.attributes.strength",
	RACE_DEXTERITY		= "player.race.attributes.dexterity",
	RACE_INTELLIGENCE	= "player.race.attributes.intelligence",
	RACE_ENDURANCE		= "player.race.attributes.endurance",

	PARTY_ID			= "player.party",
	GUILD_ID			= "player.guild";

	/** Constant <code>registeredEPlayers</code> */
	public static ConcurrentMap<UUID, EPlayer> registeredEPlayers = new ConcurrentHashMap<>();

	/**
	 * <p>isRegistered.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a boolean
	 */
	public static boolean isRegistered(Player p) {
		return registeredEPlayers.containsKey(p.getUniqueId());
	}

	/**
	 * <p>removeRegistered.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public static void removeRegistered(Player p) {
		if (registeredEPlayers.containsKey(p.getUniqueId())) registeredEPlayers.remove(p.getUniqueId());
	}

	/**
	 * <p>registerEPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public static void registerEPlayer(Player p) {
		EPlayer.registeredEPlayers.put(p.getUniqueId(), new EPlayer(p));
	}

	/**
	 * <p>getEPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a {@link ru.asl.api.ejcore.entity.EPlayer} object
	 */
	public static EPlayer getEPlayer(Player p) {
		if (isRegistered(p))
			return EPlayer.registeredEPlayers.get(p.getUniqueId());
		else {
			EPlayer.registerEPlayer(p);
			return EPlayer.registeredEPlayers.get(p.getUniqueId());
		}
	}
	/**
	 * <p>getEJPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a {@link ru.asl.api.ejcore.entity.interfaze.EJPlayer} object
	 */
	@Deprecated
	public static EJPlayer getEJPlayer(Player p) {
		return getEPlayer(p);
	}

	@Getter private DoubleSettings tempSettings;
	@Getter private StringSettings settings;
	@Getter private Player player;
	@Getter private EquipInventory equipInventory = new EquipInventory();

	/**
	 * <p>Constructor for EPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 */
	public EPlayer(Player p) {
		player = p;
		tempSettings = new DoubleSettings();
		settings = new StringSettings();
		settings.importFromYAML(YAML.getPlayerFile(p), "");
		tempSettings.setCustom("player.hunger-max", 20d);

		final EPlayerRegisteredEvent event = new EPlayerRegisteredEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);

		if (Core.getAttr() != null) {
			for (final BasicAttr stat : Core.getAttr().getRegistered()) {
				if (!stat.isEnabled())
					continue;
				switch (stat.getType()) {
				case PER_LEVEL:
					tempSettings.setValue(stat.getPath(), stat.getFirstValue(), stat.getSecondValue());
				case RANGE:
					tempSettings.setRange(stat.getPath(), stat.getFirstValue(), stat.getSecondValue());
				case SINGLE:
					tempSettings.setCustom(stat.getPath(), stat.getFirstValue());
				}
			}
			updateStats();
		}
	}

	/**
	 * <p>getDamage.</p>
	 *
	 * @param stat a {@link ru.asl.modules.attributes.BasicAttr} object
	 * @return a double
	 */
	public double getDamage(BasicAttr stat) { // TODO
		if (!stat.getKey().contains("DAMAGE")) return 0d;

		final double[] damage = getStatValue(stat);
		return MathUtil.getRandomRange(damage[0], damage[1]);
	}

	/**
	 * <p>getBaseStatValue.</p>
	 *
	 * @param attr a {@link ru.asl.modules.attributes.BasicAttr} object
	 * @return an array of {@link double} objects
	 */
	public double[] getBaseStatValue(BasicAttr attr) {
		double[] values = { 0.0d, 0.0d };
		switch(attr.getType()) {
		case PER_LEVEL:
			values[0] = tempSettings.getAndScale(attr.getPath(), getLevel());
			break;
		case RANGE:
			values = tempSettings.getRange(attr.getPath());
			break;
		case SINGLE:
			values[0] = tempSettings.getValue(attr.getPath(), attr.getFirstValue());
			break;
		}
		return values;
	}

	/**
	 * <p>getStatValue.</p>
	 *
	 * @param stat a {@link ru.asl.modules.attributes.BasicAttr} object
	 * @return an array of {@link double} objects
	 */
	public double[] getStatValue(BasicAttr stat) {
		final double[] values = getBaseStatValue(stat);
		final double[] multiplier = new double[] { 0D, 0D };

		for (final EquipSlot slot : EquipSlot.values()) {
			if (tempSettings.hasRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey())) {
				final double[] val = tempSettings.getRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey());
				values[0] = values[0] + val[0];
				values[1] = values[1] + val[1];
			}

			if (tempSettings.hasRange("player.equip." + slot.name().toLowerCase() + "." +  stat.getKey() + "-multiplier")) {
				final double[] val = tempSettings.getRange("player.equip." + slot.name().toLowerCase() + "." + stat.getKey() + "-multiplier");
				multiplier[0] = multiplier[0] + val[0];
				multiplier[1] = multiplier[1] + val[1];
			}
		}

		if (multiplier[1] < multiplier[0]) multiplier[1] = multiplier[0];

		return new double[] { values[0] + (values[0] * (multiplier[0] / 100)), values[1] + (values[1] * (multiplier[1] / 100)) };
	}

	/**
	 * <p>getHealthSumm.</p>
	 *
	 * @return a double
	 */
	public double getHealthSumm() {
		final List<Entry<String, Double>> entryList = tempSettings.getKey("health-max");

		double val = 0d;
		for (final Entry<String,Double> entry : entryList) {
			val+= entry.getValue();
		}

		return val;
	}

	/**
	 * Добавляет предмет в слот виртуального инвентаря
	 *
	 * @param slot слот в который добавляется предмет
	 * @param stack сам предмет
	 */
	public void equip(EquipSlot slot, ItemStack stack) {
		if (!ItemStackUtil.isEquals(stack, equipInventory.getEquip(slot))) {
			removeEquip(slot);
			equipInventory.setItem(slot, stack);
		}
	}

	/**
	 * <p>checkEquipWith.</p>
	 *
	 * @param slot a {@link ru.asl.api.ejcore.equip.EquipSlot} object
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	public boolean checkEquipWith(EquipSlot slot,ItemStack stack) {
		return ItemStackUtil.isEquals(equipInventory.getEquip(slot), stack);
	}

	/**
	 * Убирает предмет из виртуального инвентаря
	 *
	 * @param slot слот в котором убирается предмет
	 */
	public void removeEquip(EquipSlot slot) {
		equipInventory.setItem(slot, null);
		tempSettings.removeKey(slot.name().toLowerCase());
	}

	/**
	 * <p>unequipAll.</p>
	 */
	public void unequipAll() {
		for (final EquipSlot slot : EquipSlot.values())
			removeEquip(slot);

		updateStats();
	}

	/**
	 * {@inheritDoc}
	 *
	 * Применяет изменения переменных HEALTH, CLASS HEALTH для изменения максимального количества жизней.
	 */
	@Override
	public void updateStats() {
		double defHealth = 20;
		if (Core.getAttr() != null)
			defHealth = getStatValue(Core.getAttr().getByKey("MAX_HEALTH"))[0];

		final double classHealth = tempSettings.getValue(CLASS_HEALTH, getLevel());

		final double maxHealth = defHealth + classHealth;
		changeMaxHealth(maxHealth >= 0 ? maxHealth : 1);

		if (Core.getAttr() == null) return;

		final double speed = getStatValue(Core.getAttr().getByKey("SPEED"))[0];

		if ((speed >= 0)) getPlayer().setWalkSpeed((float) ((MathUtil.getPercentsOfValue(20, speed) / 100) >= 1.0f ? 1.0f : MathUtil.getPercentsOfValue(20, speed) / 100));
	}

	/**
	 * Лечит игрока
	 *
	 * @param amount количество восполняемого здоровья
	 */
	public void heal(double amount) {
		if (amount <= 0) {
			player.setHealth(player.getHealth()-amount);
			return;
		}

		final AttributeInstance attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		final double currentHealth = player.getHealth();

		if (currentHealth + amount >= attr.getBaseValue())
			player.setHealth(attr.getBaseValue());
		else
			player.setHealth(currentHealth + amount);
	}

	/**
	 * Восполняет игроку голод и насыщение
	 *
	 * @param hunger количество восполняемого голода (может быть негативным)
	 * @param saturation количество восполняемого насыщения (может быть негативным)
	 */
	public void feed(int hunger, float saturation) {
		final double maxHunger = Core.getAttr() == null ? 20.0 : Math.floor(getStatValue(Core.getAttr().getByKey("MAX_HUNGER"))[0]);
		final double currHunger = ValueUtil.parseDouble(getSettings().getValue(HUNGER_CURRENT));
		float currSaturation = player.getSaturation();

		if (currHunger + hunger >= maxHunger)
			player.setFoodLevel(20);
		else
			player.setFoodLevel((int) (currHunger/maxHunger)*20 );

		if (currSaturation + saturation >= 20)
			currSaturation = 20;
		else currSaturation += saturation;

		player.setSaturation(currSaturation);
	}

	/**
	 * Изменяет максимальное количество здоровья
	 *
	 * @param newValue a double
	 */
	public void changeMaxHealth(double newValue) {
		final AttributeInstance attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		final double healthModifier = player.getHealth() / attr.getValue();

		attr.setBaseValue(newValue);
		fixHealthBarScale();

		if (newValue * healthModifier >= newValue)
			player.setHealth(newValue);
		else
			player.setHealth(newValue * healthModifier);
	}

	/**
	 * <p>changeMaxHunger.</p>
	 *
	 * @param newValue a double
	 */
	public void changeMaxHunger(double newValue) {
		final double maxHunger = Core.getAttr() == null ? 20.0 : Math.floor(getStatValue(Core.getAttr().getByKey("MAX_HUNGER"))[0]);
		final double currHunger = ValueUtil.parseDouble(getSettings().getValue(HUNGER_CURRENT));

		final double hungerModifier = currHunger / maxHunger;
		getSettings().setValue(HUNGER_CURRENT, (newValue * hungerModifier) + "");
		getTempSettings().setBase(HUNGER_MAX, newValue);
	}

	/**
	 * Изменяет количество сердец на интерфейсе относительно<br>
	 * текущего количества здоровья и переменной HEALTH_PER_BAR.
	 *
	 * Используется в методе {@link ru.asl.api.ejcore.entity.EPlayer#changeMaxHealth(double)};
	 */
	protected void fixHealthBarScale() {
		final AttributeInstance attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		player.setHealthScaled(true);

		if (!Core.getCfg().ONE_HP_BAR)
			player.setHealthScale(attr.getBaseValue() / Core.getCfg().HEALTH_PER_BAR * 20.0D);
		else
			player.setHealthScale(20.0D);
	}

	/**
	 * Изменяет переменную level внутри игрока.
	 * Аналогично можно использовать EPlayer.getTempSetings().setCustom(LEVEL, 1);
	 *
	 * @param level a int
	 */
	public void setLevel(int level) {
		tempSettings.setCustom(LEVEL, level);
	}

	/**
	 * Возвращает переменную level из игрока
	 * Аналогично можно использовать EPlayer.getTempSetings().getValue(LEVEL,0)
	 *
	 * @return a int
	 */
	public int getLevel() {
		return (int) Math.round(tempSettings.getValue(LEVEL, 0));
	}

}

