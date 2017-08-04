package pyb.portfolio.urlshortener.api.v1;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "shorturl")
public class ShortUrl {
	@Id
	private String shortCode;
	@NotNull
	private String longUrl;
	private short apiVersion;

	public ShortUrl() {
		// mandatory for org.hibernate.tuple.PojoInstantiator.instantiate
	}

	public ShortUrl(String shortCode, String longUrl, short apiVersion) {
		super();
		this.shortCode = shortCode;
		this.longUrl = longUrl;
		this.apiVersion = apiVersion;
	}

	public URL toURL() throws MalformedURLException {
		return new URL(longUrl);
	}

}
