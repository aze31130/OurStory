package ourstory.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
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

public final class TalvenPhase3 implements Goal<Mob> {
	private final Boss boss;
	private Player target;
	private Location lastTargetLoc;
	private Random random;

	private final List<Spell> spells;
	/**
	 * Last time (in server ticks) the behaviour has been triggered
	 */
	private Integer lastTickActivated;
	private Boolean canUseSpell;
	private Spell currentSpell;

	public TalvenPhase3(final Boss boss, final List<Spell> spells) {
		this.boss = boss;
		this.spells = spells;
	}

	@Override
	public void start() {
		this.random = new Random();
		this.canUseSpell = true;
	}

	@Override
	public void tick() {
		if (canUseSpell && (random.nextInt(100) < 1)) {
			this.currentSpell = spells.get(0);
			this.currentSpell.setup();
			this.canUseSpell = false;
		}

		if (this.currentSpell != null) {
			this.currentSpell.tick();

			if (this.currentSpell.shouldStop()) {
				this.currentSpell.stop();
				canUseSpell = true;
			}
		}
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldActivate() {
		double currentHealth = (this.boss.entity.getHealth() / this.boss.entity.getAttribute(Attribute.MAX_HEALTH).getValue());
		return currentHealth < 0.25;
	}

	@Override
	public GoalKey<Mob> getKey() {
		return GoalKey.of(Mob.class, new NamespacedKey("ourstory", "boss_phase3"));
	}

	@Override
	public EnumSet<GoalType> getTypes() {
		return EnumSet.of(GoalType.TARGET);
	}
}
