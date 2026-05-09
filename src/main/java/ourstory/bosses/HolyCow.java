package ourstory.bosses;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ourstory.goal.ChargeClosestGoal;
import ourstory.goal.SleepGoal;
import ourstory.spells.Annihilation;
import ourstory.spells.Spell;

public class HolyCow implements IBoss {
	private final Mob mob;
	private final ImmutableList<Player> engagedPlayers;

	private final int level;

	/**
	 * @param mob : Entity attached to the boss
	 * @param engaged : The players involved in the fight.
	 */
	public HolyCow(final Mob mob, final ImmutableList<Player> engagedPlayers, int level) {
		this.mob = mob;
		this.engagedPlayers = engagedPlayers;
		this.level = level;
	}

	/**
	 * Register different behaviours of the boss. These can be seen as phases. Each phase defines a set
	 * of spells that the boss can use.
	 */
	public void registerGoals(final MobGoals goals) {
		goals.removeAllGoals(mob);
		goals.addGoal(mob, 0, new SleepGoal(this)); // Phase 1
		goals.addGoal(mob, 1, new ChargeClosestGoal(this)); // Phase 2
	}

	@Override
	public ImmutableList<Player> getEngagedPlayers() {
		return engagedPlayers;
	}

	@Override
	public Mob getBossEntity() {
		return mob;
	}

	@Override
	public Set<Spell> getSpells(int phase) {
		Set<Spell> set = new HashSet<>();
		// La l'idée c'est de choisir en fonction de la phase les spells qu'on veut.
		if (phase == 0) {
			set.add(new Annihilation(mob, engagedPlayers, level));
		}
		return set;
	}
}
