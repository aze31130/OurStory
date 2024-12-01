package ourstory.skills;

import java.util.List;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Wave implements Skills {

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		for (Entity entity : targets) {
			double x = entity.getLocation().getX() - caster.getLocation().getX();
			double y = entity.getLocation().getY() - caster.getLocation().getY();
			double z = entity.getLocation().getZ() - caster.getLocation().getZ();

			entity.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, entity.getLocation(), 1, 0, 0, 0, 0);
			entity.setVelocity(new Vector(x, y, z).multiply(2));
		}
	}
}
