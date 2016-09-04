package io.github.kag0.oauth2.password;

import io.github.kag0.oauth2.TokenRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class PasswordTokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		PasswordTokenRequest r = ImmutablePasswordTokenRequest.builder()
				.username("jim")
				.password("pass☢")
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}