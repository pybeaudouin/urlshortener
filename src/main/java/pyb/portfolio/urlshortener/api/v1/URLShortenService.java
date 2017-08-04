package pyb.portfolio.urlshortener.api.v1;

import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pyb.portfolio.urlshortener.data.ChecksumService;

@Service
public class URLShortenService {

	// TODO: try https://en.wikipedia.org/wiki/Adler-32, CRC32C
	private static final Checksum SHORT_HASH = new CRC32();
	@Autowired
	private ChecksumService checksumService;

	/**
	 * Returns 8 characters long code for a given URL.
	 *
	 * @param url
	 * @return Alphanumeric code. The code will be the same for two identical URLs.
	 */
	public String shortCode(URL url) {
		final byte[] data = url.toString().getBytes();
		return checksumService.checksum(SHORT_HASH, data);
	}
}
