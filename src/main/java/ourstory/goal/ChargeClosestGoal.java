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
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.entity.LookAnchor;
import net.kyori.adventure.text.Component;
import ourstory.bosses.IBoss;

public final class ChargeClosestGoal implements Goal<Mob> {
	private static final Integer COOLDOWN = 20 * 10;
	private static final Integer SCAN_RADIUS = 20;
	private final IBoss boss;
	private Player target;
	private Location lastTargetLoc;
	private Location originalLoc;

	/**
	 * Last time (in server ticks) the behaviour has been triggered
	 */
	private Integer lastTickActivated;

	public ChargeClosestGoal(final IBoss boss) {
		this.boss = boss;
		this.lastTickActivated = Bukkit.getCurrentTick();
	}

	private Optional<Player> getClosest(Collection<Player> players) {
		double mindist = Integer.MAX_VALUE;
		Player player = null;
		for (var p : players) {
			if (p.getLocation().distance(boss.getBossEntity().getLocation()) < mindist) {
				player = p;
			}
		}
		return Optional.ofNullable(player);
	}

	@Override
	public void start() {
		Bukkit.getServer().broadcast(Component.text("[ChargeGoal] - Targetting ").append(target.name()));
		boss.getBossEntity().lookAt(
				target.getEyeLocation().x(),
				target.getEyeLocation().y(),
				target.getEyeLocation().z(),
				LookAnchor.EYES);
		lastTargetLoc = target.getLocation();
		originalLoc = boss.getBossEntity().getLocation();
	}

	@Override
	public void tick() {
		Bukkit.getServer().broadcast(Component.text(String.format("[ChargeGoal] - Charging!!!")));
		boss.getBossEntity().lookAt(lastTargetLoc);
		boss.getBossEntity().getPathfinder().moveTo(lastTargetLoc, 2);
		boss.getBossEntity().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, boss.getBossEntity().getLocation(), 10);
	}

	@Override
	public void stop() {
		if (target.getLocation().distance(boss.getBossEntity().getLocation()) < 5.0D) {
			var delta = new Vector(
					target.getX(),
					target.getY(),
					target.getZ()).subtract(
							new Vector(
									boss.getBossEntity().getX(),
									boss.getBossEntity().getY(),
									boss.getBossEntity().getZ()));
			target.knockback(2, -delta.getX(), -delta.getZ());
		}
		Bukkit.broadcast(Component.text("[ChargeGoal] - Stop"));
		this.lastTickActivated = Bukkit.getCurrentTick();
	}

	@Override
	public boolean shouldActivate() {
		Collection<Player> nearbyPlayers = boss.getBossEntity().getLocation().getNearbyPlayers(SCAN_RADIUS, boss.getEngagedPlayers()::contains);
		Optional<Player> closest = getClosest(nearbyPlayers);
		int currentTick = Bukkit.getCurrentTick();
		Bukkit.broadcast(Component.text(String.format("[ChargeGoal] - cooldown=%d", currentTick - lastTickActivated)));
		if (closest.isPresent() && currentTick - lastTickActivated > COOLDOWN) {
			this.target = closest.get();
			this.lastTickActivated = currentTick;
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldStayActive() {
		boolean f = boss.getBossEntity().getLocation().distance(lastTargetLoc) > 2.0D;
		Bukkit.broadcast(Component.text(String.format("[ChargeGoal] - ShouldStayActive=%b", f)));
		return f;
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(
				Mob.class,
				new NamespacedKey("ourstory", "boss_goal_charge"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.MOVE);
	}
}
