package pyb.portfolio.urlshortener.api.v1;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShortUrlController {

	public static final short API_VERSION = 1;

	@Autowired
	private ShortUrlService shortUrlService;

	@RequestMapping(value = "/" + API_VERSION, method = RequestMethod.POST)
	public ResponseEntity<String> shorten(@RequestParam("url") String urlStr) {
		log.info("Shortening URL " + urlStr);
		URL url;
		try {
			url = new URL(urlStr);
		} catch (final MalformedURLException e) {
			final String error = String.format("Invalid URL: [%s]", urlStr);
			log.error(error, e);
			// SECURITY: don't print the input to avoid Cross-site request forgery
			return new ResponseEntity<>("Invalid URL", HttpStatus.BAD_REQUEST);
		}
		final String shortCode = shortUrlService.shortCode(url);

		URL shortURL = null;
		try {
			shortURL = buildShortURL(shortCode);
		} catch (final MalformedURLException e) {
			log.error(String.format(
					"The URL '%s' can be shorten to the short code %s but there was an error while generating the short URL.",
					urlStr, shortCode), e);
		}

		final String shortUrlStr = shortURL.toExternalForm();
		log.info(String.format("The URL '%s' can be shorten to: %s", urlStr, shortUrlStr));

		final ShortUrl shortUrl = new ShortUrl(shortCode, urlStr, API_VERSION);
		shortUrlService.save(shortUrl);

		return new ResponseEntity<>(shortUrlStr, HttpStatus.OK);
	}

	private URL buildShortURL(String shortCode) throws MalformedURLException {
		final UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
		builder.path("/" + API_VERSION);
		builder.pathSegment("{shortCode}");
		final URI newUri = builder.buildAndExpand(shortCode).toUri();
		return newUri.toURL();
	}

	@RequestMapping(value = "/" + API_VERSION + "/{shortCode}", method = RequestMethod.GET)
	public ResponseEntity<String> expand(@PathVariable("shortCode") String shortCode) {
		log.info("Expanding URL " + shortCode);
		String longUrl = null;
		try {
			longUrl = shortUrlService.longUrl(shortCode);
		} catch (final UnknownShortCodeException e) {
			final String error = String.format("Unknown short code: [%s]", shortCode);
			log.error(error, e);
			// SECURITY: don't print the input to avoid Cross-site request forgery
			return new ResponseEntity<>("Short code not found", HttpStatus.NOT_FOUND);
		}

		log.info(String.format("The short code '%s' belongs to the URL '%s'", shortCode, longUrl));
		return new ResponseEntity<>(longUrl, HttpStatus.OK);
	}
}
