package ourstory.goal.holy_cow;

import java.util.EnumSet;
import com.destroystokyo.paper.entity.ai.GoalType;
import ourstory.bosses.HolyCow;
import ourstory.bosses.HolyCow.State;
import ourstory.goal.AbstractBossGoal;

public class HolyCowPhaseOne extends AbstractBossGoal<HolyCow> {

	public HolyCowPhaseOne(HolyCow boss) {
		super(boss);
	}

	@Override
	public boolean shouldActivate() {
		return boss.getState() == State.PHASE_1;
	}

	@Override
	public void start() {}

	@Override
	public void tick() {}

	@Override
	public void stop() {}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}

	@Override
	protected String getPhaseKey() {
		return "holy_cow_phase_one";
	}
}
