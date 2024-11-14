package ourstory.events;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;

public class onEntityDeath implements Listener {
	@EventHandler
	public void entityDeath(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		Player killer = (Player) entity.getEntity().getKiller();

		// Compute Leech enchant
		ItemStack weapon = killer.getInventory().getItemInMainHand();
		int totalLeechLevel = EnchantItem.getEnchantAmount(weapon, "leech");

		killer.heal(totalLeechLevel / 2);
	}

	@EventHandler
	public void MonsterHeadDrop(EntityDeathEvent entity) {
		if (entity.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent) {
			if (!(damageEvent.getDamager() instanceof Creeper)) {
				return;
			}
		} else
			return;
		Creeper killer = (Creeper) ((EntityDamageByEntityEvent) entity.getEntity().getLastDamageCause()).getDamager();
		if (!killer.isPowered())
			return;
		if (!(entity.getEntity() instanceof Zombie ||
				entity.getEntity() instanceof Creeper ||
				entity.getEntity() instanceof Skeleton ||
				entity.getEntity() instanceof WitherSkeleton ||
				entity.getEntity() instanceof Piglin))
			return;

		ItemStack head = null;
		if (entity.getEntity() instanceof Zombie) {
			head = new ItemStack(Material.ZOMBIE_HEAD);
		} else if (entity.getEntity() instanceof Creeper) {
			head = new ItemStack(Material.CREEPER_HEAD);
		} else if (entity.getEntity() instanceof Skeleton) {
			head = new ItemStack(Material.SKELETON_SKULL);
		} else if (entity.getEntity() instanceof WitherSkeleton) {
			head = new ItemStack(Material.WITHER_SKELETON_SKULL);
		} else if (entity.getEntity() instanceof Piglin) {
			head = new ItemStack(Material.PIGLIN_HEAD);
		}

		boolean headDropped = false;
		for (ItemStack drop : entity.getDrops()) {
			if (drop.getType() == head.getType()) {
				headDropped = true;
				break;
			}
		}

		if (!headDropped) {
			entity.getDrops().add(head);
		}
	}
}
