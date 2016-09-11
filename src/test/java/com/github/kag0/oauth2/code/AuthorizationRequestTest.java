package com.github.kag0.oauth2.code;

import javaslang.collection.HashSet;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class AuthorizationRequestTest {
	@Test
	public void fromFormEncoded() throws Exception {
		AuthorizationRequest request = ImmutableAuthorizationRequest.builder()
				.clientId("alice")
				.redirectUri(URI.create("door.black"))
				.scope(HashSet.of("hi"))
				.state("xyz")
				.build();
		String requestString = request.toFormEncoded();
		AuthorizationRequest parsed = AuthorizationRequest.fromFormEncoded(requestString);
		assertEquals(request, parsed);
	}

}