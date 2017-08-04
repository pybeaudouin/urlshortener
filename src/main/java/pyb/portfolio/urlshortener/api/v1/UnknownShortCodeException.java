package pyb.portfolio.urlshortener.api.v1;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnknownShortCodeException extends Exception {
	private static final long serialVersionUID = -7787575205511554023L;

	@NonNull
	@Getter
	private String shortCode;
}
