package io.github.kag0.oauth2.password;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class TokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		TokenRequest r = ImmutableTokenRequest.builder()
				.username("jim")
				.password("pass☢")
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, TokenRequest.fromFormEncoded(rEncoded));
	}

}