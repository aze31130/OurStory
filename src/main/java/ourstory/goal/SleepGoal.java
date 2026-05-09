package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.text.Component;
import ourstory.bosses.IBoss;

public class SleepGoal implements Goal<Mob> {

	public final IBoss boss;

	public SleepGoal(final IBoss boss) {
		this.boss = boss;
	}

	@Override
	public boolean shouldActivate() {
		return boss.getBossEntity().getHealth() > boss.getBossEntity().getAttribute(Attribute.MAX_HEALTH).getBaseValue() / 2.0f;
	}

	@Override
	public void tick() {
		Bukkit.getServer().broadcast(Component.text("[SleepGoal] - zZz...zZz"));
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(
				Mob.class,
				new NamespacedKey("ourstory", "boss_goal_sleep"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.LOOK, GoalType.MOVE);
	}
}
