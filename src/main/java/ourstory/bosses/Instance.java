package ourstory.bosses;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ourstory.Main;

public class Instance {
	public Boss boss;
	public Map<Player, Integer> players;
	public LocalDateTime start;
	public LocalDateTime limit;
	public int maxRespawn;
	public World arena;

	private BukkitTask timer;

	private static final List<Integer> TIMER_WARNINGS = List.of(15, 10, 5, 3, 2, 1);

	public Instance(Boss boss, List<Player> players, int durationMinutes, int maxRespawn, String arena) {
		this.boss = boss;
		this.start = LocalDateTime.now();
		this.limit = this.start.plusMinutes(durationMinutes);
		this.maxRespawn = maxRespawn;
		this.arena = Bukkit.getWorld(arena);

		this.players = new HashMap<>();

		if (this.arena == null) {
			Bukkit.getLogger().warning("Boss instance arena world '" + arena + "' does not exist; instance disabled.");
			return;
		}

		for (Player p : players) {
			this.players.put(p, 0);
			p.teleport(this.arena.getSpawnLocation());
		}

		timer = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
			LocalDateTime now = LocalDateTime.now();
			int minutesLeft = (int) Duration.between(now, this.limit).toMinutes();

			if (TIMER_WARNINGS.contains(minutesLeft))
				for (Player p : this.players.keySet())
					p.sendMessage(minutesLeft + " minutes left to defeat " + this.boss.name);

			if (minutesLeft <= 0)
				fail();
		}, 0, 20 * 60);
	}

	public void fail() {
		if (this.timer != null)
			this.timer.cancel();
		this.boss.entity.remove();

		World spawn = Bukkit.getWorld("world");
		for (Player p : this.players.keySet()) {
			p.sendMessage("You failed to defeat " + this.boss.name + " in time. Warping you back...");
			if (spawn != null)
				p.teleport(spawn.getSpawnLocation());
		}
	}

	public void finish() {
		if (this.timer != null)
			this.timer.cancel();
		this.boss.entity.remove();
	}
}
