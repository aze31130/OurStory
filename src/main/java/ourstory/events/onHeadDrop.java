package ourstory.events;

import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class onHeadDrop implements Listener {

	private final Map<EntityType, Material> targetedEntity = Map.of(
			EntityType.ZOMBIE, Material.ZOMBIE_HEAD,
			EntityType.CREEPER, Material.CREEPER_HEAD,
			EntityType.SKELETON, Material.SKELETON_SKULL,
			EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SKULL,
			EntityType.PIGLIN, Material.PIGLIN_HEAD);

	@EventHandler
	public void onMonsterHeadDrop(EntityDeathEvent entity) {
		EntityDamageEvent damageEvent = entity.getEntity().getLastDamageCause();

		if (!(damageEvent instanceof EntityDamageByEntityEvent))
			return;

		Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();

		if (!(damager instanceof Creeper))
			return;

		Creeper killer = (Creeper) damager;

		if (!killer.isPowered())
			return;

		if (!targetedEntity.containsKey(entity.getEntityType()))
			return;

		ItemStack head = new ItemStack(targetedEntity.get(entity.getEntityType()));

		Optional<ItemStack> potentialDrop = entity.getDrops().stream().filter(drop -> drop.getType() == head.getType()).findFirst();

		if (potentialDrop.isEmpty())
			entity.getDrops().add(head);
	}
}
