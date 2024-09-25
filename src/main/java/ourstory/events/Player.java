package ourstory.events;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Player implements Listener {

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
			"[player] just bus-canned"};

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();

		player.sendMessage(Component.text("Welcome back to OurStory " + player.getName() + " !")
				.color(NamedTextColor.AQUA));

		player.sendMessage(
				Component.text(
						"! WARNING TO NEW PLAYERS ! In order to fully access the server (break blocks, eat, ...) you need to fill the server's form at https://forms.gle/zWbcYjQWpHZLrC818 or directly contact the server owner !")
						.color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
	}


	@EventHandler
	public void onItemConsume(final PlayerItemConsumeEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();

		switch (item.getType()) {
			case RABBIT_STEW:
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2400, 1));
				break;

			case PUMPKIN_PIE:
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3600, 1));
				break;

			case BEETROOT_SOUP:
				player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 6000, 1));
				break;

			default:
				break;
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		TeleportCause cause = event.getCause();

		switch (cause) {
			case ENDER_PEARL:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
				break;

			case CHORUS_FRUIT:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 700, 0));
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0));
				break;

			default:
				break;
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
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
		// ItemStack offItem = player.getPlayer().getInventory().getItemInOffHand();
		// ItemMeta itemMetaOffItem = offItem.getItemMeta();
		// if (itemMetaOffItem.getLore().contains("�7Phoenix X"))
		// event.setKeepInventory(true);
	}

	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.TNT)) {
			// TODO send alert message including coords
		}
	}

	@EventHandler
	public void onXpPickup(PlayerExpChangeEvent experience) {
		org.bukkit.entity.Player player = experience.getPlayer();
		int multiplier = 1;

		// LUCK potion effect works as exp multiplicator
		if (player.hasPotionEffect(PotionEffectType.LUCK)) {
			PotionEffect pe = player.getPotionEffect(PotionEffectType.LUCK);
			multiplier += pe.getAmplifier() + 1;
		}

		experience.setAmount(experience.getAmount() * multiplier);
	}

	/*
	 * Removes player that breaks farmland if jumped on top
	 */
	@EventHandler
	public void cancelPlayerJumpOnFarmland(PlayerInteractEvent e) {
		if (e == null)
			return;

		// Disable jumping on farmlands
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType().equals(Material.FARMLAND)) {
			e.setCancelled(true);
		}
	}
}
