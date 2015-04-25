

public class SteamGame implements Comparable<SteamGame> {
	public String name;

	public SteamGame(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(SteamGame s) {
		return this.name.compareTo(s.getName());
	}

}
