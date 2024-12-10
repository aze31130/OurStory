package ourstory.skills;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

/**
 *
 * @author aurel
 */
public class ArcanicShield implements Skills {
	final double preShieldTimer = 40;
	final double shieldTimer = 100;
	final int preShieldParticleCount = 20;
	final int particleCount = 70;

	/*
	 * 1 seconde par dégats, et chaque 20, le temps est baissé de 20 sec, mais l'effet passe au niveau
	 * supérieur
	 */
	final int damageThresholdPerStrengthLevel = 20;
	final int strengthTimerBase = 200;


	@Override
	public void cast(Entity caster, List<Entity> targets) {

		new BukkitRunnable() {
			boolean isListenerRegistered = false;
			float takenDamage = 0;
			double currentTimer = 0;

			BoundingBox bb = caster.getBoundingBox();
			double sphereRadius = (Math.sqrt((Math.pow(bb.getWidthX(), 2)) + (Math.pow(bb.getWidthZ(), 2)) + (Math.pow(bb.getHeight(), 2)))) / 2;

			final Listener damageListener = new Listener() {
				@EventHandler
				public void onEntityDamage(EntityDamageEvent event) {
					if (event.getEntity().equals(caster)) {
						double damage = event.getFinalDamage();
						takenDamage += damage;

						// Cancel damage
						event.setCancelled(true);
					}
				}
			};

			@Override
			public void run() {
				currentTimer++;
				if (currentTimer > (shieldTimer + preShieldTimer)) {
					((LivingEntity) caster).addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, (int) (20 * (takenDamage % damageThresholdPerStrengthLevel)) + strengthTimerBase,
							Math.round(takenDamage / damageThresholdPerStrengthLevel), false, true, true));
					HandlerList.unregisterAll(damageListener);
					isListenerRegistered = false; // Probably useless, but i've still put it here, just in case
					this.cancel();
					return;
				}
				if (currentTimer < preShieldTimer) {
					create_sphere(new Location(caster.getWorld(), caster.getBoundingBox().getCenterX(), caster.getBoundingBox().getCenterY(), caster.getBoundingBox().getCenterZ()), sphereRadius,
							Particle.NAUTILUS, preShieldParticleCount, Optional.empty());
				} else {
					if (!isListenerRegistered) {
						Bukkit.getPluginManager().registerEvents(damageListener, plugin);
						isListenerRegistered = true;
					}
					create_sphere(new Location(caster.getWorld(), caster.getBoundingBox().getCenterX(), caster.getBoundingBox().getCenterY(), caster.getBoundingBox().getCenterZ()), sphereRadius,
							Particle.SCULK_CHARGE, particleCount, Optional.of((float) 0.0f));
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public void create_sphere(Location center, double radius, Particle part, int count, Optional<Float> data) {
		for (int i = 0; i < count; i++) {
			double phi = Math.acos(1 - 2 * Math.random());
			double theta = 2 * Math.PI * Math.random();

			double x = radius * Math.sin(phi) * Math.cos(theta);
			double y = radius * Math.sin(phi) * Math.sin(theta);
			double z = radius * Math.cos(phi);

			if (data.isPresent())
				center.getWorld().spawnParticle(part, center.clone().add(x, y, z), 1, 0, 0, 0, 0, data.get());
			else
				center.getWorld().spawnParticle(part, center.clone().add(x, y, z), 1, 0, 0, 0, 0);
		}
	}
}
