package ourstory.spells;

import java.util.List;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Wave extends Spell {

	public Wave(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	@Override
	public void setup() {}

	@Override
	public void tick() {}

	@Override
	public void stop() {
		for (Entity entity : targets) {
			double x = entity.getLocation().getX() - caster.getLocation().getX();
			double y = entity.getLocation().getY() - caster.getLocation().getY();
			double z = entity.getLocation().getZ() - caster.getLocation().getZ();

			entity.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, entity.getLocation(), 1, 0, 0, 0, 0);
			entity.setVelocity(new Vector(x, y, z).multiply(level + 1));
		}
	}

	@Override
	public boolean shouldStop() {
		return true;
	}
}
