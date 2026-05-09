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
	abstract void setup();

	/**
	 * Update du tick
	 */
	abstract void tick();

	/**
	 * Le dernier tick. Si on veut faire un truc à la fin, avant de passer a un autre spell
	 */
	abstract void stop();
}
