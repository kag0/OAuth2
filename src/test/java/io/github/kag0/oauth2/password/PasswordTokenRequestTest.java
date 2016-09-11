package io.github.kag0.oauth2.password;

import io.github.kag0.oauth2.TokenRequest;
import javaslang.collection.HashSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nfischer on 9/3/2016.
 */
public class PasswordTokenRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		assertTrue(TokenRequest.parseEncoded("password=pass%E2%98%A2&grant_type=password&scope=hi+world&username=jim").get() instanceof PasswordTokenRequest);

		PasswordTokenRequest r = ImmutablePasswordTokenRequest.builder()
				.username("jim")
				.password("pass☢")
				.scope(HashSet.of("hi", "world"))
				.build();
		String rEncoded = r.toFormEncoded();
		System.out.println(rEncoded);
		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}