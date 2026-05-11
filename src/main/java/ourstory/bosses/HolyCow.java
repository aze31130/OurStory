package ourstory.bosses;

import java.util.List;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.goal.DivineDescentPhase;

/**
 * - Invocation => Buter X vaches (en mode punition divine)
 * 
 * 1. Elle descend du ciel avec un halo de lumière autour de sa tête
 * 
 * 2. Un meuh qui stun (Spell)
 * 
 * 3. Lancé de seau de laits (Spell)
 * 
 * 4. Charge les joueurs
 */
public class HolyCow extends Boss {
	private State state;

	public HolyCow(Mob mob, List<Player> engagedPlayers, int level) {
		super("Holy Cow", mob, engagedPlayers, level);
	}

	public State getState() {
		return state;
	}

	/**
	 * Register different behaviours of the boss. These can be seen as phases. Each phase defines a set
	 * of spells that the boss can use.
	 */
	public void registerGoals(MobGoals goals) {
		goals.removeAllGoals(this.entity);
		goals.addGoal(this.entity, 0, new DivineDescentPhase(this));
		// goals.addGoal(this.entity, 1, new ChargeClosestGoal(this)); // Phase 2
	}

	// @Override
	// public Set<Spell> getSpells(int phase) {
	// Set<Spell> set = new HashSet<>();
	// // La l'idée c'est de choisir en fonction de la phase les spells qu'on veut.
	// if (phase == 0) {
	// set.add(new Annihilation(mob, engagedPlayers, level));
	// }
	// return set;
	// }

	@Override
	protected void onSpawn() {
		this.state = State.DESCENDING;
	}

	@Override
	protected void onHit() {}

	@Override
	protected void onDeath() {}


	public static enum State {
		DESCENDING, INVINCIBLE, DEFAULT
	}
}
