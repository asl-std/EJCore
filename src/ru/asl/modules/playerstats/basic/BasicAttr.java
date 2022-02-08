package ru.asl.modules.playerstats.basic;

import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.value.DoubleSettings;
import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;

// Базовый класс атрибутов.
public class BasicAttr {
	public static final String
	FIRST_VALUE 				= "base-values.first",
	SECOND_VALUE 				= "base-values.second";

	public static final Pattern getRegexPattern(BasicAttr stat) { //\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)
		return Pattern.compile(EText.e(stat.getVisualName().toLowerCase() + ".?\\s*([+-]?\\d+\\.?\\d*\\-?\\d*\\.?\\d*[%]?)"), Pattern.CASE_INSENSITIVE);
	}

	protected YAML statCfg = null;
	@Getter private int priority = 1;
	@Getter @Setter private int uniquePosition = 0;

	public void setPriority(int priority) { this.priority = priority; }
	public void setPriority(Priority priority) { setPriority(priority.getPriority()); }

	@Getter private final String key;
	@Getter protected String path;
	@Getter protected StatType type = StatType.PER_LEVEL;
	protected double defBase, defPerLevel;

	protected DoubleSettings settings = new DoubleSettings();

	public void setFirstValue(double firstValue) 	{ settings.setCustom(FIRST_VALUE, firstValue); }
	public void setSecondValue(double secondValue) 	{ settings.setCustom(SECOND_VALUE, secondValue); }
	public double getFirstValue()	{ return settings.get(FIRST_VALUE); }
	public double getSecondValue()	{ return settings.get(SECOND_VALUE); }

	public boolean isEnabled() { return statCfg.getBoolean(toString() + ".is-enabled", true, true); }

	public double getAndScale(int modifier) {
		if (getType() != StatType.PER_LEVEL) {
			EText.warn(toString() + ": You can't scale values from RANGE or STATIC stats, skipped.");
			return getFirstValue();
		}
		return getFirstValue() + getSecondValue() * modifier;
	}

	public BasicAttr(String keyName, String path, double defBase, double defPerLevel, StatType type) {
		key = keyName;
		this.path = path;
		this.defBase = defBase;
		this.defPerLevel = defPerLevel;
		this.type = type;

		if (statCfg == null)
			statCfg = new YAML(Core.instance().getDataFolder() + "/stats/" + toString() + ".yml");

		initCustomSettings();
		initializeBasicValues(defBase, defPerLevel);

		getVisualName();
		getCostValue();
		getColorDecorator();
	}

	public BasicAttr(String keyName, String path, double defBase, double defPerLevel) {
		key = keyName;
		this.path = path;
		this.defBase = defBase;
		this.defPerLevel = defPerLevel;

		if (statCfg == null)
			statCfg = new YAML(Core.instance().getDataFolder() + "/stats/" + toString() + ".yml");

		initCustomSettings();
		initializeBasicValues(defBase, defPerLevel);

		getVisualName();
		getCostValue();
		getColorDecorator();
	}

	public String getVisualName() { return statCfg.getString(toString() + ".visual-name", "&7" + WordUtils.capitalizeFully(toString().replaceAll("-", " ")), true); }

	public double getCostValue() { return statCfg.getDouble(toString() + ".cost-value", 0.0D, true); }

	public String getColorDecorator() { return statCfg.getString(toString() + ".suffix-color-decorator", "&7", true); }

	// 0 = minus or plus, 1 = first value, 2 = "-" if needed, or %, 3 = second
	// value, 4 = "%" if needed.
	public String getVisualTemplate() {
		if (getType() == StatType.RANGE)
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2$3$4";
		else
			return getVisualName() + ": " + getColorDecorator() + "$0$1$2";
	}

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

	public void initCustomSettings() {}

	protected void initializeBasicValues(double defBase, double defPerLevel) {
		switch(type) {
		case PER_LEVEL:
			setFirstValue(statCfg.getDouble(toString() + ".base", defBase, true));
			setSecondValue(statCfg.getDouble(toString() + ".per-level", defPerLevel, true));
			break;
		case RANGE:
			final String[] values = statCfg.getString(toString() + ".range-value", defBase + "-" + defPerLevel, true).replace(" ", "").split("-");
			if (values.length < 2) {
				EText.warn(toString() + ": found incorrect template, don't set only one value for this stat, you must write 2 values separated them with &a'-'&4 symbol. For example: &a'2.5-5.0'");
				EText.warn(toString() + ": initialisation skipped.. using " + defBase + "-" + defPerLevel + " as base value");
				setFirstValue(defBase);
				setSecondValue(defPerLevel);
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
				setFirstValue(defBase);
				setSecondValue(defPerLevel);
			}
			break;
		case SINGLE:
			setFirstValue(statCfg.getDouble(toString() + ".value", defBase, true));
			break;
		}
	}

	@Override
	public String toString() {
		return key.toLowerCase().replace('_', '-');
	}

}
