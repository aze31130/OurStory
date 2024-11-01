package ourstory.events;

import java.lang.annotation.Target;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import ourstory.utils.EnchantItem;
import org.bukkit.util.Vector;


public class onPlayerInteract implements Listener {
	/*
	 * Removes player that breaks farmland if jumped on top
	 */
	@EventHandler
	public void cancelPlayerJumpOnFarmland(PlayerInteractEvent e) {
		if (e == null)
			return;

		// Disable jumping on farmlands
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType().equals(Material.FARMLAND))
			e.setCancelled(true);
	}


	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");
	private final HashMap<Player, BukkitRunnable> runningTasks = new HashMap<>();

	@EventHandler
	public void ApplyEffectWithSpyglass(PlayerInteractEvent event) {
		if (event == null)
			return;
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		Player player = event.getPlayer();

		if (runningTasks.containsKey(player)) {
			runningTasks.get(player).cancel();
			runningTasks.remove(player);
		}

		// Démarrer une nouvelle tâche
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				if (!(player.getInventory().getItemInMainHand().getType().equals(Material.SPYGLASS) || player.getInventory().getItemInOffHand().getType().equals(Material.SPYGLASS))) {
					// Si le joueur ne regarde plus avec le spyglass, annuler la tâche
					this.cancel();
					runningTasks.remove(player);
				}
				ItemStack item = player.getInventory().getItemInMainHand().getType().equals(Material.SPYGLASS) ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();

				int VulneSeekLevel = EnchantItem.getEnchantAmount(item, "vulnerability_seeker");

				if ((VulneSeekLevel > 0) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
					Vector direction = player.getEyeLocation().getDirection();
					RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(), direction, 50, (entity) -> entity instanceof Entity && entity != player);
					if (result != null && result.getHitEntity() instanceof LivingEntity) {
						((LivingEntity) result.getHitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1200, 1, false, false, true));
					}
				} else {
					// Si le joueur ne regarde plus avec le spyglass, annuler la tâche
					this.cancel();
					runningTasks.remove(player);
				}
			}
		};

		task.runTaskTimer(p, 0, 1); // Exécutez chaque tick
		runningTasks.put(player, task);
	}
}
