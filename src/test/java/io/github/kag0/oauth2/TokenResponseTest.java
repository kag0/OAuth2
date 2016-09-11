package io.github.kag0.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import javaslang.collection.HashSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfischer on 9/3/2016.
 */
public class TokenResponseTest {
	@Test
	public void fromJson() throws Exception {


		TokenResponse e = ImmutableTokenResponse.builder()
				.accessToken("asdf")
				.tokenType(TokenType.StdTokenType.Bearer)
				.exipresIn(60)
				.refreshToken("asdf")
				.scope(HashSet.of("hi", "world"))
				.build();
		JsonNode eJson = e.toJson();
		System.out.println(eJson);

		TokenResponse e2 = TokenResponse.parse(eJson).right().get();
		assertEquals(e, e2);
	}

}