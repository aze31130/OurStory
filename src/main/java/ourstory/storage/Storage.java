package ourstory.storage;

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

	// Running Boss instances
}
