package ourstory.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;

public class onZombieDeath implements Listener {
	/*
	 * If a zombie if killed by fire, makes him drop leather instead of rotten flesh
	 */
	private final List<EntityType> zombies = Arrays.asList(
			EntityType.ZOMBIE,
			EntityType.HUSK,
			EntityType.DROWNED,
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.ZOGLIN);


	@EventHandler
	public void zombieDeath(EntityDeathEvent entity) {
		if (!zombies.contains(entity.getEntityType()))
			return;

		boolean killedByFire = false;
		DamageSource source = entity.getDamageSource();

		// Check if killed from fire_aspect
		if (source.getDamageType() == DamageType.PLAYER_ATTACK) {
			Player p = (Player) source.getCausingEntity();
			ItemStack weapon = p.getInventory().getItemInMainHand();

			killedByFire = EnchantItem.getEnchantAmount(weapon, "fire_aspect") > 0;
		}

		// Check if killed by flamed arrow
		if (source.getDamageType() == DamageType.ARROW)
			killedByFire = source.getDirectEntity().getFireTicks() > 0;

		if (killedByFire || entity.getEntity().getFireTicks() > 0) {
			List<ItemStack> temp = new ArrayList<>();

			for (ItemStack drop : entity.getDrops())
				if (drop.getType() == Material.ROTTEN_FLESH)
					temp.add(drop);

			for (ItemStack d : temp) {
				entity.getDrops().add(new ItemStack(Material.LEATHER, d.getAmount()));
				entity.getDrops().remove(d);
			}
		}
	}
}
