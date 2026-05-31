package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import com.destroystokyo.paper.entity.ai.MobGoals;
import net.kyori.adventure.text.Component;
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

		this.healthBar = Bukkit.createBossBar(this.name, BarColor.RED, BarStyle.SOLID);
		this.healthBar.setVisible(true);
		this.healthBar.setProgress(1.0);

		for (Entity target : targets) {
			this.healthBar.addPlayer((Player) target);
		}
	}

	/**
	 * Enregistre / Supprime des comportements (goals) du boss.
	 */
	public abstract void registerGoals(final MobGoals goals);

	public void updateBossBar() {
		double progress = this.entity.getHealth() / this.entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		this.healthBar.setProgress(progress);
		Bukkit.broadcast(Component.text(this.entity.getHealth() + " " + this.entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue()));

		if (this.entity.isDead())
			this.healthBar.removeAll();
	}

	/*
	 * Some dialogue / effects here
	 */
	public abstract void onSpawn();

	public abstract void onHit(EntityDamageByEntityEvent event);

	public abstract void onDeath(EntityDeathEvent event);
}
