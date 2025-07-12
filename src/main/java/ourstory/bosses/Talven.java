package ourstory.bosses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;
import ourstory.skills.*;
import ourstory.utils.EnchantItem;

public class Talven extends Boss implements Runnable {

	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	private Map<Attribute, Double> attributes = Map.of(
			Attribute.MAX_HEALTH, 500.0,
			Attribute.MOVEMENT_SPEED, 0.2,
			Attribute.ATTACK_DAMAGE, 35.0,
			Attribute.SCALE, 1.5,
			Attribute.KNOCKBACK_RESISTANCE, 1.0);

	private List<LootEntry> loots = List.of(
			new LootEntry(new ItemStack(Material.GOLDEN_CARROT), 15, 80.0),
			new LootEntry(new ItemStack(Material.GOLDEN_APPLE), 5, 80.0),
			new LootEntry(new ItemStack(Material.DIAMOND), 20, 75.0),
			new LootEntry(new ItemStack(Material.NETHERITE_INGOT), 3, 40.0),
			new LootEntry(new ItemStack(Material.TOTEM_OF_UNDYING), 3, 40.0),
			new LootEntry(new ItemStack(Material.NETHER_STAR), 2, 40.0),
			new LootEntry(new ItemStack(Material.SPAWNER), 1, 35.0),
			new LootEntry(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 5, 30.0),
			new LootEntry(new ItemStack(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE), 1, 2.0));

	public Thread skills = new Thread(this);

	public Talven(Location l) {
		super("Talven", l, new ArrayList<>());

		this.entity = (Monster) l.getWorld().spawnEntity(l, EntityType.EVOKER);

		EntityEquipment equipment = entity.getEquipment();
		ItemStack[] armor = {
				EnchantItem.createEnchantedItem(Material.NETHERITE_BOOTS, Map.of(Enchantment.VANISHING_CURSE, 1)),
				EnchantItem.createEnchantedItem(Material.NETHERITE_LEGGINGS, Map.of(Enchantment.VANISHING_CURSE, 1)),
				EnchantItem.createEnchantedItem(Material.NETHERITE_CHESTPLATE, Map.of(Enchantment.VANISHING_CURSE, 1)),
				EnchantItem.createEnchantedItem(Material.NETHERITE_HELMET, Map.of(Enchantment.VANISHING_CURSE, 1))
		};

		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(EnchantItem.createEnchantedItem(Material.BREEZE_ROD, Map.of(Enchantment.VANISHING_CURSE, 1)));
		entity.customName(Component.text(this.name));
		entity.setCustomNameVisible(true);

		entity.setMetadata("isBoss", new FixedMetadataValue(p, true));
		entity.setAggressive(true);

		// Apply attributes modifiers
		for (Map.Entry<Attribute, Double> entry : attributes.entrySet()) {
			AttributeInstance a = entity.getAttribute(entry.getKey());
			a.setBaseValue(entry.getValue());
		}

		// Set entity to max life
		entity.setHealth(attributes.get(Attribute.MAX_HEALTH));

		// Define HealthBar
		this.healthBar = Bukkit.createBossBar(this.name, BarColor.PURPLE, BarStyle.SOLID);
		this.healthBar.setVisible(true);

		double progress = entity.getHealth() / entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		this.healthBar.setProgress(progress);
	}

	public void onSpawn() {
		for (Player p : this.targets)
			p.sendMessage("You dare challenge me ? Witness power beyond your comprehension !");

		World w = this.bossSpawn.getWorld();
		Location center = this.bossSpawn.clone().add(0, 15, 0);
		int pointCount = 10;
		double radius = 20;
		int totalSteps = 100;
		double maxY = 7;

		for (int i = 0; i < pointCount; i++) {
			double angle = 2 * Math.PI * i / pointCount;
			double startX = Math.cos(angle) * radius;
			double startZ = Math.sin(angle) * radius;

			new BukkitRunnable() {
				int t = 0;

				@Override
				public void run() {
					if (t > totalSteps) {
						cancel();
						return;
					}

					double progress = (double) t / totalSteps;
					double arcY = 4 * maxY * progress * (1 - progress); // parabolic Y

					// Linearly interpolate from start to center (radial inward)
					double x = startX * (1 - progress);
					double z = startZ * (1 - progress);

					Location particleLoc = center.clone().add(x, arcY, z);
					DustOptions dustOptions = new DustOptions(Color.fromRGB(19, 29, 79), 15);
					center.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0, 0, 0, dustOptions);

					t++;
				}
			}.runTaskTimer(p, i * 2L, 2L); // Optional stagger
		}

