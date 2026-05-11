package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ourstory.Main;

public class onBossHit implements Listener {
	@EventHandler
	public void bossHit(EntityDamageByEntityEvent entity) {
		if (Main.runningInstance == null)
			return;

		if (!(entity.getDamager() instanceof Player))
			return;

		if (entity.getEntity().getMetadata("isBoss").isEmpty())
			return;

		Main.runningInstance.boss.onHit(entity);
	}
}
