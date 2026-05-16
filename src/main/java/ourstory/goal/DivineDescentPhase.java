package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.util.TriState;
import ourstory.bosses.HolyCow;
import ourstory.bosses.HolyCow.State;

/**
 * Première phase de la Sainte Vache. Elle descend des cieux pour venger ses camarades abattus de
 * sang froid par les joueurs.
 */
public class DivineDescentPhase extends AbstractBossGoal<HolyCow> {
	BlockDisplay glassShield;
	private Matrix4f shieldTransformation;
	private float shieldScale;

	private static final int ANIM_DURATION = 20;

	public DivineDescentPhase(final HolyCow boss) {
		super(boss);
	}

	@Override
	public boolean shouldActivate() {
		return boss.getState() == HolyCow.State.DESCENDING;
	}

	@Override
	public boolean shouldStayActive() {
		return Math.abs(boss.entity.getVelocity().getY()) > 0.01F;
	}

	private Location getBossLocation() {
		var bb = boss.entity.getBoundingBox();
		return new Location(
				boss.entity.getWorld(),
				bb.getCenterX(),
				bb.getCenterY(),
				bb.getCenterZ());
	}

	/* ------------------------------- Prepare ------------------------------- */
	@Override
	public void start() {
		var str = String.format("[DivineDescentPhase] start()");

		/* Logic */
		boss.targets.forEach(p -> p.sendMessage(str));
		boss.entity.setGravity(false);
		// boss.entity.setAI(false);
		boss.entity.setFrictionState(TriState.FALSE);
		boss.entity.setVelocity(new Vector(0.0f, -0.1f, 0.0f));
		boss.entity.setInvulnerable(true);

		/* Visuals */
		World world = boss.entity.getWorld();
		this.shieldScale = 4.0f;
		/* Initial transformation */
		this.shieldTransformation = new Matrix4f()
				.rotateXYZ(
						(float) Math.toRadians(90f) + 0.001f,
						(float) Math.toRadians(90f) + 0.001f,
						(float) Math.toRadians(90f) + 0.001f)
				.translate(
						-shieldScale / 2f,
						-shieldScale / 2f,
						-shieldScale / 2f)
				.scale(shieldScale);
		this.glassShield = world.spawn(getBossLocation(), BlockDisplay.class, entity -> {
			entity.setBlock(Material.GLASS.createBlockData());
			entity.setTransformationMatrix(shieldTransformation);
			/* this avoids flickering */
			entity.setTeleportDuration(2);
		});
	}
	/* ---------------------------------------------------------------------- */

	/* ------------------------------- Update ------------------------------- */
	@Override
	public void tick() {
		if (!boss.entity.isValid() || !glassShield.isValid()) {
			return;
		}
		/* Rotate glassShield */
		glassShield.teleport(getBossLocation());
		// boss.targets.forEach(p -> p.sendMessage("tickCount=" + tickCount));
		this.shieldTransformation = shieldTransformation
				.rotateXYZ(
						(float) Math.toRadians(360f) + 0.1f,
						(float) Math.toRadians(360f) + 0.1f,
						(float) Math.toRadians(360f) + 0.1f);
		glassShield.setInterpolationDelay(0);
		glassShield.setInterpolationDuration(ANIM_DURATION);
		glassShield.setTransformationMatrix(shieldTransformation);
		/* Repell entities */
		for (Entity e : boss.entity.getLocation().getNearbyEntitiesByType(Entity.class, shieldScale)) {
			if (e.getUniqueId() == boss.entity.getUniqueId() || e.getUniqueId() == glassShield.getUniqueId())
				continue;
			Vector dir = e.getLocation().subtract(boss.entity.getLocation()).getDirection();
			e.setVelocity(e.getVelocity().subtract(dir));
		}
	}
	/* ---------------------------------------------------------------------- */

	/* -------------------------------- Stop -------------------------------- */
	@Override
	public void stop() {
		var str = String.format("[DivineDescentPhase] stop()");
		boss.targets.forEach(p -> p.sendMessage(str));
		boss.entity.setGravity(true);
		boss.entity.setFrictionState(TriState.TRUE);
		boss.entity.setInvulnerable(false);
		// boss.entity.setAI(true);
		glassShield.remove();
		boss.setState(State.SLEEPING);
	}
	/* ---------------------------------------------------------------------- */

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.LOOK, GoalType.MOVE);
	}

	@Override
	String getPhaseKey() {
		return "holy_cow_descent";
	}
}
