package ourstory.events;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onShieldBoing implements Listener {
	private final double proximityKnockback = 0.7;
	private final double projectileKnockback = 0.5;

	@EventHandler
	public void proximityBoing(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player player))
			return;
		if (!(player.isBlocking()))
			return;
		if (!(event.getDamager() instanceof LivingEntity damager))
			return;
		ItemStack mainhand = player.getInventory().getItemInMainHand();
		ItemStack offhand = player.getInventory().getItemInOffHand();
		int level = mainhand.getType() == Material.SHIELD ? EnchantItem.getEnchantAmount(mainhand, "boing") : EnchantItem.getEnchantAmount(offhand, "boing");
		if (level < 1)
			return;
		damager.knockback(proximityKnockback * level, player.getLocation().getX() - damager.getLocation().getX(), player.getLocation().getZ() - damager.getLocation().getZ());
	}

	@EventHandler
	public void projectileBoing(ProjectileHitEvent event) {
		if (!(event.getHitEntity() instanceof Player player))
			return;
		if (!(player.isBlocking()))
			return;
		ItemStack mainhand = player.getInventory().getItemInMainHand();
		ItemStack offhand = player.getInventory().getItemInOffHand();
		double level = mainhand.getType() == Material.SHIELD ? EnchantItem.getEnchantAmount(mainhand, "boing") : EnchantItem.getEnchantAmount(offhand, "boing");
		if (level < 1)
			return;
		// // Version retour à l'envoyeur
		// Vector velo = new Vector();
		// velo.setX(event.getEntity().getVelocity().getX());
		// velo.setY(event.getEntity().getVelocity().getY());
		// velo.setZ(event.getEntity().getVelocity().getZ());
		// velo.multiply(level * -1);

		// Version Genji
		Vector velo = player.getEyeLocation().getDirection();
		double longeur = event.getEntity().getVelocity().length();
		velo.multiply(longeur * level * projectileKnockback);


		event.getEntity().setVelocity(velo);
		event.getEntity().setShooter(player);
		event.setCancelled(true);
	}
}
