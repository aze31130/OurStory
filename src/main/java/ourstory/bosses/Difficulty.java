package ourstory.bosses;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
	EASY, NORMAL, HARD, CHAOS;

	public static List<String> getNames() {
		List<String> difficulties = new ArrayList<>();
		for (Difficulty d : Difficulty.values())
			difficulties.add(d.name());
		return difficulties;
	}
}
