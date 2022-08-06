package ru.aslcraft.modules.attribute;

import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

import lombok.Getter;
import lombok.Setter;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.ejcore.value.DoubleSettings;
import ru.aslcraft.api.ejcore.yaml.YAML;
import ru.aslcraft.core.Core;

// Базовый класс атрибутов.
/**
 * <p>BasicAttr class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BasicAttr {
	/** Constant <code>FIRST_VALUE="base-values.first"</code> */
	/** Constant <code>SECOND_VALUE="base-values.second"</code> */
	public static final String
	FIRST_VALUE 				= "base-values.first",
	SECOND_VALUE 				= "base-values.second";

	/**
	 * <p>getRegexPattern.</p>
	 *
	 * @param stat a {@link ru.aslcraft.modules.attribute.BasicAttr} object
	 * @return a {@link java.util.regex.Pattern} object
	 */
	public static final Pattern getRegexPattern(BasicAttr stat) { //\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)
		return Pattern.compile(EText.e(stat.getVisualName().toLowerCase() + ".?\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)"), Pattern.CASE_INSENSITIVE);
	}

	protected YAML statCfg = null;
	@Getter private int priority = 1;
	@Getter @Setter private int uniquePosition = 0;

	/**
	 * <p>Setter for the field <code>priority</code>.</p>
	 *
	 * @param priority a int
	 */
	public void setPriority(int priority) { this.priority = priority; }
	/**
	 * <p>Setter for the field <code>priority</code>.</p>
	 *
	 * @param priority a {@link ru.aslcraft.modules.attribute.Priority} object
	 */
	public void setPriority(Priority priority) { setPriority(priority.getPriority()); }

	@Getter private final String key;
	@Getter protected String path;
	@Getter protected AttrType type = AttrType.PER_LEVEL;
	@Getter protected double defaultValue, defaultPerLevel;

	protected DoubleSettings settings = new DoubleSettings();

	/**
	 * <p>setFirstValue.</p>
	 *
	 * @param firstValue a double
	 */
	public void setFirstValue(double firstValue) 	{ settings.setCustom(FIRST_VALUE, firstValue); }
	/**
	 * <p>setSecondValue.</p>
	 *
	 * @param secondValue a double
	 */
	public void setSecondValue(double secondValue) 	{ settings.setCustom(SECOND_VALUE, secondValue); }
	/**
	 * <p>getFirstValue.</p>
	 *
	 * @return a double
	 */
	public double getFirstValue()	{ return settings.get(FIRST_VALUE); }
	/**
	 * <p>getSecondValue.</p>
	 *
	 * @return a double
	 */
	public double getSecondValue()	{ return settings.get(SECOND_VALUE); }

	/**
	 * <p>isEnabled.</p>
	 *
	 * @return a boolean
	 */
	public boolean isEnabled() { return statCfg.getBoolean(toString() + ".is-enabled", true, true); }

	/**
	 * <p>getAndScale.</p>
	 *
	 * @param modifier a int
	 * @return a double
	 */
	public double getAndScale(int modifier) {
		if (getType() != AttrType.PER_LEVEL) {
			EText.warn(toString() + ": You can't scale values from RANGE or STATIC stats, skipped.");
			return getFirstValue();
		}
		return getFirstValue() + getSecondValue() * modifier;
	}

	/**
	 * <p>Constructor for BasicAttr.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 * @param type a {@link ru.aslcraft.modules.attribute.AttrType} object
	 */
	public BasicAttr(String keyName, String path, double defBase, double defPerLevel, AttrType type) {
		key = keyName;
		this.path = path;
		defaultValue = defBase;
		defaultPerLevel = defPerLevel;
		this.type = type;

		if (statCfg == null)
			statCfg = new YAML(Core.instance().getDataFolder() + "/attr/" + toString() + ".yml");

		initCustomSettings();
		initializeBasicValues();

		getVisualName();
		getCostValue();
		getColorDecorator();
	}

	/**
	 * <p>Constructor for BasicAttr.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public BasicAttr(String keyName, String path, double defBase, double defPerLevel) {
		key = keyName;
		this.path = path;
		defaultValue = defBase;
		defaultPerLevel = defPerLevel;

		if (statCfg == null)
			statCfg = new YAML(Core.instance().getDataFolder() + "/attr/" + toString() + ".yml");

		initCustomSettings();
		initializeBasicValues();

		getVisualName();
		getCostValue();
		getColorDecorator();
	}

	/**
	 * <p>getVisualName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getVisualName() { return statCfg.getString(toString() + ".visual-name", "&7" + WordUtils.capitalizeFully(toString().replaceAll("-", " ")), true); }

	/**
	 * <p>getCostValue.</p>
	 *
	 * @return a double
	 */
	public double getCostValue() { return statCfg.getDouble(toString() + ".cost-value", 0.0D, true); }

	/**
	 * <p>getColorDecorator.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getColorDecorator() { return statCfg.getString(toString() + ".suffix-color-decorator", "&7", true); }

	// 0 = minus or plus, 1 = first value, 2 = "-" if needed, or %, 3 = second
	// value, 4 = "%" if needed.
	/**
	 * <p>getVisualTemplate.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getVisualTemplate() {
		if (getType() == AttrType.RANGE)
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2$3$4";
		else
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2";
	}

	/**
	 * <p>convertToLore.</p>
	 *
	 * @param values a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String convertToLore(String... values) {
		String converted = getVisualTemplate();
		int $ = 0;

		for (final String dod : values) {
			converted = converted.replace("$" + $, dod);
			$++;
		}

		while (converted.contains("$" + $)) {
			converted = converted.replace("$" + $, "");
			$++;
		}

		return converted;
	}

	/**
	 * <p>initCustomSettings.</p>
	 */
	public void initCustomSettings() {}

	/**
	 * <p>initializeBasicValues.</p>
	 */
	public void initializeBasicValues() {
		switch(type) {
		case PER_LEVEL:
			setFirstValue(statCfg.getDouble(toString() + ".base", getDefaultValue(), true));
			setSecondValue(statCfg.getDouble(toString() + ".per-level", getDefaultPerLevel(), true));
			break;
		case RANGE:
			final String[] values = statCfg.getString(toString() + ".range-value", getDefaultValue() + "-" + getDefaultPerLevel(), true).replace(" ", "").split("-");
			if (values.length < 2) {
				EText.warn(toString() + ": found incorrect template, don't set only one value for this stat, you must write 2 values separated them with &a'-'&4 symbol. For example: &a'2.5-5.0'");
				EText.warn(toString() + ": initialisation skipped.. using " + getDefaultValue() + "-" + getDefaultPerLevel() + " as base value");
				setFirstValue(getDefaultValue());
				setSecondValue(getDefaultPerLevel());
				break;
			}
			try {
				final double first = Double.parseDouble(values[0]);
				double second = Double.parseDouble(values[1]);

				if (second < first) {
					EText.warn(toString() + ": &5" + first + "-" + second + " | &aSecond value&4 MUST be greater or equal the &afirst value&4. For now second value will be cloned from first value");
					second = first;
				}

				setFirstValue(first);
				setSecondValue(second);
			} catch(final NumberFormatException e) {
				EText.warn("RANGE value: &5" + toString()+ ": &5" + values[0] + "-" + values[1] + " |  has incorrect symbols, you must write 2 values separated them with &a'-'&4 symbol. For example: &a'2.5-5.0'");
				setFirstValue(getDefaultValue());
				setSecondValue(getDefaultPerLevel());
			}
			break;
		case SINGLE:
			setFirstValue(statCfg.getDouble(toString() + ".value", getDefaultValue(), true));
			break;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return key.toLowerCase();
	}

}
