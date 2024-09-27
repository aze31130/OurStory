package ourstory.commands;

import java.util.List;
import java.util.Optional;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.Main;
import org.bukkit.entity.Player;

public class RankUp implements BasicCommand {
	private Main plugin;

	public RankUp(Main plugin) {
		this.plugin = plugin;
	}

	record RankPrice(String from_rank, String to_rank, Double price) {
	}

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		var executor = sender.getExecutor();
		if (!(executor instanceof Player)) {
			sender.getSender().sendMessage("Command executed by non-player.");
			return;
		}
		var player = (Player) (executor);
		var maybe_rank_price = this.nextRankPriceFor(player);
		if (maybe_rank_price.isEmpty()) {
			sender.getSender().sendMessage("There are no ranks above yours.");
			return;
		}
		var rank_price = maybe_rank_price.get();
		if (!this.playerHasPrice(player, rank_price)) {
			sender.getSender().sendMessage("Can't rank up, ranking up to " + rank_price.to_rank + " costs " + rank_price.price.toString() + "$.");
			return;
		}
		this.takePrice(player, rank_price);
		this.upgradeRank(player, rank_price);
		sender.getSender().sendMessage("You have ranked up to " + rank_price.to_rank + " for " + rank_price.price.toString() + "$.");
	}

	private Optional<RankPrice> nextRankPriceFor(Player player) {
		for (RankPrice price : rankPrices) {
			var player_in_base_rank = plugin.permissions.playerInGroup(player, price.from_rank);
			if (player_in_base_rank)
				return Optional.of(price);
		}
		return Optional.empty();
	}

	private boolean playerHasPrice(Player player, RankPrice rank_price) {
		var player_balance = plugin.economy.getBalance(player);
		return player_balance >= rank_price.price;
	}

	private static final List<RankPrice> rankPrices = List.of(
			new RankPrice("member", "ecuyer", 1000.),
			new RankPrice("ecuyer", "comte", 1000.),
			new RankPrice("comte", "prince", 1000.),
			new RankPrice("prince", "roi", 1000.),
			new RankPrice("roi", "empereur", 1000.));

	private void takePrice(Player player, RankPrice rank_price) {
		plugin.economy.withdrawPlayer(player, rank_price.price);
	}

	private void upgradeRank(Player player, RankPrice rank_price) {
		plugin.permissions.playerAddGroup("default", player, rank_price.to_rank);
		plugin.permissions.playerRemoveGroup("default", player, rank_price.from_rank);
	}
}
