package pyb.portfolio.urlshortener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import pyb.portfolio.urlshortener.api.v1.URLShortenService;
import pyb.portfolio.urlshortener.shorturl.ShortUrl;
import pyb.portfolio.urlshortener.shorturl.ShortUrlService;

@RestController
@Slf4j
public class URLController {

	public static final short API_VERSION = 1;

	@Autowired
	private URLShortenService urlSrv;

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
		final String shortCode = urlSrv.shortCode(url);

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
}
