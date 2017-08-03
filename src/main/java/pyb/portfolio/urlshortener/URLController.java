package pyb.portfolio.urlshortener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class URLController {

	private final Scanner SCANNER = new Scanner(System.in);
	private final Map<Character, MenuItem> menu = new LinkedHashMap<>();

	public URLController() {
		menu.put('S', new MenuItem('S', "Shorten a URL", () -> shorten()));
		menu.put('E', new MenuItem('E', "Expand a URL", () -> expand()));
		menu.put('Q', new MenuItem('Q', "Quit", () -> quit()));
	}

	private void shorten() {
		log.info("Shortening URLs");
	}

	private void expand() {
		log.info("Expanding URLs");
	}

	private void quit() {
		System.out.println("Thank you for your service!");
		SCANNER.close();
		System.exit(0);
	}

	void loop() {

		while (true) {
			for(final MenuItem it: menu.values()) {
				System.out.println(it);
			}
			final String input = SCANNER.next();

			System.out.println(String.format("You typed [%s]", input));
			if (input.length() != 1) {
				System.err.println("Please enter a valid choice.");
				continue;
			}

			final char choice = Character.toUpperCase(input.charAt(0));
			if (!menu.containsKey(choice)) {
				System.err.println("Please enter a valid choice.");
			} else {
				menu.get(choice).run();
			}
		}
	}

	@Data
	private static class MenuItem {
		private final char key;
		private final String label;
		private final Runnable command;

		public void run() {
			command.run();
		}

		@Override
		public String toString() {
			return key + ") " + label;
		}
	}

}
