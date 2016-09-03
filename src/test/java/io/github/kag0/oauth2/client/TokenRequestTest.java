package io.github.kag0.oauth2.client;

import javaslang.collection.HashSet;
import javaslang.collection.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by nfischer on 9/3/2016.
 */
public class TokenRequestTest {
	@org.junit.Test
	public void form() throws Exception {
		Set<String> scopes = HashSet.of("a", "b", "c", "D");
		ImmutableTokenRequest request = ImmutableTokenRequest.builder()
				.scope(scopes)
				.build();

		String urlForm = request.toFormEncoded();
		System.out.println(urlForm);

		TokenRequest parsed = TokenRequest.fromFormEncoded(urlForm);
		assertEquals(scopes, parsed.scope().get());

		try{
			TokenRequest.fromFormEncoded("grant_type=nopez&scope=a+b+c+D");
			fail();
		}catch (IllegalArgumentException e){}
	}

}