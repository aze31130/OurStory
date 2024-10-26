package ourstory.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import ourstory.Main;
import ourstory.Main.CustomSkin;

public class Skin implements BasicCommand {

	public Permission permissions = Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider();
	public Economy economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		Entity executor = sender.getExecutor();

		if (!(executor instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player player = (Player) (executor);
		ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);

		if (item == null) {
			sender.getSender().sendMessage("You need to hold an item to apply a skin");
			return;
		}

		if (args.length == 0) {
			sender.getSender().sendMessage("You need to provide a valid skin name");
			return;
		}

		// Get skin id
		Optional<CustomSkin> customSkinSearch = Main.skins.stream().filter(i -> i.name().equalsIgnoreCase(args[0])).findFirst();

		if (customSkinSearch.isEmpty()) {
			sender.getSender().sendMessage("Unknown skin !");
			return;
		}

		CustomSkin customSkin = customSkinSearch.get();
		ItemMeta meta = item.getItemMeta();

		if (meta == null) {
			sender.getSender().sendMessage("Error while getting item meta.");
			return;
		}

		meta.setCustomModelData(customSkin.id());
		item.setItemMeta(meta);
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		List<String> suggestions = new ArrayList<>();

		if (args.length > 1)
			return suggestions;

		for (CustomSkin cs : Main.skins)
			if (args.length == 0 || (args.length > 0 && cs.name().toLowerCase().startsWith(args[0].toLowerCase())))
				suggestions.add(cs.name());

		return suggestions;
	}
}
