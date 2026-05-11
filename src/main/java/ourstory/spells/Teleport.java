package ourstory.spells;

import java.util.List;
import org.bukkit.entity.Entity;

public class Teleport extends Spell {

	public Teleport(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		// not yet implemented
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
}
