package org.aslstd.core.listeners;

import java.util.List;

import org.aslstd.api.attributes.BasicAttr;
import org.aslstd.api.attributes.ListeningCombat;
import org.aslstd.api.attributes.managers.WAttributes;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.api.bukkit.events.combat.EntityDamagePrepareEvent;
import org.aslstd.api.ejcore.plugin.BukkitListener;
import org.aslstd.api.ejcore.plugin.Named;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * <p>CombatListener class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@Named(key = "combatEventCustom")
public class CombatListener implements BukkitListener {

	/**
	 * <p>throwCombatEvent.</p>
	 *
	 * @param e a {@link org.bukkit.event.entity.EntityDamageByEntityEvent} object
	 */
	@EventHandler()
	public void throwCombatEvent(EntityDamageByEntityEvent e) {
		Entity attacker = e.getDamager();
		boolean ranged = false;

		if (attacker instanceof final Projectile p)
			if (p.getShooter() instanceof final Entity s) {
				attacker = s;
				ranged = true;
			}

		final Entity receiver = e.getEntity();
		final DamageCause cause = DamageCause.CUSTOM;

		final EntityDamagePrepareEvent edpe = new EntityDamagePrepareEvent(receiver, cause, e.getDamage(), CombatType.from(receiver, attacker), ranged);

		Bukkit.getPluginManager().callEvent(edpe);

		if (attacker != null && receiver != null) {
			final CombatEvent event = new CombatEvent(attacker, receiver, cause, edpe.getDamage(), CombatType.from(receiver, attacker), ranged, e.isCancelled());

			final List<BasicAttr> stats = WAttributes.getSortedList();

			for (final BasicAttr stat : stats) {
				if (stat instanceof ListeningCombat)
					((ListeningCombat) stat).listen(event);

				if (event.isCancelled())
					return;
			}


			Bukkit.getPluginManager().callEvent(event);

			if (event.isCancelled()) {
				e.setCancelled(true);
				return;
			}

			e.setDamage(event.getDamage());
		}
	}

}
