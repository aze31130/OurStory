package ourstory.bosses;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
	EASY(1), NORMAL(2), HARD(3), CHAOS(4);

	public int level;

	private Difficulty(int level) {
		this.level = level;
	}

	public static List<String> getNames() {
		List<String> difficulties = new ArrayList<>();
		for (Difficulty d : Difficulty.values())
			difficulties.add(d.name());
		return difficulties;
	}
}
