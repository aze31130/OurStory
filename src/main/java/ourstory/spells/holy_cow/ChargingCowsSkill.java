package ourstory.spells.holy_cow;

import java.util.List;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import ourstory.spells.Spell;

public class ChargingCowsSkill extends Spell {

	private Location headLocation;

	public ChargingCowsSkill(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	// private void

	@Override
	public void setup() {
		Entity closest;
		double minDist = Double.MAX_VALUE;
		// Must not be empty
		for (Entity e : targets) {
			var dist = e.getLocation().distance(caster.getLocation());
			if (dist < minDist) {
				closest = e;
				minDist = dist;
			}
		}

		Vector dir = closest.getLocation().subtract(caster.getLocation()).getDirection();
		dir.setY(caster.getY());
		if (Math.abs(dir.getX()) > Math.abs(dir.getZ())) {
			dir.setZ(0);
		} else {
			dir.setX(0);
		}
		this.headLocation = new Location(caster.getWorld(), dir.getX(), dir.getY(), dir.getZ());
	}

	@Override
	public void tick() {
		throw new UnsupportedOperationException("Unimplemented method 'tick'");
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("Unimplemented method 'stop'");
	}

	@Override
	public boolean shouldStop() {
		throw new UnsupportedOperationException("Unimplemented method 'shouldStop'");
	}

}
