package ourstory.spells;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

public class WitherRage extends Spell {
	public WitherRage(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	private Entity target;
	private int cpt, stop;

	@Override
	public void setup() {
		this.target = targets.get(0);
		this.cpt = 0;
		this.stop = cpt + 5 * level;
		target.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, target.getLocation(), 5);
	}

	@Override
	public void tick() {
		Location bossLocation = caster.getLocation();
		Location playerLocation = target.getLocation();

		Vector direction = playerLocation.toVector().subtract(bossLocation.toVector()).normalize();

		// Spawn the WitherSkull at the boss's location
		WitherSkull skull = (WitherSkull) caster.getWorld().spawnEntity(caster.getLocation().add(0, 1, 0), EntityType.WITHER_SKULL);

		// Set the direction of the WitherSkull to point towards the player
		skull.setDirection(direction);
		skull.setAcceleration(direction.multiply(0.1));
		cpt++;

	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return cpt >= stop;
	}

	// @Override
	// public void cast(Entity caster, List<Entity> targets, int level) {
	// for (int i = 0; i < 15; i++) {
	// new BukkitRunnable() {
	// @Override
	// public void run() {
	// }
	// }.runTaskLater(plugin, 20 + i * 4);
	// }
	// }
}
