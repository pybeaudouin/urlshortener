package pyb.portfolio.urlshortener.data;

import java.util.zip.Checksum;

import org.springframework.stereotype.Service;

/**
 * Utility methods for checksum calculation.
 */
@Service
public class ChecksumService {
	public String checksum(Checksum checksumGenerator, byte[] data) {
		checksumGenerator.reset();
		checksumGenerator.update(data, 0, data.length);
		final long hash = checksumGenerator.getValue();
		return Long.toHexString(hash);
	}
}
