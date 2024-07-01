package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Player implements Listener {

	static final String[] deathMessages = {"[player] died !", "[player] decided to alt-f4"};

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();

		// String ip = player.getAddress().getHostName();
		// if ((player.getName().equalsIgnoreCase("aze31130")) && (!ip.equals("guilhem-1.home"))){
		// player.kick();

		player.sendMessage(Component.text("Welcome back to OurStory 2.0 " + player.getName() + " !")
				.color(TextColor.color(0x443344)));
		player.sendMessage(Component.text("Type /changelog to see list of all changes"));
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

		Bukkit.broadcast(Component.text(player.getName() + " died !"));

		// Whisper to player his death location
		player.sendMessage("Your death location:");
		player.sendMessage("World: " + player.getWorld().getName());
		player.sendMessage("X: " + player.getLocation().getBlockX());
		player.sendMessage("Y: " + player.getLocation().getBlockY());
		player.sendMessage("Z: " + player.getLocation().getBlockZ());

		// Death sound
		for (org.bukkit.entity.Player OnlinePlayer : Bukkit.getOnlinePlayers())
			OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1000, 1);

		// Test for Phoenix enchant
		// ItemStack offItem = player.getPlayer().getInventory().getItemInOffHand();
		// ItemMeta itemMetaOffItem = offItem.getItemMeta();
		// if itemMetaOffItem.getLore().contains("�7Phoenix X"))) { event.setKeepInventory(true);
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
}
