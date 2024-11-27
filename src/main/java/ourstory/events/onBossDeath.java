package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ourstory.storage.Storage;

public class onBossDeath implements Listener {
	@EventHandler
	public void bossDeath(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;


		// Call on death boss method
		if (entity.getEntity().getMetadata("isBoss").size() > 0) {
			Storage s = Storage.getInstance();
			s.bossInstance.monster.onDeath(entity);
		}
	}
}
