package pyb.portfolio.urlshortener;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pyb.portfolio.urlshortener.api.v1.URLShortenService;

@RestController
@Slf4j
public class URLController {

	@Autowired
	private URLShortenService urlSrv;

	@RequestMapping(value = "/1", method = RequestMethod.POST)
	public ResponseEntity<String> shorten(@RequestParam("url") String urlStr) {
		log.info("Shortening URL " + urlStr);
		URL url;
		try {
			url = new URL(urlStr);
		} catch (final MalformedURLException e) {
			String error = String.format("Invalid URL: [%s]", urlStr);
			log.error(error, e);
			// SECURITY: don't print the input to avoid Cross-site request forgery
			return new ResponseEntity<String>("Invalid URL", HttpStatus.BAD_REQUEST);
		}
		final String shortCode = urlSrv.shortCode(url);
		log.info(String.format("The URL '%s' can be shorten to: %s", urlStr, shortCode));
		return new ResponseEntity<String>(shortCode, HttpStatus.OK);
	}
}
