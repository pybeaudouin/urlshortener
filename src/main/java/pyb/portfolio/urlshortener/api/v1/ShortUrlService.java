package pyb.portfolio.urlshortener.api.v1;

import java.net.URL;

public interface ShortUrlService {
	/** Generate a short code from a {@link URL} */
	String shortCode(URL url);

	/** Persist a {@link ShortUrl} */
	void save(ShortUrl shortUrl);

	/** Get a {@link ShortUrl} by its short code */
	void getByShortCode(String shortCode);
}
