package io.github.kag0.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.kag0.oauth2.coding.JsonCodable;
import org.immutables.value.Value;

import java.net.URI;
import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc6749#section-5.2
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableErrorResponse.class)
@JsonDeserialize(as = ImmutableErrorResponse.class)
public interface ErrorResponse extends JsonCodable, Parameters{

	ErrorType error();

	@JsonProperty(error_description)
	Optional<String> errorDescription();

	@JsonProperty(error_uri)
	Optional<URI> errorUri();

	@JsonProperty(state)
	Optional<String> state();

	static ErrorResponse fromJson(JsonNode json) throws JsonProcessingException {
		return MAPPER.treeToValue(json, ImmutableErrorResponse.class);
	}
}
