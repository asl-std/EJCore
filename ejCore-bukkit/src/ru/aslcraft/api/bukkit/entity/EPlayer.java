package ru.aslcraft.api.bukkit.entity;

import java.lang.reflect.Method;
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
import ru.aslcraft.api.bukkit.entity.interfaze.EJPlayer;
import ru.aslcraft.api.bukkit.equip.EquipInventory;
import ru.aslcraft.api.bukkit.equip.EquipSlot;
import ru.aslcraft.api.bukkit.events.EPlayerRegisteredEvent;
import ru.aslcraft.api.bukkit.items.ItemStackUtil;
import ru.aslcraft.api.bukkit.value.DoubleSettings;
import ru.aslcraft.api.bukkit.value.StringSettings;
import ru.aslcraft.api.bukkit.value.util.ValueUtil;
import ru.aslcraft.api.bukkit.yaml.YAML;
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
 * Для получения объекта этого класса из игрока надо использовать {@link ru.aslcraft.api.ejcore.entity.EPlayer#getEJPlayer(Player)}<br>
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
 * В начале класса описаны константы путей переменных в {@link ru.aslcraft.api.ejcore.entity.EPlayer#tempSettings}
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
	public static void unregister(Player p) {
		if (registeredEPlayers.containsKey(p.getUniqueId())) {
			registeredEPlayers.remove(p.getUniqueId()).save();
		}
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
	 * @return a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
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
	 * @return a {@link ru.aslcraft.api.ejcore.entity.interfaze.EJPlayer} object
	 */
	@Deprecated
	public static EJPlayer getEJPlayer(Player p) {
		return getEPlayer(p);
	}

	@Getter private DoubleSettings tempSettings;
	@Getter private StringSettings settings;
	@Getter private Player player;
	@Getter private EquipInventory equipInventory = new EquipInventory();

	@Getter private Method update;
	@Getter private Object instance;

	public void setUpdateMethod(Method method, Object instance) {
		if (update == null)
			update = method;
		this.instance = instance;
	}

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
	 * @param slot a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
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
		if (update != null) { try { update.invoke(instance, this); } catch (final Exception e) { e.printStackTrace(); } return; }

		final double defHealth = 20;
		final double classHealth = getTempSettings().getValue(CLASS_HEALTH, getLevel());

		final double maxHealth = defHealth + classHealth;
		changeMaxHealth(maxHealth >= 0 ? maxHealth : 1);
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
		final double maxHunger = tempSettings.getAndScale("player.hunger-max", getLevel());
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
		final double maxHunger = tempSettings.getAndScale("player.hunger-max", getLevel());
		final double currHunger = ValueUtil.parseDouble(getSettings().getValue(HUNGER_CURRENT));

		final double hungerModifier = currHunger / maxHunger;
		getSettings().setValue(HUNGER_CURRENT, (newValue * hungerModifier) + "");
		getTempSettings().setBase(HUNGER_MAX, newValue);
	}

	/**
	 * Изменяет количество сердец на интерфейсе относительно<br>
	 * текущего количества здоровья и переменной HEALTH_PER_BAR.
	 *
	 * Используется в методе {@link ru.aslcraft.api.ejcore.entity.EPlayer#changeMaxHealth(double)};
	 */
	protected void fixHealthBarScale() {
		final AttributeInstance attr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

		player.setHealthScaled(true);

		if (YAML.getMainConfig() != null && YAML.getMainConfig().getBoolean("health-bar.fix-to-one-line", true, true))
			player.setHealthScale(attr.getBaseValue() / YAML.getMainConfig().getInt("health-bar.health-per-bar", 20, true) * 20.0D);
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

	public void save() {
		settings.exportToYAML(YAML.getPlayerFile(player), "");
	}

}

