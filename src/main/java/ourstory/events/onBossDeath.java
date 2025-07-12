package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ourstory.Main;

public class onBossDeath implements Listener {
	@EventHandler
	public void bossDeath(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		// Call onHit method for boss monsters
		if (!entity.getEntity().getMetadata("isBoss").isEmpty())
			Main.runningInstance.boss.onDeath(entity);
	}
}
