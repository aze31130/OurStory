package ourstory.goal;

import java.util.EnumSet;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.text.Component;
import ourstory.bosses.Boss;
import ourstory.spells.Spell;

public final class Phase1 implements Goal<Mob> {
	private static final Integer COOLDOWN = 20 * 10;
	private static final Integer SCAN_RADIUS = 20;
	private final Boss boss;
	private Player target;
	private Location lastTargetLoc;

	private final List<Spell> spells;
	/**
	 * Last time (in server ticks) the behaviour has been triggered
	 */
	private Integer lastTickActivated;

	public Phase1(final Boss boss, final List<Spell> spells) {
		this.boss = boss;
		this.spells = spells;
	}

	@Override
	public void start() {
		Bukkit.getServer().broadcast(Component.text("[Phase1] - Setup ").append(target.name()));
	}

	@Override
	public void tick() {
		Bukkit.getServer().broadcast(Component.text("[Phase1] - Running ").append(target.name()));
	}

	@Override
	public void stop() {
		Bukkit.getServer().broadcast(Component.text("[Phase1] - Stop ").append(target.name()));
	}

	@Override
	public boolean shouldActivate() {
		return true;
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(Mob.class, new NamespacedKey("ourstory", "boss_goal_charge"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}
}
