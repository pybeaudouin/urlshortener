package pyb.portfolio.urlshortener.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

	@Autowired
	private ShortUrlRepository shortUrlRepository;

	@Override
	public void save(ShortUrl shortUrl) {
		shortUrlRepository.save(shortUrl);
	}

	@Override
	public void getByShortCode(String shortCode) {
		shortUrlRepository.findOne(shortCode);
	}

}
