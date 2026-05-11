package ourstory.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;
import ourstory.utils.EnchantItem;

public class onPhoenixDeath implements Listener {

	private final Map<UUID, List<PotionEffect>> savedEffects = new HashMap<>();

	@EventHandler
	public void phoenixDeath(PlayerDeathEvent event) {
		Player player = event.getPlayer();

		ItemStack[] armorContents = player.getInventory().getArmorContents();
		int totalPhoenixLevel = 0;
		for (ItemStack armor : armorContents)
			totalPhoenixLevel += EnchantItem.getEnchantAmount(armor, "phoenix");

		if (totalPhoenixLevel <= 0)
			return;

		double roll = ThreadLocalRandom.current().nextDouble(0, 100);
		if (roll >= totalPhoenixLevel * 2.5)
			return;

		player.sendMessage(Component.text("You got blessed by the Phoenix enchant ! Your inventory and effects have been safeguarded !").color(NamedTextColor.GREEN));
		event.setKeepInventory(true);
		event.setKeepLevel(false);
		event.setDroppedExp(0);
		event.getDrops().clear();
		savedEffects.put(player.getUniqueId(), List.copyOf(player.getActivePotionEffects()));
	}

	@EventHandler
	public void phoenixRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		List<PotionEffect> effects = savedEffects.remove(player.getUniqueId());
		if (effects == null)
			return;

		Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
			for (PotionEffect effect : effects)
				player.addPotionEffect(effect);
		}, 20);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		savedEffects.remove(event.getPlayer().getUniqueId());
	}
}
