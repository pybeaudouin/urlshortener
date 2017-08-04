package pyb.portfolio.urlshortener.data;

import java.util.Arrays;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChecksumServiceTest {
	private static final Checksum CRC32 = new CRC32();
	private static final Checksum ADLER32 = new Adler32();

	@Autowired
	private ChecksumService srv;

	@Test
	public void testAdler32Wikipedia() {
		final String alphabet = "Wikipedia";
		final String actual = srv.checksum(ADLER32, alphabet.getBytes());
		Assert.assertEquals("11e60398", actual);
	}

	@Test
	public void testCRC32Alphabet() {
		final String alphabet = "The quick brown fox jumps over the lazy dog";
		final String actual = srv.checksum(CRC32, alphabet.getBytes());
		Assert.assertEquals("414fa339", actual);
	}

	@Test
	public void testCRC32Zeroes() {
		final byte[] zeroes = new byte[32];
		Arrays.fill(zeroes, (byte) 0);
		final String actual = srv.checksum(CRC32, zeroes);
		Assert.assertEquals("190a55ad", actual);
		// CRC32C Assert.assertEquals("aa36918a", actual);
	}

	@Test
	public void testCRC32Ones() {
		final byte[] zeroes = new byte[32];
		Arrays.fill(zeroes, (byte) 1);
		final String actual = srv.checksum(CRC32, zeroes);
		Assert.assertEquals("62319fcc", actual);
		// CRC32C Assert.assertEquals("43aba862", actual);
	}

	@Test
	public void testCRC32OneToNine() {
		final String oneToNine = "123456789";
		final String actual = srv.checksum(CRC32, oneToNine.getBytes());
		Assert.assertEquals("cbf43926", actual);
		// CRC32C Assert.assertEquals("e3069283", actual);
	}

	@Test
	public void testCRC32EICAR() {
		final String eicar = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
		final String actual = srv.checksum(CRC32, eicar.getBytes());
		Assert.assertEquals("6851cf3c", actual);
	}
}
