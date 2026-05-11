package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.Main;
import ourstory.spells.Spell;

public abstract class Boss implements Listener {
	// Entity that reprensent the boss
	public Mob entity;
	public List<Player> targets;
	public String name;
	public int level;
	public BossBar healthBar;

	public Set<Spell> spells = new HashSet<>();

	private final NamespacedKey attachedEntityUUIDKey;

	public Boss(String name, Mob mob, List<Player> targets, int level) {
		this.name = name;
		this.entity = mob;
		this.targets = targets;
		this.level = level;
		this.attachedEntityUUIDKey = NamespacedKey.fromString("bossUUID",
				Bukkit.getPluginManager().getPlugin(Main.namespace));
		mob.getPersistentDataContainer().set(attachedEntityUUIDKey, PersistentDataType.STRING, mob.getUniqueId().toString());
	}

	/**
	 * Enregistre / Supprime des comportements (goals) du boss.
	 */
	public abstract void registerGoals(final MobGoals goals);

	@EventHandler
	public void onSpawn(EntitySpawnEvent event) {
		String uuidstr = event.getEntity().getPersistentDataContainer().get(attachedEntityUUIDKey, PersistentDataType.STRING);
		UUID uuid = UUID.fromString(uuidstr);
		if (uuid != entity.getUniqueId()) {
			return;
		}
		onSpawn();
	}

	/**
	 * |!| THIS METHOD IS CALLED BEFORE `registerGoals(...)`
	 */
	protected abstract void onSpawn();

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		String uuidstr = event.getEntity().getPersistentDataContainer().get(attachedEntityUUIDKey, PersistentDataType.STRING);
		UUID uuid = UUID.fromString(uuidstr);
		if (uuid != entity.getUniqueId()) {
			return;
		}
		onHit();
	}

	protected abstract void onHit();


	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		String uuidstr = event.getEntity().getPersistentDataContainer().get(attachedEntityUUIDKey, PersistentDataType.STRING);
		UUID uuid = UUID.fromString(uuidstr);
		if (uuid != entity.getUniqueId()) {
			return;
		}
		onDeath();
	}

	protected abstract void onDeath();


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
