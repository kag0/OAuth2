package io.github.kag0.oauth2.jwt;

import io.github.kag0.oauth2.TokenRequest;
import javaslang.collection.HashSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/4/2016.
 */
public class JwtTokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		JwtTokenRequest r = ImmutableJwtTokenRequest.builder()
				.assertion("pass☢")
				.scope(HashSet.of("hi", "world"))
				.build();
		String rEncoded = r.toFormEncoded();
		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}