package ourstory.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONTokener;
import ourstory.Main;
import ourstory.guilds.Guild;
import org.json.JSONArray;

public class FileUtils {
	/*
	 * This private constructor hides the implicit public one
	 */
	private FileUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Guild> loadGuilds(String guildFile) {
		List<Guild> guilds = new ArrayList<>();

		JSONArray array = loadJsonArray(guildFile);

		for (int i = 0; i < array.length(); i++)
			guilds.add(new Guild(array.getJSONObject(i)));

		return guilds;
	}

	public static JSONArray loadJsonArray(String filename) {
		if (!filename.endsWith(".json"))
			filename.concat(".json");

		String rawContent = FileUtils.readRawFile(filename);
		JSONTokener jsonParser = new JSONTokener(rawContent);
		JSONArray json = new JSONArray(jsonParser);

		return json;
	}

	public static JSONObject loadJsonObject(String filename) {
		if (!filename.endsWith(".json"))
			filename.concat(".json");

		String rawContent = FileUtils.readRawFile(filename);
		JSONTokener jsonParser = new JSONTokener(rawContent);
		JSONObject json = new JSONObject(jsonParser);

		return json;
	}

	public static String readRawFile(String filename) {
		if (!Main.configDir.exists())
			Main.configDir.mkdir();

		File f = new File(Main.configDir, filename);
		try {
			return new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Cannot open file " + filename + "!");
		}
		return new String();
	}

	public static void writeRawFile(String filename, String content) {
		if (!Main.configDir.exists())
			Main.configDir.mkdir();

		File f = new File(Main.configDir, filename);

		try {
			FileWriter writer = new FileWriter(f.getAbsolutePath());
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Cannot write to file " + filename + " !");
		}
	}
}
