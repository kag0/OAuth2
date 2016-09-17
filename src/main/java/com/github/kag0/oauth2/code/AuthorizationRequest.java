package com.github.kag0.oauth2.code;

import com.github.kag0.oauth2.Parameters;
import com.github.kag0.oauth2.ResponseType;
import com.github.kag0.oauth2.coding.FormCodable;
import com.github.kag0.oauth2.coding.Scopes;
import javaslang.collection.Set;
import org.immutables.value.Value;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface AuthorizationRequest extends FormCodable, Parameters{

	@Value.Derived
	default ResponseType responseType(){
		return ResponseType.StdResponseType.code;
	}

	String clientId();
	Optional<URI> redirectUri();
	Optional<Set<String>> scope();
	Optional<String> state();

	static AuthorizationRequest fromForm(Map<String, String> form){
		if(!ResponseType.StdResponseType.code.name().equals(form.get(response_type)))
			throw new IllegalArgumentException("Expected response_type " +
					ResponseType.StdResponseType.code.name() +
					" but found " + form.get(response_type)
			);
		return ImmutableAuthorizationRequest.builder()
				.scope(Optional.ofNullable(form.get(scope))
						.map(Scopes::decodeScopes))
				.clientId(form.get(client_id))
				.redirectUri(Optional.ofNullable(form.get(redirect_uri))
						.map(URI::create))
				.state(Optional.ofNullable(form.get(state)))
				.build();
	}

	static AuthorizationRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> map = new HashMap<>();
		map.put(response_type, responseType().name());
		map.put(client_id, clientId());
		redirectUri().ifPresent(r -> map.put(redirect_uri, r.toASCIIString()));
		scope().map(Scopes::encodeScopes).ifPresent(s -> map.put(scope, s));
		state().ifPresent(s -> map.put(state, s));
		return map;
	}

}
