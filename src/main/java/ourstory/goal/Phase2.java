package ourstory.goal;

import java.util.EnumSet;
import java.util.Set;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import ourstory.bosses.Boss;
import ourstory.spells.Spell;

public final class Phase2 implements Goal<Mob> {
	private final Boss boss;
	@SuppressWarnings("unused")
	private final Set<Spell> spells;

	public Phase2(final Boss boss, final Set<Spell> spells) {
		this.boss = boss;
		this.spells = spells;
	}

	@Override
	public void start() {
		// no-op
	}

	@Override
	public void tick() {
		// no-op
	}

	@Override
	public void stop() {
		// no-op
	}

	@Override
	public boolean shouldActivate() {
		double max = this.boss.entity.getAttribute(Attribute.MAX_HEALTH).getValue();
		if (max <= 0)
			return false;
		return (this.boss.entity.getHealth() / max) < 0.5;
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(Mob.class, new NamespacedKey("ourstory", "boss_phase2"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.TARGET);
	}
}
