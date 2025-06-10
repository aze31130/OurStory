package ourstory.bosses;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Instance {
	public Boss boss;
	public Map<Player, Integer> players;
	public LocalDateTime start;
	public LocalDateTime limit;
	public int maxRespawn;
	public World arena;

	private BukkitTask timer;

	private static final List<Integer> timerWarnings = List.of(15, 10, 5, 3, 2, 1);
	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");


	public Instance(Boss boss, List<Player> players, int durationMinutes, int maxRespawn, String arena) {
		this.boss = boss;
		this.start = LocalDateTime.now();
		this.limit = this.start.plusMinutes(durationMinutes);
		this.maxRespawn = maxRespawn;
		this.arena = Bukkit.getWorld(arena);

		this.players = new HashMap<>();

		// Init damage hashmap and warp player to arena
		for (Player p : players) {
			this.players.put(p, 0);
			p.teleport(this.arena.getSpawnLocation());
		}

		// Exec reminder task every 60 seconds
		timer = Bukkit.getScheduler().runTaskTimer(p, () -> {
			LocalDateTime now = LocalDateTime.now();
			int minutesLeft = (int) java.time.Duration.between(now, this.limit).toMinutes();

			if (timerWarnings.contains(minutesLeft))
				for (Player p : this.players.keySet())
					p.sendMessage(minutesLeft + " minutes lef to defeat " + this.boss.name);

			if (minutesLeft <= 0) {
				fail();
			}
		}, 0, 20 * 60);
	}

	public void fail() {
		this.timer.cancel();
		this.boss.entity.remove();
		this.boss.skills.interrupt();

		World spawn = Bukkit.getWorld("world");

		for (Player p : this.players.keySet()) {
			p.sendMessage("You failed to defeat" + this.boss.name + " in time. Warping you back...");
			p.teleport(spawn.getSpawnLocation());
		}
	}

	public void finish() {
		this.timer.cancel();
		this.boss.entity.remove();
		this.boss.skills.interrupt();
	}
}
