package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.entity.LookAnchor;

public final class ChargeGoal implements Goal<Cow> {

	public final LivingEntity target;
	public final Cow self;


	public ChargeGoal(LivingEntity target, Cow self) {
		this.target = target;
		this.self = self;
	}

	@Override
	public void start() {
		target.sendMessage("[ChargeGoal] - Start");
		self.lookAt(
				target.getEyeLocation().x(),
				target.getEyeLocation().y(),
				target.getEyeLocation().z(),
				LookAnchor.EYES);
	}

	@Override
	public void tick() {
		target.sendMessage("[ChargeGoal] - Tick");
		self.lookAt(target);
		self.getPathfinder().moveTo(target, 2);
	}

	@Override
	public void stop() {
		target.sendMessage("[ChargeGoal] - Stop");
	}

	@Override
	public boolean shouldActivate() {
		/* Si la vache peut voir sa cible */
		return self.hasLineOfSight(target);
	}

	@Override
	public GoalKey<Cow> getKey() {
		return GoalKey.of(
				Cow.class,
				new NamespacedKey("ourstory", "cow_charge_target"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}

}
