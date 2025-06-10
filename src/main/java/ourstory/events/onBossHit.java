package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ourstory.Main;

public class onBossHit implements Listener {
	@EventHandler
	public void bossHit(EntityDamageByEntityEvent entity) {
		// Cancel if not player
		if (!(entity.getDamager() instanceof Player))
			return;

		// Call onHit method for boss monsters
		if (!entity.getEntity().getMetadata("isBoss").isEmpty())
			Main.runningInstances.get(0).boss.onHit(entity);
	}
}