		double radius2 = 1.0;
		new BukkitRunnable() {
			double t = 0;

			@Override
			public void run() {
				t += Math.PI / 16;

				for (double theta = 0; theta < Math.PI; theta += Math.PI / 10) {
					for (double phi = 0; phi < 2 * Math.PI; phi += Math.PI / 10) {
						double x = radius2 * Math.sin(theta) * Math.cos(phi);
						double y = radius2 * Math.cos(theta);
						double z = radius2 * Math.sin(theta) * Math.sin(phi);

						Location loc = center.clone().add(x, y, z);
						w.spawnParticle(Particle.SCULK_SOUL, loc, 0, 0, 0, 0, 0);
					}
				}

				if (t > 2 * Math.PI)
					this.cancel();
			}
		}.runTaskTimer(p, totalSteps * 2, 10);


		new BukkitRunnable() {
			int step = 0;
			int lines = 10;
			int steps = 100;
			int maxRadius = 10;

			// Generate fixed direction vectors for each line
			final List<Vector> directions = new ArrayList<>();
			{
				for (int i = 0; i < lines; i++) {
					double theta = Math.random() * 2 * Math.PI;
					double phi = Math.acos(2 * Math.random() - 1);
					Vector dir = new Vector(
							Math.sin(phi) * Math.cos(theta),
							Math.sin(phi) * Math.sin(theta),
							Math.cos(phi)).normalize();
					directions.add(dir);
				}
			}

			@Override
			public void run() {
				if (step >= steps) {
					cancel();
					return;
				}

				double currentRadius = maxRadius * (step / (double) steps);

				for (Vector dir : directions) {
					Location loc = center.clone().add(dir.clone().multiply(currentRadius));
					center.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 0, 0, 0, 0, 0);
				}

				step++;
			}
		}.runTaskTimer(p, totalSteps * 3, 1L);

		// Start skill thread
		this.skills.start();
	}

	/*
	 * Boss skills. For now, they only trigger randomly after an hit.
	 */
	@Override
	public void run() {
		Random r = new Random();

		// Waiting time for boss skills
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			/*
			 * Check if party ran out of time
			 */
			Date currentDate = new Date();

			int rng = r.nextInt(100);

			if (rng < 10) {
				ArrowWall a = new ArrowWall();
				a.cast(entity, null);
			}

			if (rng < 25) {
				Summon su = new Summon();
				su.cast(entity, null);
			}

			WitherRage wr = new WitherRage();
			wr.cast(entity, null);

			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/*
	 * Method used for phase transitions
	 */
	public void onHit(EntityDamageByEntityEvent event) {
		Monster boss = (Monster) event.getEntity();
		Player p = (Player) event.getDamager();

		// Play sound effect
		if (boss.getHealth() > 2000) {
			p.playSound(boss.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 1);
		} else {
			p.playSound(boss.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 0.4f);
		}

		// Increase party damage
		// s.bossInstance.damage.put(p, s.bossInstance.damage.get(p) + event.getDamage());

		// Add player to bossbar list
		this.healthBar.addPlayer(p);

		// Update bossbar
		double progress = entity.getHealth() / entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		this.healthBar.setProgress(progress);

		// Check phase
		Double maxHealth = attributes.get(Attribute.MAX_HEALTH);
		Double currentHealth = boss.getHealth();

		Double healthPercent = (currentHealth * 100) / maxHealth;

		if (healthPercent < 55 && phase == 1) {
			ArrowWall a = new ArrowWall();
			a.cast(entity, null);
			phase = 2;
		}

		if (healthPercent < 20 && phase == 2) {
			// Summon special skill
			phase = 3;
		}

		if (healthPercent < 20 && phase == 2) {
			// Summon special skill
			Annihilation an = new Annihilation();
			an.cast(boss, null);

			phase = 3;
		}
	}


	public void onDeath(EntityDeathEvent event) {
		for (Player p : this.targets)
			p.sendMessage("No... Impossible... You can't defeat me");
		Bukkit.broadcast(Component.text(this.name + " has been defeated !"));

		// Stop skill loop and removes bossbar
		this.skills.interrupt();
		this.healthBar.removeAll();

		Player killer = event.getEntity().getKiller();

		// Death animation
		Location bossDeath = event.getEntity().getLocation();
		for (int i = -5; i < 5; i++) {
			event.getEntity().getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, bossDeath.getX() + i, bossDeath.getY(), bossDeath.getZ() + i, 50);

		}

		// for (Map.Entry<Player, Double> entry : s.bossInstance.damage.entrySet())
		// Bukkit.broadcast(Component.text(entry.getKey().getName() + " dealt " + String.format("%.2f",
		// entry.getValue()) + " damage"));

		generateDrops(event, loots);
	}
}
