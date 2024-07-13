package ourstory.skills;

import java.util.List;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.utils.PlayerUtils;

public class Buff {
	public static void reinforce(Monster boss, List<Player> damager) {
		PlayerUtils.broadcastToPlayers(damager, "Feel my strengh");

	}

	public static void heal(Monster boss, List<Player> damager) {
		PlayerUtils.broadcastToPlayers(damager, "Ultimate Heal !");

		boss.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 20, 120));
	}
}
