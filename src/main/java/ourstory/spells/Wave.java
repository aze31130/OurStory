package ourstory.spells;

import java.util.List;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Wave extends Spell {

	public Wave(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	void setup() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setup'");
	}

	@Override
	void tick() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'tick'");
	}

	@Override
	void stop() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'stop'");
	}

	@Override
	boolean shouldStop() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shouldStop'");
	}

	// @Override
	// public void cast(Entity caster, List<Entity> targets, int level) {
	// for (Entity entity : targets) {
	// double x = entity.getLocation().getX() - caster.getLocation().getX();
	// double y = entity.getLocation().getY() - caster.getLocation().getY();
	// double z = entity.getLocation().getZ() - caster.getLocation().getZ();

	// entity.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, entity.getLocation(), 1, 0, 0, 0, 0);
	// entity.setVelocity(new Vector(x, y, z).multiply(2));
	// }
	// }
}
