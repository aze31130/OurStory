package ourstory.storage;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import ourstory.bosses.Boss;

public class BossInstance {
	public Boss monster;
	public List<Player> players;
	public Map<Player, Double> damage;

	public Date entered;
	public Date finished;

	public List<Integer> timerWarnings = List.of(20, 10, 5, 3, 2, 1);

	public Boolean isFinished = false;

	public BossInstance(Boss boss, List<Player> players, int duration) {
		this.monster = boss;
		this.players = players;

		// Init hashmap
		this.damage = new HashMap<>();
		for (Player p : players)
			damage.put(p, 0D);


		this.entered = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, duration);
		this.finished = c.getTime();
	}
}
