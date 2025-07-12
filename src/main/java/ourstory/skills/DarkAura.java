package ourstory.skills;

import java.util.List;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class DarkAura implements Skills {

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		spawnDarkFaceEffect(caster.getLocation());
	}

	public void spawnDarkFaceEffect(Location loc) {
		World world = loc.getWorld();

		// 1. Ring of Flame
		for (double t = 0; t < 2 * Math.PI; t += Math.PI / 16) {
			double x = Math.cos(t) * 1.5;
			double z = Math.sin(t) * 1.5;
			Location particleLoc = loc.clone().add(x, 0, z);
			world.spawnParticle(Particle.LARGE_SMOKE, particleLoc, 1, 0, 0.1, 0, 0);
			world.spawnParticle(Particle.SOUL_FIRE_FLAME, particleLoc, 1, 0, 0.1, 0, 0);
		}

		// 2. Face (simplified pixel pattern)
		double spacing = 0.8;
		String[] face = {
				"  xx  xx  ",
				"          ",
				"   xxxx   ",
				"  xxxxxx  ",
				"   xxxx   "
		};

		for (int y = 0; y < face.length; y++) {
			String row = face[y];
			for (int x = 0; x < row.length(); x++) {
				if (row.charAt(x) == 'x') {
					Location pixelLoc = loc.clone().add((x - 5) * spacing, (face.length - y) * spacing, 0);
					world.spawnParticle(Particle.DUST, pixelLoc, 1, new Particle.DustOptions(Color.fromRGB(100, 0, 0), 1));
				}
			}
		}

		world.spawnParticle(Particle.WITCH, loc, 20, 0.1, 0.2, 0.1, 0.01);
	}
}
