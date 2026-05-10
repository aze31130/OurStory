package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.text.Component;
import ourstory.bosses.HolyCow;

/**
 * Première phase de la Sainte Vache. Elle descend des cieux pour venger ses camarades abattus de
 * sang froid par les joueurs.
 */
public class DivineDescentPhase extends AbstractBossGoal<HolyCow> {
	public DivineDescentPhase(final HolyCow boss) {
		super(boss);
	}

	@Override
	public boolean shouldActivate() {
		return boss.getState() == HolyCow.State.DESCENDING;
	}

	@Override
	public boolean shouldStayActive() {
		return boss.st
	}

	@Override
	public void start() {
		boss.entity.teleport(boss.entity.getLocation().add(0, 100, 0));
	}

	@Override
	public void tick() {
		Bukkit.getServer().broadcast(Component.text("[SleepGoal] - zZz...zZz"));
	}

	@Override
	public void stop() {

	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.LOOK, GoalType.MOVE);
	}

	@Override
	String getPhaseKey() {
		return "holy_cow_descent";
	}
}
