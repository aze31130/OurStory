package ourstory.skills;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.storage.Storage;
import ourstory.utils.PlayerUtils;

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

	public static void reinforce() {
		Storage s = Storage.getInstance();

		PlayerUtils.broadcastToPlayers(s.bossInstance.players, "Feel my strengh");

	}

	public static void heal() {
		Storage s = Storage.getInstance();
		// Entity boss = s.bossInstance.monster.entity;

		PlayerUtils.broadcastToPlayers(s.bossInstance.players, "Ultimate Heal !");

		// boss.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 20, 120));
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
