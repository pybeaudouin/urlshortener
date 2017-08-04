package pyb.portfolio.urlshortener.shorturl;

public interface ShortUrlService {
	/** Persist a {@link ShortUrl} */
	public void save(ShortUrl shortUrl);

	/** Get a {@link ShortUrl} by its short code */
	public void getByShortCode(String shortCode);
}
