package io.github.kag0.oauth2.refresh;

import io.github.kag0.oauth2.TokenRequest;
import javaslang.collection.HashSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class RefreshTokenRequestTest {
	@Test
	public void fromForm() throws Exception {
		RefreshTokenRequest r = ImmutableRefreshTokenRequest.builder()
				.refreshToken("asdf")
				.scope(HashSet.of("hello", "world"))
				.build();
		String rEncoded = r.toFormEncoded();

		assertEquals(r, TokenRequest.parseEncoded(rEncoded).get());
	}

}