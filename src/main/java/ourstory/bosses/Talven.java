package ourstory.bosses;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import com.destroystokyo.paper.entity.ai.MobGoals;
import net.kyori.adventure.text.Component;
import ourstory.goal.*;
import ourstory.spells.Annihilation;
import ourstory.spells.ArrowWall;
import ourstory.utils.EnchantItem;

public class Talven extends Boss {
	private final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final NamespacedKey bossKey = new NamespacedKey(plugin, "isBoss");

	private Map<Attribute, Double> attributes = Map.of(
			Attribute.MAX_HEALTH, 20.0,
			Attribute.MOVEMENT_SPEED, 0.2,
			Attribute.ATTACK_DAMAGE, 35.0,
			Attribute.SCALE, 1.4,
			Attribute.KNOCKBACK_RESISTANCE, 1.0);

	ItemStack[] armor = {
			EnchantItem.createEnchantedItem(Material.NETHERITE_BOOTS,
					Map.of(Enchantment.VANISHING_CURSE, 1)),
			EnchantItem.createEnchantedItem(Material.NETHERITE_LEGGINGS,
					Map.of(Enchantment.VANISHING_CURSE, 1)),
			EnchantItem.createEnchantedItem(Material.NETHERITE_CHESTPLATE,
					Map.of(Enchantment.VANISHING_CURSE, 1)),
			EnchantItem.createEnchantedItem(Material.NETHERITE_HELMET, Map.of(Enchantment.VANISHING_CURSE, 1))
	};

	public Talven(Location spawn, List<Entity> targets, int level) {
		super("Talven", targets, level);

		this.entity = (Mob) spawn.getWorld().spawnEntity(spawn, EntityType.EVOKER);

		// Equip entity
		EntityEquipment equipment = entity.getEquipment();
		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(EnchantItem.createEnchantedItem(Material.BREEZE_ROD, Map.of(Enchantment.VANISHING_CURSE, 1)));
		entity.customName(Component.text(this.name));
		entity.setCustomNameVisible(true);
		entity.setAggressive(true);
		entity.setLootTable(Bukkit.getLootTable(NamespacedKey.fromString("ourstory:boss/talven")));

		PersistentDataContainer container = entity.getPersistentDataContainer();
		container.set(bossKey, PersistentDataType.BOOLEAN, true);

		// // Apply attributes modifiers
		for (Map.Entry<Attribute, Double> entry : attributes.entrySet()) {
			AttributeInstance a = entity.getAttribute(entry.getKey());
			a.setBaseValue(entry.getValue());
		}
		entity.setHealth(attributes.get(Attribute.MAX_HEALTH));


		registerGoals(Bukkit.getServer().getMobGoals());
		onSpawn();

		// // Define HealthBar
		// this.healthBar = Bukkit.createBossBar(this.name, BarColor.PURPLE, BarStyle.SOLID);
		// this.healthBar.setVisible(true);

		// double progress = entity.getHealth() /
		// entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		// this.healthBar.setProgress(progress);
		// }
	}

	@Override
	public void registerGoals(MobGoals goals) {
		goals.removeAllGoals(this.entity);
		goals.addGoal(this.entity, 0, new TalvenPhase1(this, Set.of(
				new ArrowWall(entity, targets, 0),
				new Annihilation(entity, targets, 0))));
		goals.addGoal(this.entity, 1, new TalvenPhase2(this, this.spells));
		goals.addGoal(this.entity, 2, new TalvenPhase3(this, this.spells));
	}

	@Override
	public void onSpawn() {
		for (Entity p : this.targets)
			p.sendMessage("You dare challenge me ? Witness power beyond your comprehension !");
	}

	@Override
	public void onHit(EntityDamageByEntityEvent event) {
		for (Entity p : this.targets)
			p.sendMessage("Hit" + this.entity.getHealth() + " " + this.entity.getAttribute(Attribute.MAX_HEALTH).getValue());
	}

