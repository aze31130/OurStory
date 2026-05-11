package ourstory.bosses;

import java.util.List;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import com.destroystokyo.paper.entity.ai.MobGoals;
import ourstory.goal.SleepGoal;

public class HolyCow extends Boss {
	public HolyCow(Mob mob, List<Player> engagedPlayers, int level) {
		super("Holy Cow", mob, engagedPlayers, level);
	}

	@Override
	public void registerGoals(MobGoals goals) {
		goals.removeAllGoals(this.entity);
		goals.addGoal(this.entity, 0, new SleepGoal(this));
	}

	@Override
	public void onSpawn() {
		// no-op: spawn behaviour not yet implemented
	}

	@Override
	public void onHit(EntityDamageByEntityEvent event) {
		// no-op: hit reaction not yet implemented
	}

	@Override
	public void onDeath(EntityDeathEvent event) {
		// no-op: death sequence not yet implemented
	}
}
