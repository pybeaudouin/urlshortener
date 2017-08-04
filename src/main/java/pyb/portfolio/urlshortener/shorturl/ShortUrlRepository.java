package pyb.portfolio.urlshortener.shorturl;

import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

}
