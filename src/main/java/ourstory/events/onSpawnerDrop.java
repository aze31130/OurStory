package ourstory.events;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.utils.EnchantItem;
import ourstory.utils.MonsterUtils;

public class onSpawnerDrop implements Listener {
	/*
	 * Event that computes if a spawner should drop
	 */
	@EventHandler
	public void spawnerDrop(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		Player killer = (Player) entity.getEntity().getKiller();

		// Compute looting enchant
		ItemStack weapon = killer.getInventory().getItemInMainHand();
		int totalLootingLevel = EnchantItem.getEnchantAmount(weapon, "looting");

		int rates[] = {1000000, 975000, 900000, 825000, 750000, 600000, 500000, 350000, 200000, 100000, 10000};

		Random rng = new Random();

		if (rng.nextInt(rates[totalLootingLevel]) == 1) {
			EntityType entityType = entity.getEntity().getType();
			Material spawnEgg = MonsterUtils.getMonsterEgg(entityType);

			entity.getDrops().add(new ItemStack(spawnEgg, 1));

			Bukkit.broadcast(Component.text("Player " + killer.getName() + " just dropped a Mythical " + spawnEgg.toString() + " !").color(NamedTextColor.DARK_PURPLE));
			Bukkit.broadcast(Component.text("Congratulation on such an amazing achievement !").color(NamedTextColor.DARK_PURPLE));

			for (Player OnlinePlayer : Bukkit.getOnlinePlayers())
				OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1000, 1);
		}
	}
}
