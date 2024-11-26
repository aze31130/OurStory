package ourstory.events;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.utils.EnchantItem;

public class onDummyHit implements Listener {

	private final Map<String, Enchantment> EnchantmentsSensitivity = Map.of(
			"sensitive_smite", Enchantment.SMITE,
			"sensitive_boa", Enchantment.BANE_OF_ARTHROPODS,
			"sensitive_impaling", Enchantment.IMPALING);

	@EventHandler
	public void dummyHit(EntityDamageByEntityEvent event) {
		// Cancel if not player
		if (!(event.getDamager() instanceof Player))
			return;

		// Cancel if not dummy
		if (event.getEntity().getMetadata("isDummy").isEmpty())
			return;

		event.setCancelled(true);

		double base_damage = event.getDamage();
		ItemStack itemInHand = ((Player) event.getDamager()).getInventory().getItemInMainHand();

		// Ajout d'un moyen de suppression d'un dummy
		if (itemInHand.getType().equals(Material.COD) && event.getDamager().isOp())
			event.getEntity().remove();

		// Ajout des dégâts pour chaque enchants présent sur le dummy et la meta data
		for (String str : EnchantmentsSensitivity.keySet()) {
			if (!event.getEntity().getMetadata(str).isEmpty())
				base_damage += 2.5 * (itemInHand.containsEnchantment(EnchantmentsSensitivity.get(str)) ? itemInHand.getEnchantmentLevel(EnchantmentsSensitivity.get(str)) : 0);
		}

		// Gestion de final_damage
		base_damage *= 1 + (0.05 * EnchantItem.getEnchantAmount(itemInHand, "final_damage"));

		Player p = (Player) event.getDamager();
		p.sendMessage(Component.text(event.getEntity().getCustomName() + " : ").append(Component.text(String.format("%.2f", base_damage)).color(NamedTextColor.RED)));
	}
}
