package ru.asl.modules.playerattr;

import org.bukkit.entity.LivingEntity;

import ru.asl.core.Core;
import ru.asl.core.configs.EConfig;
import ru.asl.modules.playerattr.basic.BasicAttr;
import ru.asl.modules.playerattr.basic.interfaze.AffectingEntity;

public class MaxHealth extends BasicAttr implements AffectingEntity {

	public MaxHealth(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void initCustomSettings() {
		final EConfig cfg = Core.getCfg();

		if (cfg.contains("max-health")) {
			double base = 20.0;
			if (cfg.contains("max-health.base")) {
				base = cfg.getDouble("max-health.base");
				cfg.set("max-health.base", null);
			}
			double cost = 0.0;
			if (cfg.contains("max-health.cost-value")) {
				cost = cfg.getDouble("max-health.cost-value");
				cfg.set("max-health.cost-value", null);
			}
			double perLevel = 0.0;
			if (cfg.contains("max-health.per-level")) {
				perLevel = cfg.getDouble("max-health.per-level");
				cfg.set("max-health.per-level", null);
			}
			String visualName = getVisualName();
			if (cfg.contains("max-health.visual-name")) {
				visualName = cfg.getString("max-health.visual-name");
				cfg.set("max-health.visual-name", null);
			}
			boolean isEnabled = true;
			if (cfg.contains("max-health.is-enabled")) {
				isEnabled = cfg.getBoolean("max-health.is-enabled");
				cfg.set("max-health.is-enabled", null);
			}

			cfg.set("max-health", null);

			statCfg.set("max-health.is-enabled", isEnabled);
			statCfg.set("max-health.base", base);
			statCfg.set("max-health.per-level", perLevel);
			statCfg.set("max-health.visual-name", visualName);
			statCfg.set("max-health.cost-value", cost);
		}
	}

	@Override
	public void affectEntity(LivingEntity entity) {

	}

}
