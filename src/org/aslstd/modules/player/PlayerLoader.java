package org.aslstd.modules.player;

import org.aslstd.api.bukkit.events.EPlayerRegisteredEvent;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.managers.WAttributes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLoader implements Listener {

	@EventHandler
	public void onPlayerRegister(EPlayerRegisteredEvent e) {

		try {
			e.getPlayer().setUpdateMethod(PlayerUtils.class.getMethod("updateStats", e.getPlayer().getClass()), PlayerUtils.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}

		for (final BasicAttr stat : WAttributes.getRegistered()) {
			if (!stat.isEnabled())
				continue;
			switch (stat.getType()) {
			case PER_LEVEL:
				e.getPlayer().getTempSettings().setValue(stat.getPath(), stat.getFirstValue(), stat.getSecondValue());
			case RANGE:
				e.getPlayer().getTempSettings().setRange(stat.getPath(), stat.getFirstValue(), stat.getSecondValue());
			case SINGLE:
				e.getPlayer().getTempSettings().setCustom(stat.getPath(), stat.getFirstValue());
			}
		}

		e.getPlayer().updateStats();

	}


}
