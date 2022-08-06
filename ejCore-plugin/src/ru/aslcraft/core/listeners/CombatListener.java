package ru.aslcraft.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import ru.aslcraft.api.bukkit.events.combat.CombatEvent;
import ru.aslcraft.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.aslcraft.api.bukkit.events.combat.EntityDamagePrepareEvent;

/**
 * <p>CombatListener class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class CombatListener implements Listener {

	/**
	 * <p>throwCombatEvent.</p>
	 *
	 * @param e a {@link org.bukkit.event.entity.EntityDamageByEntityEvent} object
	 */
	@EventHandler()
	public void throwCombatEvent(EntityDamageByEntityEvent e) {
		final Entity attacker = e.getDamager();
		boolean ranged = false;

		if (attacker instanceof Projectile) {
			final Projectile p = (Projectile) attacker;
			ranged = true;
		}

		final Entity receiver = e.getEntity();
		final DamageCause cause = DamageCause.CUSTOM;

		final EntityDamagePrepareEvent edpe = new EntityDamagePrepareEvent(receiver, cause, e.getDamage(), CombatType.from(receiver, attacker), ranged);

		Bukkit.getPluginManager().callEvent(edpe);

		if (attacker != null && receiver != null) {
			final CombatEvent event = new CombatEvent(attacker, receiver, cause, edpe.getDamage(), CombatType.from(receiver, attacker), ranged, e.isCancelled());

			Bukkit.getPluginManager().callEvent(event);

			if (event.isCancelled()) {
				e.setCancelled(true);
				return;
			}

			e.setDamage(event.getDamage());
		}
	}

}
