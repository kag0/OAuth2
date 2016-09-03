package io.github.kag0.oauth2.code;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class TokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		TokenRequest r = ImmutableTokenRequest.builder()
				.code("asdf")
				.redirectUri(URI.create("mysite.com"))
				.clientId("bob")
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, TokenRequest.fromFormEncoded(rEncoded));
	}

}