package ru.asl.core.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.api.bukkit.events.CombatType;
import ru.asl.api.bukkit.events.EntityDamagePrepareEvent;
import ru.asl.core.Core;
import ru.asl.modules.playerstats.basic.BasicStat;
import ru.asl.modules.playerstats.basic.interfaze.ListeningCombat;

public class CombatListener implements Listener {

	@EventHandler()
	public void throwCombatEvent(EntityDamageByEntityEvent e) {
		Entity attacker = e.getDamager();
		boolean ranged = false;

		if (attacker instanceof Projectile) {
			final Projectile p = (Projectile) attacker;
			attacker = (Entity) p.getShooter();
			ranged = true;
		}

		final Entity receiver = e.getEntity();
		final DamageCause cause = DamageCause.CUSTOM;

		final EntityDamagePrepareEvent edpe = new EntityDamagePrepareEvent(receiver, cause, e.getDamage(), CombatType.from(receiver, attacker), ranged);

		Bukkit.getPluginManager().callEvent(edpe);

		if (attacker != null && receiver != null) {
			final CombatEvent event = new CombatEvent(attacker, receiver, cause, edpe.getDamage(), CombatType.from(receiver, attacker), ranged, e.isCancelled());

			if (Core.getStats() != null) {
				final List<BasicStat> stats = Core.getStats().getSortedList();

				for (final BasicStat stat : stats) {
					if (stat instanceof ListeningCombat)
						((ListeningCombat) stat).listen(event);

					if (event.isCancelled())
						return;
				}
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
