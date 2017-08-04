package pyb.portfolio.urlshortener.api.v1;

import java.net.MalformedURLException;
import java.net.URL;

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

}
