package ru.aslcraft.modules.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.commandbook.util.PlayerUtil;

import ru.aslcraft.api.ejcore.events.EPlayerRegisteredEvent;
import ru.aslcraft.modules.MAttributes;
import ru.aslcraft.modules.attribute.BasicAttr;

public class PlayerLoader implements Listener {

	@EventHandler
	public void onPlayerRegister(EPlayerRegisteredEvent e) {

		try {
			e.getPlayer().setUpdateMethod(PlayerUtils.class.getMethod("updateStats", e.getPlayer().getClass()), PlayerUtil.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}


		for (final BasicAttr stat : MAttributes.getWeaponAttributes().getRegistered()) {
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
