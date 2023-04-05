package org.aslstd.api.attributes.weapon;

import org.aslstd.api.attributes.AttrType;
import org.aslstd.api.attributes.BasicAttr;
import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.core.listeners.RegisterEventListener;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * <p>ExperienceBonus class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class ExperienceBonus extends BasicAttr implements Listener {

	/**
	 * <p>Constructor for ExperienceBonus.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param base a double
	 * @param perLevel a double
	 */
	public ExperienceBonus(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel, AttrType.PER_LEVEL);
		RegisterEventListener.register("exp-bonus", this);
	}

	/**
	 * <p>onEntityDeath.</p>
	 *
	 * @param e a {@link org.bukkit.event.entity.EntityDeathEvent} object
	 */
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
