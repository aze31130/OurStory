package ourstory.bosses;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableList;

public interface IBoss {
	/**
	 * Liste des joueurs qui se sont engagés contre le boss. Aucun joueur autre qu'eux ne pourra
	 * attaquer le boss, recevoir des dégats du boss, ni ne recevoir de loot à sa mort.
	 */
	ImmutableList<Player> getEngagedPlayers();

	/**
	 * Entité qui représente le boss en jeu.
	 */
	Mob getBossEntity();
}
