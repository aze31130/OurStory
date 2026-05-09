package ourstory.bosses;

import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableList;
import ourstory.goal.ChargeClosestGoal;
import ourstory.goal.SleepGoal;

public class HolyCow implements IBoss {
	private final Mob mob;
	private final ImmutableList<Player> engagedPlayers;

	/**
	 * 
	 * @param mob : Entity attached to the boss
	 * @param engaged : The players involved in the fight.
	 */
	public HolyCow(final Mob mob, final ImmutableList<Player> engagedPlayers) {
		this.mob = mob;
		this.engagedPlayers = engagedPlayers;
	}

	/**
	 * Register different behaviours of the boss. These can be seen as phases. Each phase defines a set
	 * of spells that the boss can use.
	 */
	public void registerGoals() {
		var goals = Bukkit.getServer().getMobGoals();
		goals.removeAllGoals(mob);
		goals.addGoal(mob, 0, new SleepGoal(mob));
		goals.addGoal(mob, 1, new ChargeClosestGoal(this));
	}

	public void spawn() {
		this.registerAttributes();
	}

	@Override
	public ImmutableList<Player> getEngagedPlayers() {
		return engagedPlayers;
	}

	@Override
	public Mob getBossEntity() {
		return mob;
	}
}
