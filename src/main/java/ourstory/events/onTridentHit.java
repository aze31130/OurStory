/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
 * license Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;


/**
 *
 * @author aurel
 */
public class onTridentHit implements Listener {
	private final NamespacedKey wipeTrident = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "wipeTrident");

	@EventHandler
	public void onLaunch(ProjectileLaunchEvent e) {
		if (e.getEntity().getType() != EntityType.TRIDENT)
			return;
		if (!(e.getEntity().getShooter() instanceof Player))
			return;
		Player p = (Player) e.getEntity().getShooter();
		if (!p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.CHANNELING))
			return;
		e.getEntity().getPersistentDataContainer().set(wipeTrident, PersistentDataType.INTEGER, 1);
	}


	// Détecter quand un trident touche un bloc
	@EventHandler
	public void onTridentHitBlock(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Trident) {
			if (event.getEntity().getPersistentDataContainer().has(wipeTrident, PersistentDataType.INTEGER)) {
				if (event.getHitBlock() != null) {
					Block hitBlock = event.getHitBlock();
					Player player = (Player) event.getEntity().getShooter();
					if (hitBlock.getType() == Material.LIGHTNING_ROD) {
						if (player.getWorld().hasStorm())
							return;
						player.getWorld().strikeLightningEffect(event.getEntity().getLocation());
						player.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
					}
				}
			}
		}
	}

	// Détecter quand un trident inflige des dégâts à une entité
	@EventHandler
	public void onTridentHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Trident) {
			if (event.getDamager().getPersistentDataContainer().has(wipeTrident, PersistentDataType.INTEGER)) {
				Entity target = event.getEntity();
				if (target.getWorld().hasStorm())
					return;
				target.getWorld().strikeLightningEffect(event.getEntity().getLocation());
				target.getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1000, 1);
			}
		}
	}
}
