package ourstory.events;

import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class onPlayerDeath implements Listener {

	private static final String[] deathMessages = {
			"[player] died !",
			"[player] decided to alt-f4",
			"Look at [player], he's dead now !",
			"[player] forgot to read the safety manual",
			"[player] was just trying to mind their own business...",
			"[player] thought he was invincible. He was wrong.",
			"[player] was having a good day... until now",
			"[player] is now a ghost, haunting this very spot",
			"[player] challenged Minecraft and lost",
			"[player] underestimated the situation",
			"[player] took a shortcut... to the afterlife",
			"1... 2... 3... [player] is gone !",
			"[player] was yeeted out of existence",
			"[player] just bus-canned",
			"[player] took an L, permanently.",
			"[player] is taking an involuntary nap.",
			"Oops! [player] forgot to dodge.",
			"[player] is now one with the void.",
			"[player] failed the survival exam.",
			"[player] just got deleted from existence.",
			"That's one way to log out, [player].",
			"[player] saw the light... and walked right into it.",
			"RIP [player], gone too soon.",
			"[player] forgot how to not die.",
			"[player] fell off the edge of reason.",
			"[player] didn't stick the landing.",
			"Press F for [player].",
			"[player] played with fire... and got burned.",
			"[player] ran out of luck, and hearts.",
			"[player] just experienced sudden gravity.",
			"[player] picked a fight with the wrong mob.",
			"Looks like [player] hit a dead end.",
			"[player] zigged when they should've zagged.",
			"Game over, [player].",
			"[player] just found the fast way down.",
			"[player] went out with a bang... literally.",
			"[player] tested the fall damage limit. It’s high.",
			"[player] is now sleeping with the squids.",
			"[player] just rage quit life.",
			"[player] made a poor life choice.",
			"[player] is now a resident of Respawn Village.",
			"[player] learned the hard way about TNT.",
			"[player] is now on spectator mode. Forever.",
			"[player] had an explosive personality. Too explosive.",
			"[player] took a leap of faith... without faith.",
			"[player] tried to swim in lava. Big mistake.",
			"[player] couldn’t handle the pressure (plate).",
			"That was the last creeper [player] saw.",
			"[player] forgot to breathe. Oops.",
			"The mobs won. [player] lost.",
			"[player] just rage quit the real way.",
			"[player] tried to outrun death. Didn’t work.",
			"[player] was just minding their own business... or not.",
			"[player] was last seen falling from a great height.",
			"[player] went boom-boom.",
			"The End just claimed [player].",
			"[player] is now pixel dust.",
			"[player] tried to fly. Bad idea.",
			"[player] pulled an 'oopsie' and paid for it.",
			"[player] forgot to bring a totem.",
			"[player] became food for the zombies.",
			"[player] took a creeper hug... bad decision.",
			"[player] got caught in a crossfire.",
			"[player] has entered the void, permanently."};

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();

		Random rng = new Random();

		Bukkit.broadcast(Component.text(deathMessages[rng.nextInt(deathMessages.length)].replace("[player]", player.getName())).color(NamedTextColor.DARK_RED));

		// Whisper to player his death location
		player.sendMessage(Component.text(
				"Death location (" + player.getWorld().getName() + ")" +
						" X: " + player.getLocation().getBlockX() +
						" Y: " + player.getLocation().getBlockY() +
						" Z: " + player.getLocation().getBlockZ())
				.color(NamedTextColor.LIGHT_PURPLE));

		// Death sound
		for (org.bukkit.entity.Player OnlinePlayer : Bukkit.getOnlinePlayers())
			OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1000, 1);

		// Test for Phoenix enchant
		ItemStack[] armorContents = player.getInventory().getArmorContents();

		int totalPhoenixLevel = 0;

		for (ItemStack armor : armorContents) {
			if (armor != null) {
				Map<Enchantment, Integer> enchants = armor.getEnchantments();

				for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
					Enchantment enchantment = entry.getKey();
					int level = entry.getValue();

					if (enchantment.getKey().getKey().equals("phoenix"))
						totalPhoenixLevel += level;
				}
			}
		}

		if (rng.nextInt(0, 101) < (totalPhoenixLevel * 2.5)) {
			player.sendMessage(Component.text("You got blessed by the Phoenix enchant ! Your inventory has been safeguarded !").color(NamedTextColor.GREEN));
			event.setKeepInventory(true);
			event.setDroppedExp(0);
			event.getDrops().clear();
		}
	}
}
