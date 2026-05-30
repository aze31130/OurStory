package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.spells.*;

public abstract class Boss {
	// Entity that reprensent the boss
	public Mob entity;
	public List<Entity> targets;
	public String name;
	public int level;
	public BossBar healthBar;

	public Set<Spell> spells = new HashSet<>();

	public Boss(String name, List<Entity> targets, int level) {
		this.name = name;
		this.targets = targets;
		this.level = level;
	}

	/**
	 * Enregistre / Supprime des comportements (goals) du boss.
	 */
	public abstract void registerGoals(final MobGoals goals);

	/*
	 * Some dialogue / effects here
	 */
	public abstract void onSpawn();

	public abstract void onHit(EntityDamageByEntityEvent event);

	public abstract void onDeath(EntityDeathEvent event);
}
