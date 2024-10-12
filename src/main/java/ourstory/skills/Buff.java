package ourstory.skills;

import ourstory.storage.Storage;
import ourstory.utils.PlayerUtils;

public class Buff {
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
}