	@Override
	public void onDeath(EntityDeathEvent event) {
		for (Entity p : this.targets)
			p.sendMessage("No... Impossible... You can't defeat me");
		Bukkit.broadcast(Component.text(this.name + " has been defeated !"));

		// this.healthBar.removeAll();

		// Death animation
		Location bossDeath = event.getEntity().getLocation();

		for (int i = -10; i < 10; i++)
			event.getEntity().getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, bossDeath.getX() + i, bossDeath.getY(), bossDeath.getZ() + i, 50);
	}

	// public void onSpawn() {

	// World w = this.bossSpawn.getWorld();
	// Location center = this.bossSpawn.clone().add(0, 15, 0);
	// int pointCount = 10;
	// double radius = 20;
	// int totalSteps = 100;
	// double maxY = 7;

	// for (int i = 0; i < pointCount; i++) {
	// double angle = 2 * Math.PI * i / pointCount;
	// double startX = Math.cos(angle) * radius;
	// double startZ = Math.sin(angle) * radius;

	// new BukkitRunnable() {
	// int t = 0;

	// @Override
	// public void run() {
	// if (t > totalSteps) {
	// cancel();
	// return;
	// }

	// double progress = (double) t / totalSteps;
	// double arcY = 4 * maxY * progress * (1 - progress); // parabolic Y

	// // Linearly interpolate from start to center (radial inward)
	// double x = startX * (1 - progress);
	// double z = startZ * (1 - progress);

	// Location particleLoc = center.clone().add(x, arcY, z);
	// DustOptions dustOptions = new DustOptions(Color.fromRGB(19, 29, 79), 15);
	// center.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0, 0, 0, dustOptions);

	// t++;
	// }
	// }.runTaskTimer(p, i * 2L, 2L); // Optional stagger
	// }

	// double radius2 = 1.0;
	// new BukkitRunnable() {
	// double t = 0;

	// @Override
	// public void run() {
	// t += Math.PI / 16;

	// for (double theta = 0; theta < Math.PI; theta += Math.PI / 10) {
	// for (double phi = 0; phi < 2 * Math.PI; phi += Math.PI / 10) {
	// double x = radius2 * Math.sin(theta) * Math.cos(phi);
	// double y = radius2 * Math.cos(theta);
	// double z = radius2 * Math.sin(theta) * Math.sin(phi);

	// Location loc = center.clone().add(x, y, z);
	// w.spawnParticle(Particle.SCULK_SOUL, loc, 0, 0, 0, 0, 0);
	// }
	// }

	// if (t > 2 * Math.PI)
	// this.cancel();
	// }
	// }.runTaskTimer(p, totalSteps * 2, 10);


	// new BukkitRunnable() {
	// int step = 0;
	// int lines = 10;
	// int steps = 100;
	// int maxRadius = 10;

	// // Generate fixed direction vectors for each line
	// final List<Vector> directions = new ArrayList<>();
	// {
	// for (int i = 0; i < lines; i++) {
	// double theta = Math.random() * 2 * Math.PI;
	// double phi = Math.acos(2 * Math.random() - 1);
	// Vector dir = new Vector(
	// Math.sin(phi) * Math.cos(theta),
	// Math.sin(phi) * Math.sin(theta),
	// Math.cos(phi)).normalize();
	// directions.add(dir);
	// }
	// }

	// @Override
	// public void run() {
	// if (step >= steps) {
	// cancel();
	// return;
	// }

	// double currentRadius = maxRadius * (step / (double) steps);

	// for (Vector dir : directions) {
	// Location loc = center.clone().add(dir.clone().multiply(currentRadius));
	// center.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 0, 0, 0, 0, 0);
	// }

	// step++;
	// }
	// }.runTaskTimer(p, totalSteps * 3, 1L);

	// // Start skill thread
	// this.skills.start();
	// }

	// /*
	// * Boss skills. For now, they only trigger randomly after an hit.
	// */
	// @Override
	// public void run() {
	// Random r = new Random();

	// // Waiting time for boss skills
	// try {
	// Thread.sleep(15000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// return;
	// }

	// while (true) {
	// /*
	// * Check if party ran out of time
	// */
	// Date currentDate = new Date();

	// int rng = r.nextInt(100);

	// if (rng < 10) {
	// ArrowWall a = new ArrowWall();
	// a.cast(entity, null, 1);
	// }

	// if (rng < 25) {
	// Summon su = new Summon();
	// su.cast(entity, null, 1);
	// }

	// WitherRage wr = new WitherRage();
	// wr.cast(entity, null, 1);

	// try {
	// Thread.sleep(15000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// return;
	// }
	// }
	// }

	// /*
	// * Method used for phase transitions
	// */
	// public void onHit(EntityDamageByEntityEvent event) {
	// Monster boss = (Monster) event.getEntity();
	// Player p = (Player) event.getDamager();

	// // Play sound effect
	// if (boss.getHealth() > 2000) {
	// p.playSound(boss.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 1);
	// } else {
	// p.playSound(boss.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 0.4f);
	// }

	// // Increase party damage
	// // s.bossInstance.damage.put(p, s.bossInstance.damage.get(p) + event.getDamage());

	// // Add player to bossbar list
	// this.healthBar.addPlayer(p);

	// // Update bossbar
	// double progress = entity.getHealth() /
	// entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
	// this.healthBar.setProgress(progress);

	// // Check phase
	// Double maxHealth = attributes.get(Attribute.MAX_HEALTH);
	// Double currentHealth = boss.getHealth();

	// Double healthPercent = (currentHealth * 100) / maxHealth;

	// if (healthPercent < 55 && phase == 1) {
	// ArrowWall a = new ArrowWall();
	// a.cast(entity, null, 1);
	// phase = 2;
	// }

	// if (healthPercent < 20 && phase == 2) {
	// // Summon special skill
	// phase = 3;
	// }

	// if (healthPercent < 20 && phase == 2) {
	// // Summon special skill
	// Annihilation an = new Annihilation();
	// an.cast(boss, null, 1);

	// phase = 3;
	// }
	// }

	// // for (Map.Entry<Player, Double> entry : s.bossInstance.damage.entrySet())
	// // Bukkit.broadcast(Component.text(entry.getKey().getName() + " dealt " + String.format("%.2f",
	// // entry.getValue()) + " damage"));

	// generateDrops(event, loots);
	// }
}
