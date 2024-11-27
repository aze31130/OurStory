package ourstory.events;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onEntityHit implements Listener {
	@EventHandler
	public void entityHit(EntityDamageByEntityEvent entity) {
		if ((entity.getDamager() instanceof Snowball))
			entity.setDamage(1);

		if ((entity.getDamager() instanceof Egg))
			entity.setDamage(3);
	}
}
