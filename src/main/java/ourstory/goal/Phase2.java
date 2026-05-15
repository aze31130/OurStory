package ourstory.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.text.Component;
import ourstory.bosses.Boss;
import ourstory.spells.Spell;

public final class Phase2 implements Goal<Mob> {
	private final Boss boss;
	private Player target;
	private Location lastTargetLoc;

	private final Set<Spell> spells;
	/**
	 * Last time (in server ticks) the behaviour has been triggered
	 */
	private Integer lastTickActivated;

	public Phase2(final Boss boss, final Set<Spell> spells) {
		this.boss = boss;
		this.spells = spells;
	}

	@Override
	public void start() {
		Bukkit.getServer().broadcast(Component.text("[Phase2] - Setup "));
	}

	@Override
	public void tick() {
		Bukkit.getServer().broadcast(Component.text("[Phase2] - Running "));
	}

	@Override
	public void stop() {
		Bukkit.getServer().broadcast(Component.text("[Phase2] - Stop "));
	}

	@Override
	public boolean shouldActivate() {
		return (this.boss.entity.getHealth() / this.boss.entity.getAttribute(Attribute.MAX_HEALTH).getValue()) < 0.5;
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
