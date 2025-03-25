package ourstory.guilds;

import java.util.List;
import java.util.UUID;
import org.json.JSONObject;

public class Guild {
	public int id;
	public String name;
	public long money;
	public List<UUID> members;

	public Guild(int id, String name, long money) {
		this.id = id;
		this.name = name;
		this.money = money;
	}

	public JSONObject toJson() {
		JSONObject guild = new JSONObject()
				.put("id", id)
				.put("name", name)
				.put("money", money);

		return guild;
	}
}
