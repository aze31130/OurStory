package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;

/*
 * Credit to @aurel
 */
public class onTridentHit implements Listener {
	private final NamespacedKey wipeTrident = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "wipeTrident");

	@EventHandler
	public void onLaunch(ProjectileLaunchEvent e) {
		Projectile trident = e.getEntity();

		if (trident.getType() != EntityType.TRIDENT)
			return;

		if (!(trident.getShooter() instanceof Player))
			return;

		Player p = (Player) trident.getShooter();

		if (!p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.CHANNELING))
			return;
		trident.getPersistentDataContainer().set(wipeTrident, PersistentDataType.INTEGER, 1);
	}

	/*
	 * Detects when a trident hits a bloc
	 */
	@EventHandler
	public void onTridentHitBlock(ProjectileHitEvent event) {
		Projectile trident = event.getEntity();

		if (!(trident instanceof Trident))
			return;

		if (!trident.getPersistentDataContainer().has(wipeTrident, PersistentDataType.INTEGER))
			return;

		Block hitBlock = event.getHitBlock();

		if (hitBlock == null)
			return;

		Player player = (Player) trident.getShooter();

		if (hitBlock.getType() == Material.LIGHTNING_ROD) {
			player.getWorld().strikeLightning(event.getEntity().getLocation()).setCausingPlayer(player);
			player.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
		}
	}

	/*
	 * Triggers when a trident deals damage to an entity
	 */
	@EventHandler
	public void onTridentHitEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity target = event.getEntity();

		if (!(damager instanceof Trident))
			return;

		if (!damager.getPersistentDataContainer().has(wipeTrident, PersistentDataType.INTEGER))
			return;

		// Fire the lightning
		target.getWorld().strikeLightning(event.getEntity().getLocation()).setCausingPlayer((Player) ((Projectile) damager).getShooter());
		target.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
	}
}
