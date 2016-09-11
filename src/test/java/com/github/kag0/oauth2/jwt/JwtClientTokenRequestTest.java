package com.github.kag0.oauth2.jwt;

import com.github.kag0.oauth2.code.CodeTokenRequest;
import com.github.kag0.oauth2.code.ImmutableCodeTokenRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class JwtClientTokenRequestTest {
	@Test
	public void parseEncoded() throws Exception {
		CodeTokenRequest cR = ImmutableCodeTokenRequest.builder()
				.clientId("xyz")
				.code("abc")
				.build();

		JwtClientTokenRequest r = ImmutableJwtClientTokenRequest.of(cR, "jwt");

		String rEncoded = r.toFormEncoded();

		JwtClientTokenRequest r2 = JwtClientTokenRequest.parseEncoded(rEncoded).get();
		assertEquals(r, r2);
		assertEquals(cR, r2.base());
	}

}