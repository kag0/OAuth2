package io.github.kag0.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class ErrorResponseTest {
	@Test
	public void json() throws Exception {
		ErrorResponse e = ImmutableErrorResponse.builder()
				.error(ErrorType.StdErrorType.invalid_request)
				.errorDescription("oops")
				.errorUri(URI.create("oops.com"))
				.build();
		JsonNode eJson = e.toJson();
		System.out.println(eJson);

		ErrorResponse e2 = TokenResponse.parse(eJson).getLeft();
		assertEquals(e, e2);
	}

	@Test
	public void form(){
		ErrorResponse e = ImmutableErrorResponse.builder()
				.error(ErrorType.StdErrorType.invalid_request)
				.errorDescription("oops")
				.errorUri(URI.create("oops.com"))
				.build();
		String eEncoded = e.toFormEncoded();
		assertEquals(e, ErrorResponse.fromFormEncoded(eEncoded));
	}

}