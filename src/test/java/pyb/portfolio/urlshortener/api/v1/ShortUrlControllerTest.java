package pyb.portfolio.urlshortener.api.v1;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(ShortUrlController.class)
public class ShortUrlControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ShortUrlService shortUrlService;

	@Test
	public void testShorten() throws Exception {
		// Prepare
		final String shortCode = "DEMO";
		final URL longUrl = new URL("https://the/long/url");
		final String longUrlStr = longUrl.toExternalForm();
		given(this.shortUrlService.shortCode(longUrl)).willReturn(shortCode);

		// Act
		final String endpoint = String.format("/%d", ShortUrlController.API_VERSION);
		this.mvc.perform(post(endpoint)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("url", longUrlStr)
				.accept(MediaType.TEXT_PLAIN))
		// Assert
		.andExpect(status().isOk()).andExpect(result -> {
			final String serverUrl = result.getRequest().getRequestURL().toString();
					final String expectedShortUrl = String.format("%s/%s", serverUrl, shortCode);
					final String actualShortUrl = result.getResponse().getContentAsString();
					Assert.assertEquals(expectedShortUrl, actualShortUrl);
		});
	}

	@Test
	public void testExpand() throws Exception {
		// Prepare
		final String shortCode = "DEMO";
		final String longUrlStr = "https://the/long/url";
		given(this.shortUrlService.longUrl(shortCode)).willReturn(longUrlStr);

		// Act
		final String endpoint = String.format("/%d/%s", ShortUrlController.API_VERSION, shortCode);
		this.mvc.perform(get(endpoint).accept(MediaType.TEXT_PLAIN))
		// Assert
		.andExpect(status().isOk()).andExpect(content().string(longUrlStr));
	}

	@Test
	public void testExpandCodeUnknown() throws Exception {
		// Prepare
		final String shortCode = "UNKNOWN";
		given(this.shortUrlService.longUrl(shortCode)).willThrow(UnknownShortCodeException.class);

		// Act
		final String endpoint = String.format("/%d/%s", ShortUrlController.API_VERSION, shortCode);
		this.mvc.perform(get(endpoint).accept(MediaType.TEXT_PLAIN))
		// Assert
		.andExpect(status().isNotFound()).andExpect(content().string("Short code not found"));
	}

}
