package pyb.portfolio.urlshortener.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class URLShortenServiceTest {

	@Autowired
	private URLShortenService srv;

	@Test
	public void testShorten() throws MalformedURLException {
		final URL httpsGithub = new URL("https://github.com");
		final String actual = srv.shortCode(httpsGithub);
		Assert.assertEquals("69608d28", actual);
	}

	@Test
	public void testChecksum() {
		final String alphabet = "The quick brown fox jumps over the lazy dog";
		final String actual = srv.checksum(alphabet.getBytes());
		Assert.assertEquals("414fa339", actual);
	}

	@Test
	public void testChecksumZeroes() {
		final byte[] zeroes = new byte[32];
		Arrays.fill(zeroes, (byte) 0);
		final String actual = srv.checksum(zeroes);
		Assert.assertEquals("190a55ad", actual);
		// CRC32C Assert.assertEquals("aa36918a", actual);
	}

	@Test
	public void testChecksumOnes() {
		final byte[] zeroes = new byte[32];
		Arrays.fill(zeroes, (byte) 1);
		final String actual = srv.checksum(zeroes);
		Assert.assertEquals("62319fcc", actual);
		// CRC32C Assert.assertEquals("43aba862", actual);
	}

	@Test
	public void testChecksumOneToNine() {
		final String oneToNine = "123456789";
		final String actual = srv.checksum(oneToNine.getBytes());
		Assert.assertEquals("cbf43926", actual);
		// CRC32C Assert.assertEquals("e3069283", actual);
	}

	@Test
	public void testChecksumEICAR() {
		final String eicar = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
		final String actual = srv.checksum(eicar.getBytes());
		Assert.assertEquals("6851cf3c", actual);
	}

}
