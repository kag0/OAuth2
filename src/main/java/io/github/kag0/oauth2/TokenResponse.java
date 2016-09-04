package io.github.kag0.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.kag0.oauth2.coding.JsonCodable;
import javaslang.collection.Set;
import javaslang.control.Either;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc6749#section-5.1
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTokenResponse.class)
@JsonDeserialize(as = ImmutableTokenResponse.class)
public interface TokenResponse extends JsonCodable, Parameters {

	@JsonProperty(access_token)
	String accessToken();

	@JsonProperty(token_type)
	TokenType tokenType();

	@JsonProperty(expires_in)
	Optional<Long> exipresIn();

	@JsonProperty(refresh_token)
	Optional<String> refreshToken();

	@JsonProperty(scope)
	Optional<Set<String>> scope();

	static TokenResponse fromJson(JsonNode json) throws JsonProcessingException {
		return MAPPER.treeToValue(json, ImmutableTokenResponse.class);
	}

	static Either<ErrorResponse, TokenResponse> parse(JsonNode json) throws JsonProcessingException {
		if(json.get(access_token) != null)
			return Either.right(fromJson(json));
		return Either.left(ErrorResponse.fromJson(json));
	}
}
