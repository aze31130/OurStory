package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import ourstory.Main;

public class onBossHit implements Listener {
	private final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final NamespacedKey bossKey = new NamespacedKey(plugin, "isBoss");

	@EventHandler
	public void bossHit(EntityDamageByEntityEvent entity) {
		// Cancel if not player
		if (!(entity.getDamager() instanceof Player))
			return;

		// Call onHit method for boss monsters
		if (entity.getEntity().getPersistentDataContainer().has(bossKey))
			Main.runningInstance.boss.onHit(entity);
	}
}
