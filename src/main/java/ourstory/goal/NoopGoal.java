package ourstory.goal;

import java.util.EnumSet;
import com.destroystokyo.paper.entity.ai.GoalType;
import ourstory.bosses.Boss;

public class NoopGoal<T extends Boss> extends AbstractBossGoal<T> {

	public NoopGoal(T boss) {
		super(boss);
	}

	@Override
	public boolean shouldActivate() {
		return true;
	}

	@Override
	public void start() {
		boss.targets.forEach(p -> p.sendMessage("[NoopGoal] start()"));
	}

	@Override
	public void tick() {}

	@Override
	public void stop() {
		boss.targets.forEach(p -> p.sendMessage("[NoopGoal] stop()"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}

	@Override
	String getPhaseKey() {
		return "boss_sleep";
	}
}
