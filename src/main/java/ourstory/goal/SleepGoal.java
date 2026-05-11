package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import ourstory.bosses.Boss;

public class SleepGoal implements Goal<Mob> {
	public final Boss boss;

	public SleepGoal(final Boss boss) {
		this.boss = boss;
	}

	@Override
	public boolean shouldActivate() {
		return true;
	}

	@Override
	public void tick() {
		// no-op: boss is "sleeping"
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(Mob.class, new NamespacedKey("ourstory", "boss_goal_sleep"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.LOOK, GoalType.MOVE);
	}
}
