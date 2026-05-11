package ourstory.goal;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import com.google.common.collect.ImmutableSet;
import io.papermc.paper.entity.LookAnchor;
import ourstory.bosses.Boss;
import ourstory.spells.Spell;

public final class ChargeClosestGoal implements Goal<Mob> {
	private static final int COOLDOWN_TICKS = 20 * 10;
	private static final int SCAN_RADIUS = 20;

	private final Boss boss;
	private final ImmutableSet<Spell> spells;

	private Player target;
	private Location lastTargetLoc;
	private Location originalLoc;
	private int lastTickActivated = Integer.MIN_VALUE / 2;

	public ChargeClosestGoal(final Boss boss, final ImmutableSet<Spell> spells) {
		this.boss = boss;
		this.spells = spells;
	}

	private Optional<Player> getClosest(Collection<Player> players) {
		double minDistance = Double.MAX_VALUE;
		Player closest = null;
		Location bossLoc = boss.entity.getLocation();
		for (Player p : players) {
			double d = p.getLocation().distance(bossLoc);
			if (d < minDistance) {
				minDistance = d;
				closest = p;
			}
		}
		return Optional.ofNullable(closest);
	}

	@Override
	public void start() {
		if (target == null)
			return;
		boss.entity.lookAt(
				target.getEyeLocation().x(),
				target.getEyeLocation().y(),
				target.getEyeLocation().z(),
				LookAnchor.EYES);
		lastTargetLoc = target.getLocation();
		originalLoc = boss.entity.getLocation();
		lastTickActivated = Bukkit.getCurrentTick();
	}

	@Override
	public void tick() {
		if (lastTargetLoc == null)
			return;
		boss.entity.lookAt(lastTargetLoc);
		boss.entity.getPathfinder().moveTo(lastTargetLoc, 2);
		boss.entity.getWorld().spawnParticle(Particle.ANGRY_VILLAGER,
				boss.entity.getLocation(), 10);
	}

	@Override
	public void stop() {
		if (target == null)
			return;
		if (target.getLocation().distance(boss.entity.getLocation()) < 5.0D) {
			Vector delta = new Vector(target.getX(), target.getY(), target.getZ())
					.subtract(new Vector(boss.entity.getX(), boss.entity.getY(), boss.entity.getZ()));
			target.knockback(2, -delta.getX(), -delta.getZ());
		}
		lastTickActivated = Bukkit.getCurrentTick();
	}

	@Override
	public boolean shouldActivate() {
		Collection<Player> nearbyPlayers = boss.entity.getLocation().getNearbyPlayers(SCAN_RADIUS, boss.targets::contains);
		Optional<Player> closest = getClosest(nearbyPlayers);
		int currentTick = Bukkit.getCurrentTick();
		if (closest.isPresent() && currentTick - lastTickActivated > COOLDOWN_TICKS) {
			this.target = closest.get();
			this.lastTickActivated = currentTick;
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldStayActive() {
		return lastTargetLoc != null
				&& boss.entity.getLocation().distance(lastTargetLoc) > 2.0D;
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
