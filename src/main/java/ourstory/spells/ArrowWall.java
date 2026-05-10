package ourstory.spells;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ArrowWall extends Spell {
	// TODO give arrow a random effect

	private List<Entity> targets;
	private Entity caster;
	private int level, cpt, cptmax, speed;
	private Location bossLocation;
	private Entity target;

	public ArrowWall(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		this.targets = targets;
		this.caster = caster;
		this.level = level;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		this.cpt = 0;
		this.cptmax = 80 * level;
		this.speed = 3 * level;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

		this.bossLocation = caster.getLocation().add(0, 4, 0);
		this.target = targets.get(new Random().nextInt(targets.size()));
		Vector direction = new Vector(
				target.getX() - bossLocation.getX(),
				target.getY() - bossLocation.getY(),
				target.getZ() - bossLocation.getZ());

		// Spawn the arrow at the boss's location with the calculated direction
		Arrow arrow = caster.getWorld().spawnArrow(bossLocation, direction, 2, 6);
		arrow.setLifetimeTicks(1100);
		arrow.setCritical(true);
		arrow.setPierceLevel(4);
		arrow.setGravity(false);
		cpt++;
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return cpt > cptmax;
	}

}
