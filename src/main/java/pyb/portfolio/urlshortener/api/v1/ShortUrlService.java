package pyb.portfolio.urlshortener.api.v1;

import java.net.URL;

public interface ShortUrlService {
	/**
	 * Generates a short code from a {@link URL}.
	 *
	 * @param URL
	 *            URL to shorten
	 * @return short code for the URL (not the short URL)
	 */
	String shortCode(URL url);

	/**
	 * Retrieves the original (long) URL from its {@link ShortUrl#getShortCode()
	 * short code}.
	 *
	 * @param {@link
	 * 			ShortUrl#getShortCode() short code}
	 * @return String representation of the original (long) URL.
	 */
	String longUrl(String shortCode) throws UnknownShortCodeException;

	/** Persists a {@link ShortUrl}. */
	void save(ShortUrl shortUrl);

	/**
	 * Retrieves a {@link ShortUrl} by its {@link ShortUrl#getShortCode() short
	 * code}.
	 * 
	 * @return
	 */
	ShortUrl getByShortCode(String shortCode);
}
