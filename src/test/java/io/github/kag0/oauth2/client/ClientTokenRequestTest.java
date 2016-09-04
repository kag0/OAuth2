package io.github.kag0.oauth2.client;

import io.github.kag0.oauth2.TokenRequest;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by nfischer on 9/3/2016.
 */
public class ClientTokenRequestTest {
	@org.junit.Test
	public void form() throws Exception {
		Set<String> scopes = HashSet.of("a", "b", "c", "D");
		ImmutableClientTokenRequest request = ImmutableClientTokenRequest.builder()
				.scope(scopes)
				.build();

		String urlForm = request.toFormEncoded();
		System.out.println(urlForm);

		ClientTokenRequest parsed = (ClientTokenRequest) TokenRequest.parseEncoded(urlForm).get();
		assertEquals(scopes, parsed.scope().get());

		try{
			ClientTokenRequest.fromFormEncoded("grant_type=nopez&scope=a+b+c+D");
			fail();
		}catch (IllegalArgumentException e){}
	}

}