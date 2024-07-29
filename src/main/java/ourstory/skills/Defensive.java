package ourstory.skills;

import java.util.List;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.storage.Storage;

public class Defensive {
	/*
	 * 
	 */
	public static void darkAura() {

	}

	/*
	 * 
	 */
	public static void concentration() {

	}

	/*
	 * Freezes all players in the instance during 10 seconds
	 */
	public static void stopTime() {
		Storage s = Storage.getInstance();

		for (Player p : s.bossInstance.players) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 200));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 100, 255));
		}

	}
}
