package pyb.portfolio.urlshortener.api.v1;

import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pyb.portfolio.urlshortener.data.ChecksumService;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
	/**
	 * Less than 10 characters and more robust than Adler-32.
	 */
	private static final Checksum SHORT_HASH = new CRC32();

	@Autowired
	private ShortUrlRepository shortUrlRepository;

	@Override
	public void save(ShortUrl shortUrl) {
		shortUrlRepository.save(shortUrl);
	}

	@Override
	public ShortUrl getByShortCode(String shortCode) {
		return shortUrlRepository.findOne(shortCode);
	}

	@Autowired
	private ChecksumService checksumService;

	/**
	 * Returns 8 characters long code for a given URL.
	 *
	 * @param url
	 * @return Alphanumeric code. The code will be the same for two identical URLs.
	 */
	@Override
	public String shortCode(URL url) {
		final byte[] data = url.toString().getBytes();
		return checksumService.checksum(SHORT_HASH, data);
	}

	@Override
	public String longUrl(String shortCode) throws UnknownShortCodeException {
		final ShortUrl shortUrl = shortUrlRepository.findOne(shortCode);
		if (shortUrl == null) {
			throw new UnknownShortCodeException(shortCode);
		}
		return shortUrl.getLongUrl();
	}

}
