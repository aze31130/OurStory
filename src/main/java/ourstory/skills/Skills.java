package ourstory.skills;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public interface Skills {

	public final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");

	/*
	 * The caster is the boss who casted the spell. The targets are all the entities targeted by the
	 * spell
	 */
	public void cast(Entity caster, List<Entity> targets);
	/*
	 * Sound example minecraft:entity.dragon_fireball.explode minecraft:entity.blaze.ambient
	 * minecraft:entity.wither.death minecraft:entity.wither.shoot minecraft:entity.wither.spawn
	 * minecraft:entity.experience_orb.pickup
	 */
}
