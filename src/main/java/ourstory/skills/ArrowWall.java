package ourstory.skills;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowWall implements Skills {

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		Location bossLocation = caster.getLocation().add(0, 5, 0); // Spawn arrows 5 blocks above boss

		for (int i = 0; i < 800; i++) {
			new BukkitRunnable() {
				@Override
				public void run() {
					// Generate a random direction for each arrow in a spherical pattern
					double theta = Math.random() * 2 * Math.PI; // Angle around the Y-axis
					double phi = Math.random() * Math.PI; // Angle from the Y-axis down



					// Convert spherical coordinates to Cartesian (X, Y, Z)
					double x = Math.sin(phi) * Math.cos(theta);
					double y = Math.cos(phi);
					double z = Math.sin(phi) * Math.sin(theta);
					Vector direction = new Vector(x, y, z).normalize().multiply(4); // Speed of 3 blocks/sec

					// Spawn the arrow at the boss's location with the calculated direction
					Arrow arrow = caster.getWorld().spawnArrow(bossLocation, direction, 2, 0);
					arrow.setLifetimeTicks(1100);
					// arrow.setShooter(caster); // Set the boss as the shooter (optional)
				}
			}.runTaskLater(plugin, i / 20); // Slight delay for each arrow
		}
	}
}
