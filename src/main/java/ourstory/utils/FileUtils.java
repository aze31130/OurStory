package ourstory.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ourstory.Main;
import ourstory.guilds.Guild;

public final class FileUtils {
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
		String rawContent = readRawFile(filename);
		if (rawContent.isEmpty())
			return new JSONArray();
		return new JSONArray(new JSONTokener(rawContent));
	}

	public static JSONObject loadJsonObject(String filename) {
		String rawContent = readRawFile(filename);
		if (rawContent.isEmpty())
			return new JSONObject();
		return new JSONObject(new JSONTokener(rawContent));
	}

	public static String readRawFile(String filename) {
		File f = resolveInsideConfigDir(filename);
		if (f == null || !f.isFile())
			return "";
		try {
			return Files.readString(f.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "Cannot read " + filename, e);
			return "";
		}
	}

	public static void writeRawFile(String filename, String content) {
		File f = resolveInsideConfigDir(filename);
		if (f == null)
			return;
		try {
			Files.createDirectories(f.getParentFile().toPath());
			Files.writeString(f.toPath(), content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "Cannot write " + filename, e);
		}
	}

	/*
	 * Resolves a filename inside Main.configDir, rejecting any path that would escape
	 * the directory (e.g. "../etc/passwd" or absolute paths). Returns null on rejection.
	 */
	private static File resolveInsideConfigDir(String filename) {
		File configDir = Main.configDir;
		if (configDir == null)
			return null;
		Path base = configDir.toPath().toAbsolutePath().normalize();
		Path resolved = base.resolve(filename).toAbsolutePath().normalize();
		if (!resolved.startsWith(base)) {
			Bukkit.getLogger().warning("Rejected path outside configDir: " + filename);
			return null;
		}
		return resolved.toFile();
	}
}
