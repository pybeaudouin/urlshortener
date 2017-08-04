package pyb.portfolio.urlshortener;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void shorten(@RequestParam("url") String urlStr) {
		log.info("Shortening URL " + urlStr);
		URL url;
		try {
			url = new URL(urlStr);
		} catch (final MalformedURLException e) {
			log.error(String.format("'%s' isn't a valid URL: %s", urlStr, e.getMessage()));
			return;
		}
		final String shortCode = urlSrv.shortCode(url);
		log.info(String.format("The URL '%s' can be shorten to: %s", urlStr, shortCode));
	}
}
