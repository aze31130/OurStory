package ourstory.bosses;

import org.bukkit.entity.Monster;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public abstract class Boss {
	public Thread skills;
	public Monster entity;

	public String name;
	public int phase;
	public Difficulty difficulty;

	public Boss(String name, Difficulty difficulty) {
		this.name = name;
		this.phase = 1;
		this.difficulty = difficulty;
	}

	public abstract void onSpawn();

	public abstract void onHit(EntityDamageByEntityEvent event);

	public abstract void onDeath(EntityDeathEvent event);
}
