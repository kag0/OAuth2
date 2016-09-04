package io.github.kag0.oauth2.code;

import io.github.kag0.oauth2.TokenRequest;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class CodeTokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		CodeTokenRequest r = ImmutableCodeTokenRequest.builder()
				.code("asdf")
				.redirectUri(URI.create("mysite.com"))
				.clientId("bob")
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}