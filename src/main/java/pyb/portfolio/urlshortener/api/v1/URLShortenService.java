package pyb.portfolio.urlshortener.api.v1;

import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.springframework.stereotype.Service;

@Service
public class URLShortenService {

	// TODO: try https://en.wikipedia.org/wiki/Adler-32, CRC32C
	private static final Checksum SHORT_HASH = new CRC32();

	/**
	 * Returns 8 characters long code for a given URL.
	 *
	 * @param url
	 * @return Alphanumeric code. The code will be the same for two identical URLs.
	 */
	public String shortCode(URL url) {
		final byte[] data = url.toString().getBytes();
		return checksum(data);
	}

	public String checksum(byte[] data) {
		SHORT_HASH.reset();
		SHORT_HASH.update(data, 0, data.length);
		final long hash = SHORT_HASH.getValue();
		return Long.toHexString(hash);
	}
}
