package com.github.kag0.oauth2.jwt;

import com.github.kag0.oauth2.GrantType;
import com.github.kag0.oauth2.TokenRequest;
import javaslang.collection.HashSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nfischer on 9/4/2016.
 */
public class JwtTokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		try {
			Class.forName(GrantType.JwtGrantType.class.getName(), true, GrantType.JwtGrantType.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		assertTrue(TokenRequest.parseEncoded("grant_type=urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Ajwt-bearer&scope=hi+world&assertion=pass%E2%98%A2").isPresent());

		JwtTokenRequest r = ImmutableJwtTokenRequest.builder()
				.assertion("pass☢")
				.scope(HashSet.of("hi", "world"))
				.build();
		String rEncoded = r.toFormEncoded();
		System.out.println(rEncoded);
		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}