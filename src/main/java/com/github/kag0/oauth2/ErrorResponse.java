package com.github.kag0.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.kag0.oauth2.coding.FormCodable;
import com.github.kag0.oauth2.coding.JsonCodable;
import org.immutables.value.Value;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc6749#section-5.2
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableErrorResponse.class)
@JsonDeserialize(as = ImmutableErrorResponse.class)
public interface ErrorResponse extends JsonCodable, FormCodable, Parameters{

	@Value.Parameter
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

	static ErrorResponse fromForm(Map<String, String> form){
		return ImmutableErrorResponse.builder()
				.error(ErrorType.REGISTRY.parse(form.get(error)))
				.errorDescription(Optional.ofNullable(form.get(error_description)))
				.errorUri(Optional.ofNullable(form.get(error_uri)).map(URI::create))
				.state(Optional.ofNullable(form.get(state)))
				.build();
	}

	static ErrorResponse fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}

	@Value.Derived
	@JsonIgnore
	default Map<String, String> toForm(){
		Map<String, String> map = new HashMap<>();
		map.put(error, error().name());
		errorDescription().ifPresent(ed -> map.put(error_description, ed));
		errorUri().map(URI::toASCIIString).ifPresent(u -> map.put(error_uri, u));
		state().ifPresent(s -> map.put(state, s));
		return map;
	}
}
