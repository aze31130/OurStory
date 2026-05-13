package ourstory.bosses;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import com.destroystokyo.paper.entity.ai.MobGoals;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import ourstory.goal.DivineDescentPhase;
import ourstory.goal.NoopGoal;

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

	public HolyCow(List<Player> targets, int level) {
		super("Holy Cow", targets, level);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Register different behaviours of the boss. These can be seen as phases. Each phase defines a set
	 * of spells that the boss can use.
	 */
	public void registerGoals(MobGoals goals) {
		goals.removeAllGoals(this.entity);
		goals.addGoal(this.entity, 0, new DivineDescentPhase(this));
		goals.addGoal(this.entity, 1, new NoopGoal<HolyCow>(this));
		// goals.addGoal(this.entity, 1, new ChargeClosestGoal(this)); // Phase 2
	}

	@Override
	public void spawn(World world, Location location) {
		super.spawn(world, location.add(0.0D, 10.0D, 0.0D));
		targets.forEach(p -> p.sendMessage("[HolyCow] onSpawn()"));
		this.state = State.DESCENDING;
	}

	@Override
	public void onHit() {
		targets.forEach(p -> p.sendMessage("[HolyCow] onHit()"));
	}

	@Override
	public void onDeath() {
		targets.forEach(p -> p.sendMessage("[HolyCow] onDeath()"));
	}

	public static enum State {
		DESCENDING, SLEEPING
	}

	@Override
	protected Class<? extends Mob> getMobType() {
		return Cow.class;
	}
}
