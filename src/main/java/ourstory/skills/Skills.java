package ourstory.skills;

import java.util.List;
import org.bukkit.entity.Entity;

public interface Skills {
	/*
	 * The caster is the boss who casted the spell. The targets are all the entities targeted by the
	 * spell
	 */
	public void cast(Entity caster, List<Entity> targets);
}
