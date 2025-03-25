package ourstory.guilds;

import java.util.UUID;
import org.json.JSONObject;

public class Guild {
	public UUID id;
	public String name;
	public long bank;
	// public List<UUID> members; TODO

	public Guild(UUID id, String name, long bank) {
		this.id = id;
		this.name = name;
		this.bank = bank;
	}

	public Guild(JSONObject obj) {
		this(UUID.fromString(obj.getString("uuid")),
				obj.getString("name"),
				obj.getLong("bank"));
	}

	public JSONObject toJson() {
		JSONObject guild = new JSONObject()
				.put("id", id)
				.put("name", name)
				.put("bank", bank);

		return guild;
	}
}
