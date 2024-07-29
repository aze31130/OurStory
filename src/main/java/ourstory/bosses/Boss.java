package ourstory.bosses;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class Boss {
	public Monster entity;

	public abstract void onSpawn();

	public abstract void onHit(EntityDamageByEntityEvent damager);

	public abstract void onDeath(Entity damager);
}
