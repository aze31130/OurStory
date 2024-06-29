package ourstory.events;

import org.bukkit.Effect;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Entity implements Listener {
	@EventHandler
	public void onEntityHit(final EntityDamageByEntityEvent entity) {
		if ((entity.getDamager() instanceof Snowball)) {
			entity.setDamage(1);
			entity.getEntity().getWorld().playEffect(entity.getEntity().getLocation(), Effect.STEP_SOUND, 80, 1);
		}
		if ((entity.getDamager() instanceof Egg)) {
			entity.setDamage(3);
			entity.getEntity().getWorld().playEffect(entity.getEntity().getLocation(), Effect.STEP_SOUND, 80, 1);
		}
	}
}
