package ourstory.goal;

import java.util.EnumSet;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Vector;
import com.destroystokyo.paper.entity.ai.GoalType;
import ourstory.bosses.HolyCow;

/**
 * Première phase de la Sainte Vache. Elle descend des cieux pour venger ses camarades abattus de
 * sang froid par les joueurs.
 */
public class DivineDescentPhase extends AbstractBossGoal<HolyCow> {
	BlockDisplay glow1;
	// BlockDisplay glow2;
	// BlockDisplay glow3;
	private float rot;

	private static final float ANIM_STEP = 0.01f;

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
		boolean stillInPhase = boss.getState() == HolyCow.State.DESCENDING;
		boolean isOnGround = boss.entity.getLocation().toBlockLocation().isBlock();
		System.out.println(String.format("StayActive=%b", stillInPhase && !isOnGround));
		return (stillInPhase && !isOnGround);
	}

	void setupTransformations(BlockDisplay disp) {
		var trans = disp.getTransformation();
		trans.getTranslation().add(-0.5f, 0.0f, -0.5f);
		trans.getLeftRotation().rotateAxis((float) Math.toRadians(45.0f), 0.0f, 1.0f, 0.0f);
		// https://misode.github.io/transformation/
	}

	void tickRotation(BlockDisplay disp) {
		var trans = disp.getTransformation();
		trans.getLeftRotation().rotateAxis((float) Math.toRadians(rot), 0.0f, 1.0f, 0.0f);
		rot = (rot + ANIM_STEP) % 360.0f;
	}

	@Override
	public void start() {
		boss.entity.teleport(boss.entity.getLocation().add(0, 100, 0));
		boss.entity.setGravity(false);
		boss.entity.setAI(false);
		boss.entity.setVelocity(new Vector(0, -1, 0));
		World world = boss.entity.getWorld();
		this.glow1 = world.spawn(boss.entity.getLocation(), BlockDisplay.class);
		this.rot = 0.0f;
		setupTransformations(glow1);
	}

	@Override
	public void tick() {
		tickRotation(glow1);
	}

	@Override
	public void stop() {
		boss.entity.setGravity(true);
		boss.entity.setAI(true);
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
