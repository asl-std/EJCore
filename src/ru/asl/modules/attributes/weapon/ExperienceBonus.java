package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.core.Core;
import ru.asl.modules.attributes.AttrType;
import ru.asl.modules.attributes.BasicAttr;

public final class ExperienceBonus extends BasicAttr implements Listener {

	public ExperienceBonus(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel, AttrType.PER_LEVEL);
		Core.getEventLoader().addListener("exp-bonus", this);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster || e.getEntity() instanceof Animals) {
			if (e.getEntity().getKiller() == null) return;
			final Player p = e.getEntity().getKiller();
			final EPlayer rp = EPlayer.getEPlayer(p);

			if (p == null) return;

			final double[] expBonus = rp.getStatValue(this);

			final double expRec = e.getDroppedExp()*(expBonus[0]/100);
			final double currExp = p.getExp()*p.getExpToLevel();

			double summExp = expRec+currExp;

			while (summExp > p.getExpToLevel()) {
				summExp = summExp-p.getExpToLevel();
				p.setLevel(p.getLevel()+1);
			}

			p.setExp((float) (summExp / p.getExpToLevel()));
			e.setDroppedExp(0);
		}
	}
}
