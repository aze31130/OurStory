package ourstory.storage;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import ourstory.bosses.Boss;

public class Storage {
	private static volatile Storage instance = null;

	private Storage() {}

	public final static Storage getInstance() {
		if (Storage.instance == null) {
			synchronized (Storage.class) {
				if (Storage.instance == null)
					Storage.instance = new Storage();
			}
		}
		return Storage.instance;
	}

	// List of active bossbar
	public HashMap<UUID, BossBar> healthBarHashMap = new HashMap<UUID, BossBar>();
	public HashMap<UUID, BukkitRunnable> healthBarRunnables = new HashMap<UUID, BukkitRunnable>();



	// Running Boss instance
	public Boss bossInstance;

	// Party list
	// public List<List<UUID>> partyList = new ArrayList<>();
}
