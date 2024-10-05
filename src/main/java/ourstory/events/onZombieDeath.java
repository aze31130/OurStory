package ourstory.events;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class onZombieDeath implements Listener {
	/*
	 * If a zombie if killed by fire, makes him drop leather instead of rotten flesh
	 */
	@EventHandler
	public void zombieDeath(EntityDeathEvent entity) {
		if (entity.getEntityType() != EntityType.ZOMBIE)
			return;

		if (entity.getEntity().getFireTicks() > 0) {

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
