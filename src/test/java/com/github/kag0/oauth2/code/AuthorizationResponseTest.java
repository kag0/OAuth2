package com.github.kag0.oauth2.code;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class AuthorizationResponseTest {
	@Test
	public void fromFormEncoded() throws Exception {
		ImmutableAuthorizationResponse r = ImmutableAuthorizationResponse.builder()
				.code("hi")
				.state("bloblob")
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, AuthorizationResponse.parseEncoded(rEncoded).get());
	}

}