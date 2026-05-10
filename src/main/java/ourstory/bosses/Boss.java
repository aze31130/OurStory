package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.spells.*;

public abstract class Boss {
	// Entity that reprensent the boss
	public Mob entity;
	public List<Player> targets;
	public String name;
	public int level;
	public BossBar healthBar;

	public Set<Spell> spells = new HashSet<>();

	public Boss(String name, Mob mob, List<Player> targets, int level) {
		this.name = name;
		this.entity = mob;
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

	/*
	 * If a boss is killed in hard mode, then we trigger all loots tables bellow
	 */
	public void generateDrops(EntityDeathEvent event, List<LootEntry> loots) {
		Random random = new Random();

		for (LootEntry le : loots) {
			int rng = random.nextInt(101);

			if (rng < le.proba()) {
				int quantity = Math.max(1, random.nextInt(le.maxQuantity() + 1));

				ItemStack item = le.item().clone();
				item.setAmount(quantity);
				event.getDrops().add(item);
			}
		}
	}
}
