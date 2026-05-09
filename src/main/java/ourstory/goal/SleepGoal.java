package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

public class SleepGoal implements Goal<Cow> {

	public final LivingEntity target;
	public final Cow self;

	public SleepGoal(final LivingEntity target, final Cow self) {
		this.target = target;
		this.self = self;
	}

	@Override
	public boolean shouldActivate() {
		return self.getHealth() < self.getAttribute(Attribute.MAX_HEALTH).getBaseValue() / 2.0f;
	}

	@Override
	public void tick() {
		target.sendMessage("[SleepGoal] - Tick");
		self.heal(0.1);
	}

	@Override
	public GoalKey<Cow> getKey() {
		return GoalKey.of(
				Cow.class,
				new NamespacedKey("ourstory", "cow_sleep"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.LOOK, GoalType.MOVE);
	}

}
