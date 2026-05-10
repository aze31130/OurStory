package ourstory.spells;

import java.util.List;
import org.bukkit.entity.Entity;

public abstract class Spell {
	/**
	 * L'entité à l'origine du spell.
	 */
	protected Entity caster;

	/**
	 * Les entités pouvant être ciblés par le spell.
	 */
	protected List<Entity> targets;

	/**
	 * Multiplicateur de difficulté du spell
	 */
	int level;

	public Spell(Entity caster, List<Entity> targets, int level) {
		this.caster = caster;
		this.targets = targets;
		this.level = level;
	}

	/**
	 * Récupère le contexte pour exécuter le spell
	 */
	public abstract void setup();

	/**
	 * Update du tick
	 */
	public abstract void tick();

	/**
	 * Le dernier tick. Si on veut faire un truc à la fin, avant de passer a un autre spell
	 */
	public abstract void stop();

	/**
	 * Returns true
	 */
	public abstract boolean shouldStop();
	/*
	 * The caster is the boss who casted the spell. The targets are all the entities targeted by the
	 * spell
	 * 
	 * Sound example minecraft:entity.dragon_fireball.explode minecraft:entity.blaze.ambient
	 * minecraft:entity.wither.death minecraft:entity.wither.shoot minecraft:entity.wither.spawn
	 * minecraft:entity.experience_orb.pickup
	 */
}
