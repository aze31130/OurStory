package ourstory.events;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ourstory.Main;

/*
 * Credit to @aurel
 */
public class onTridentHit implements Listener {
	private NamespacedKey wipeTridentKey() {
		return new NamespacedKey(Main.plugin, "wipeTrident");
	}

	@EventHandler
	public void onLaunch(ProjectileLaunchEvent e) {
		Projectile trident = e.getEntity();

		if (trident.getType() != EntityType.TRIDENT)
			return;

		if (!(trident.getShooter() instanceof Player p))
			return;

		ItemStack mainHand = p.getInventory().getItemInMainHand();
		ItemMeta meta = mainHand.getItemMeta();
		if (meta == null || !meta.hasEnchant(Enchantment.CHANNELING))
			return;

		trident.getPersistentDataContainer().set(wipeTridentKey(), PersistentDataType.INTEGER, 1);
	}

	@EventHandler
	public void onTridentHitBlock(ProjectileHitEvent event) {
		Projectile trident = event.getEntity();

		if (!(trident instanceof Trident))
			return;

		if (!trident.getPersistentDataContainer().has(wipeTridentKey(), PersistentDataType.INTEGER))
			return;

		Block hitBlock = event.getHitBlock();
		if (hitBlock == null)
			return;

		if (!(trident.getShooter() instanceof Player player))
			return;

		if (hitBlock.getType() == Material.LIGHTNING_ROD) {
			player.getWorld().strikeLightning(event.getEntity().getLocation()).setCausingPlayer(player);
			player.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
		}
	}

	@EventHandler
	public void onTridentHitEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity target = event.getEntity();

		if (!(damager instanceof Trident trident))
			return;

		if (!damager.getPersistentDataContainer().has(wipeTridentKey(), PersistentDataType.INTEGER))
			return;

		if (!(trident.getShooter() instanceof Player shooter))
			return;

		target.getWorld().strikeLightning(event.getEntity().getLocation()).setCausingPlayer(shooter);
		target.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
	}
}
