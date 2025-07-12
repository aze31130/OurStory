package ourstory.bosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.skills.Skills;

public abstract class Boss {
	public Thread skills;
	public Monster entity;
	public List<Player> targets;
	public Location bossSpawn;

	public String name;
	public int phase;
	public BossBar healthBar;

	public List<Skills> capabilites = new ArrayList<>();

	public Boss(String name, Location spawnLocation, List<Player> targets) {
		this.name = name;
		this.bossSpawn = spawnLocation;
		this.targets = targets;
		this.phase = 1;
	}

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
