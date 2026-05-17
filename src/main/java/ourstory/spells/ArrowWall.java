package ourstory.spells;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;


public class ArrowWall extends Spell {
	// Spell test = new ArrowWall(player, player.getNearbyEntities(20, 5, 20), 3);
	/*
	 * if still called with getNearbyEntities, do not put a hugh y argument, it will only shoot on the
	 * floor (entity in caves...)
	 */

	private List<Entity> targets;
	private Entity caster, target;
	private int level, cpt, cptmax, speed;
	private Location bossLocation;
	private List<PotionType> arroweffect;

	public ArrowWall(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	@Override
	public void setup() {
		Bukkit.getServer().broadcast(Component.text("number of targets =" + targets.size()));
		this.cpt = 0;
		this.cptmax = 80 * level;
		this.speed = 3 * level;
		this.arroweffect = List.of(
				PotionType.LONG_SLOWNESS,
				PotionType.LONG_WEAKNESS,
				PotionType.HARMING,
				PotionType.INFESTED,
				PotionType.LONG_POISON,
				PotionType.OOZING,
				PotionType.AWKWARD);
	}

	@Override
	public void tick() {
		this.bossLocation = caster.getLocation().add(0, 4, 0);
		this.target = targets.get(new Random().nextInt(targets.size()));
		Vector direction = new Vector(
				target.getX() - bossLocation.getX(),
				target.getY() - bossLocation.getY(),
				target.getZ() - bossLocation.getZ());
		// Spawn the arrow at the boss's location with the calculated direction
		Arrow arrow = caster.getWorld().spawnArrow(bossLocation, direction, this.speed, 6);
		arrow.setLifetimeTicks(1100);
		arrow.setCritical(true);
		arrow.setPierceLevel(4);
		arrow.setGravity(false);
		arrow.setBasePotionType(arroweffect.get(new Random().nextInt(arroweffect.size())));
		cpt++;
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return cpt > cptmax;
	}
}
