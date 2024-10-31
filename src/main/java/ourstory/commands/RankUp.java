package ourstory.commands;

import java.util.List;
import java.util.Optional;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RankUp implements BasicCommand {

	public Permission permissions = Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider();
	public Economy economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

	private record RankPrice(String srcRank, String dstRank, Double price) {
	}

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		Entity executor = sender.getExecutor();

		if (!(executor instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player player = (Player) (executor);
		Optional<RankPrice> nextRank = this.getNextRank(player);

		if (nextRank.isEmpty()) {
			sender.getSender().sendMessage(Component.text("You already are at the highest possible rank !").color(NamedTextColor.YELLOW));
			return;
		}

		RankPrice rankUp = nextRank.get();

		// Ensures player has enough money
		if (this.economy.getBalance(player) < rankUp.price) {
			sender.getSender().sendMessage(Component.text("Error, you need " + rankUp.price + "$ to become a " + rankUp.dstRank + " !").color(NamedTextColor.RED));
			return;
		}

		// Removes the price from account
		this.economy.withdrawPlayer(player, rankUp.price);

		/*
		 * Upgrade player rank. The first null argument means the group is applied to all availables worlds.
		 */
		this.permissions.playerAddGroup(null, player, rankUp.dstRank);
		this.permissions.playerRemoveGroup(null, player, rankUp.srcRank);

		sender.getSender().sendMessage(Component.text("You have ranked up to " + rankUp.dstRank + " for " + rankUp.price.toString() + "$. Congratulation !").color(NamedTextColor.GREEN));
	}

	private Optional<RankPrice> getNextRank(Player player) {
		List<RankPrice> rankPrices = List.of(
				new RankPrice("member", "ecuyer", 25000.),
				new RankPrice("ecuyer", "comte", 50000.),
				new RankPrice("comte", "prince", 100000.),
				new RankPrice("prince", "roi", 200000.),
				new RankPrice("roi", "empereur", 300000.));

		for (RankPrice price : rankPrices)
			if (this.permissions.playerInGroup(player, price.srcRank))
				return Optional.of(price);

		return Optional.empty();
	}
}
