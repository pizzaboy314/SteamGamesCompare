import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Worker {
	
	public static ArrayList<String> user1games;
	public static ArrayList<String> user2games;
	public static ArrayList<String> intersection;

	public static void main(String[] args) {
		user1games = new ArrayList<String>();
		user2games = new ArrayList<String>();
		intersection = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String userName1 = null, userName2 = null;
		
		try {
			System.out.println("enter username1: ");
			userName1 = br.readLine();
			System.out.println("enter username2: ");
			userName2 = br.readLine();
		} catch (IOException ioe) {
			System.exit(1);
		}
		System.out.println();
		System.out.println();


		// System.out.println(userName1 + " " + userName2);
		String comURL = "http://steamcommunity.com/id/";
		String endURL = "/games/?tab=all&sort=name";

		String user1URL = comURL + userName1 + endURL;
		String user2URL = comURL + userName2 + endURL;

		user1games = parseHTML(user1URL);
		user2games = parseHTML(user2URL);

		intersection.addAll(user1games);
		intersection.retainAll(user2games);

		for (String s : intersection) {
			System.out.println(s);
		}

		System.out.println();

	}

	public static ArrayList<String> parseHTML(String input) {
		ArrayList<String> games = new ArrayList<String>();
		boolean grabJPG = false;
		String url = input;

		while (url != null) {
			String nextURL = null;
			try {
				URL source = null;
				boolean valid = true;
				try {
					source = new URL(url);
				} catch (MalformedURLException e) {
					valid = false;
				}
				while (valid == false) {
					valid = true;
					url = (String) JOptionPane.showInputDialog(null,
							"Malformed URL format. Are you sure you copied the entire URL?\n" + "Try again:", "Provide URL",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					try {
						source = new URL(url);
					} catch (MalformedURLException e) {
						valid = false;
					}
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));

				String inputLine = in.readLine();
				while (inputLine != null) {
					// System.out.println(inputLine);
					String[] arr;
					if (inputLine.contains("rgGames")) {
						arr = inputLine.split("name");
						for (String line : arr) {
							if (!line.contains("var rgGames") && line.contains("logo")) {
								String game = line.substring(3);
								game = game.substring(0, game.indexOf("\",\"logo\":"));
								game = game.replace("\\u00ae", "");
								game = game.replace("\\u221e", "");

								games.add(game);

								// System.out.println(game);
							}
						}
						// System.out.println();
					}
					inputLine = in.readLine();
				}

				in.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			url = nextURL;
		}

		return games;
	}

}
