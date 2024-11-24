package ourstory.bosses;

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Boss {
	public Thread skills;
	public Monster entity;

	public String name;
	public int phase;
	public Difficulty difficulty;
	public BossBar healthBar;

	public Boss(String name, Difficulty difficulty) {
		this.name = name;
		this.phase = 1;
		this.difficulty = difficulty;
	}

	public abstract void onSpawn();

	public abstract void onHit(EntityDamageByEntityEvent event);

	public abstract void onDeath(EntityDeathEvent event);

	/*
	 * If a boss is killed in hard mode, then we trigger all loots tables bellow
	 */
	public void generateDrops(EntityDeathEvent event, Map<Difficulty, List<LootEntry>> loots) {
		Random random = new Random();

		for (Map.Entry<Difficulty, List<LootEntry>> entry : loots.entrySet()) {
			if (this.difficulty.level >= entry.getKey().level) {
				for (LootEntry le : entry.getValue()) {
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
	}
}
