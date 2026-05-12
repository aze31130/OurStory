package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.spells.Spell;

// Pour pouvoir faire ça (listener d'events), il paut nécessairement
// spawnEntity APRES avoir créé l'instance de Boss.
public abstract class Boss implements Listener {
	// Entity that reprensent the boss
	public Mob entity;
	public List<Player> targets;
	public final String name;
	public final int level;
	public BossBar healthBar;

	public Set<Spell> spells = new HashSet<>();

	public Boss(String name, List<Player> targets, int level) {
		this.name = name;
		this.level = level;
		this.targets = targets;
	}

	/**
	 * Enregistre les comportements (phases) du boss.
	 */
	public abstract void registerGoals(final MobGoals goals);

	protected abstract void onHit();

	protected abstract void onDeath();

	protected abstract Class<? extends Mob> getMobType();

	/**
	 * Créé l'entité attachée au Boss dans le monde.
	 */
	public void spawn(World world, Location location) {
		this.entity = world.spawn(location, this.getMobType());
		this.registerGoals(Bukkit.getServer().getMobGoals());
	}

	@EventHandler
	public void onBossHit(final EntityDamageByEntityEvent event) {
		if (!event.getEntity().getUniqueId().equals(this.entity.getUniqueId())) {
			return;
		}
		this.onHit();
	}

	@EventHandler
	public void onBossDeath(final EntityDeathEvent event) {
		if (!event.getEntity().getUniqueId().equals(this.entity.getUniqueId())) {
			return;
		}
		this.onDeath();
		HandlerList.unregisterAll(this);
	}

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
