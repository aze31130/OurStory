package ourstory.events;

import java.util.ArrayList;
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
	private static final List<EntityType> ZOMBIES = List.of(
			EntityType.ZOMBIE,
			EntityType.HUSK,
			EntityType.DROWNED,
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.ZOGLIN);

	@EventHandler
	public void zombieDeath(EntityDeathEvent entity) {
		if (!ZOMBIES.contains(entity.getEntityType()))
			return;

		boolean killedByFire = false;
		DamageSource source = entity.getDamageSource();
		if (source == null)
			return;

		if (source.getDamageType() == DamageType.PLAYER_ATTACK && source.getCausingEntity() instanceof Player p) {
			ItemStack weapon = p.getInventory().getItemInMainHand();
			killedByFire = EnchantItem.getEnchantAmount(weapon, "fire_aspect") > 0;
		} else if (source.getDamageType() == DamageType.ARROW && source.getDirectEntity() != null) {
			killedByFire = source.getDirectEntity().getFireTicks() > 0;
		}

		if (!killedByFire && entity.getEntity().getFireTicks() <= 0)
			return;

		List<ItemStack> rottenFlesh = new ArrayList<>();
		for (ItemStack drop : entity.getDrops())
			if (drop.getType() == Material.ROTTEN_FLESH)
				rottenFlesh.add(drop);

		for (ItemStack d : rottenFlesh) {
			entity.getDrops().add(new ItemStack(Material.LEATHER, d.getAmount()));
			entity.getDrops().remove(d);
		}
	}
}
