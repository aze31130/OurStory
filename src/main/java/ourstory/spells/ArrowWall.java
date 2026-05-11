package ourstory.spells;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowWall extends Spell {

	public ArrowWall(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		// not yet implemented; see commented WIP below
	}

	@Override
	public void tick() {
		// not yet implemented
	}

	@Override
	public void stop() {
		// not yet implemented
	}

	@Override
	public boolean shouldStop() {
		return true;
	}

	// @Override
	// public void cast(Entity caster, List<Entity> targets, int level) {
	// Location bossLocation = caster.getLocation().add(0, 4, 0);
	// Entity target = targets.get(new Random().nextInt(targets.size()));

	// for (int i = 0; i < 80; i++) {
	// new BukkitRunnable() {
	// @Override
	// public void run() {
	// Vector direction = new Vector(
	// target.getX() - bossLocation.getX(),
	// target.getY() - bossLocation.getY(),
	// target.getZ() - bossLocation.getZ());

	// // Spawn the arrow at the boss's location with the calculated direction
	// Arrow arrow = caster.getWorld().spawnArrow(bossLocation, direction, 2, 6);
	// arrow.setLifetimeTicks(1100);
	// arrow.setCritical(true);
	// arrow.setPierceLevel(4);
	// arrow.setGravity(false);
	// }
	// }.runTaskLater(plugin, i / 20); // Slight delay for each arrow
	// }
	// }
}
