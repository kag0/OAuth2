package com.github.kag0.oauth2;

import com.github.kag0.oauth2.code.CodeTokenRequest;
import com.github.kag0.oauth2.code.ImmutableCodeTokenRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class SecretClientTokenRequestTest {

	@Test
	public void parseEncoded() throws Exception {
		CodeTokenRequest cR = ImmutableCodeTokenRequest.builder()
				.clientId("xyz")
				.code("abc")
				.build();

		SecretClientTokenRequest r = ImmutableSecretClientTokenRequest.of(cR, "secret");

		String rEncoded = r.toFormEncoded();
		System.out.println(rEncoded);

		SecretClientTokenRequest r2 = SecretClientTokenRequest.parseEncoded(rEncoded).get();
		assertEquals(r, r2);
		assertEquals(cR, r2.base());
	}

}