package pyb.portfolio.urlshortener.api.v1;

import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {

}
