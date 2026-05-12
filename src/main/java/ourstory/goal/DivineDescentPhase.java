package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import com.destroystokyo.paper.entity.ai.GoalType;
import net.kyori.adventure.util.TriState;
import ourstory.bosses.HolyCow;
import ourstory.bosses.HolyCow.State;

/**
 * Première phase de la Sainte Vache. Elle descend des cieux pour venger ses camarades abattus de
 * sang froid par les joueurs.
 */
public class DivineDescentPhase extends AbstractBossGoal<HolyCow> {
	BlockDisplay glow1;
	private Matrix4f transformation;

	private static final int ANIM_DURATION = 20;
	private int tickCount;

	public DivineDescentPhase(final HolyCow boss) {
		super(boss);
	}

	@Override
	public boolean shouldActivate() {
		System.out.println("State=" + boss.getState());
		return boss.getState() == HolyCow.State.DESCENDING;
	}

	@Override
	public boolean shouldStayActive() {
		return Math.abs(boss.entity.getVelocity().getY()) > 0.01F;
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
		this.transformation = new Matrix4f()
				.translate(new Vector3f(0.5f, 0f, 0.5f));
		this.glow1 = world.spawn(boss.entity.getLocation(), BlockDisplay.class, entity -> {
			entity.setBlock(Material.CYAN_STAINED_GLASS.createBlockData());
			entity.setTransformationMatrix(transformation);
			// entity.setTransformation(
			// new Transformation(
			// new Vector3f(0.5f, 0.5f, 0.5f),
			// new AxisAngle4f((float) Math.toRadians(45.0D), 1f, 0f, 0f),
			// new Vector3f(2f, 2f, 2f),
			// new AxisAngle4f()));
			entity.setInterpolationDelay(1);
			entity.setInterpolationDuration(ANIM_DURATION);
		});
		this.tickCount = 0;
		tickRotation(glow1); // create interp
	}
	/* ---------------------------------------------------------------------- */

	/* ------------------------------- Update ------------------------------- */
	void tickRotation(BlockDisplay disp) {
		disp.setInterpolationDelay(1);
		disp.setInterpolationDuration(ANIM_DURATION); // bah jsp...
		disp.setTransformationMatrix(transformation
				// .rotateX(((float) Math.toRadians(360f)) + 0.1F)
				.rotateY(((float) Math.toRadians(180f)) + 0.1F /* Avoid client interpolation */));
	}

	@Override
	public void tick() {
		boss.targets.forEach(p -> p.sendMessage("tickCount=" + tickCount));
		if (tickCount < ANIM_DURATION) {
			tickCount++;
			return;
		}
		tickCount = 0;
		tickRotation(glow1);
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
		glow1.remove();
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
