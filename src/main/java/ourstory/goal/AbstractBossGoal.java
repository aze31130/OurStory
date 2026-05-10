package ourstory.goal;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import ourstory.bosses.Boss;

public abstract class AbstractBossGoal<T extends Boss> implements Goal<Mob> {
	protected final T boss;

	public AbstractBossGoal(final T boss) {
		this.boss = boss;
	}

	abstract String getPhaseKey();

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(
				Mob.class,
				new NamespacedKey("ourstory", this.getPhaseKey()));
	}
}
