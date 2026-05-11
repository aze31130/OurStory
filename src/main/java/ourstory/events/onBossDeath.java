package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ourstory.Main;

public class onBossDeath implements Listener {
	@EventHandler
	public void bossDeath(EntityDeathEvent entity) {
		if (Main.runningInstance == null)
			return;

		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		if (entity.getEntity().getMetadata("isBoss").isEmpty())
			return;

		Main.runningInstance.boss.onDeath(entity);
	}
}
