package ourstory.commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import ourstory.utils.EnchantItem;
import ourstory.utils.Permissions;

public class Reset implements BasicCommand {

	public Economy economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.reset"))
			return;

		Entity executor = sender.getExecutor();

		if (!(executor instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player player = (Player) (executor);

		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta itemMeta = item.getItemMeta();

		if (!(itemMeta instanceof Repairable)) {
			sender.getSender().sendMessage(Component.text("You cannot reset that item !").color(NamedTextColor.RED));
			return;
		}

		Repairable r = (Repairable) itemMeta;
		long cost = (r.getRepairCost() * 100) + (EnchantItem.getEnchantLevelQuantity(item) * 2000);

		if (cost > 1000000)
			cost = 1000000;

		if (cost == 0) {
			player.sendMessage(Component.text("You cannot reset that item !").color(NamedTextColor.RED));
			return;
		}

		if (args.length == 0 || !args[0].equalsIgnoreCase("confirm")) {
			player.sendMessage(Component.text("You are about to reset your item for " + cost + " money !").color(NamedTextColor.YELLOW));
			player.sendMessage(Component.text("Use /reset confirm to perform the action !").color(NamedTextColor.YELLOW));
			return;
		}

		// Ensures player has enough money
		if (this.economy.getBalance(player) < cost) {
			player.sendMessage(Component.text("Error, you need " + cost + "$ to reset your item !").color(NamedTextColor.RED));
			return;
		}

		// Removes the price from account
		this.economy.withdrawPlayer(player, cost);
		r.setRepairCost(0);
		item.setItemMeta(itemMeta);
		player.sendMessage(Component.text("Your item has been reseted !").color(NamedTextColor.GREEN));
	}

	/*
	 * /reset confirm
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return List.of("confirm");
	}
}
