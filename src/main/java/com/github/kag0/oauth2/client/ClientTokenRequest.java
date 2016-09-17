package com.github.kag0.oauth2.client;

import com.github.kag0.oauth2.GrantType;
import com.github.kag0.oauth2.Parameters;
import com.github.kag0.oauth2.TokenRequest;
import com.github.kag0.oauth2.coding.FormCodable;
import com.github.kag0.oauth2.coding.Scopes;
import javaslang.collection.Set;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc6749#section-4.4.2
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface ClientTokenRequest extends TokenRequest, FormCodable, Parameters{

	@Value.Derived
	static GrantType grantType(){
		return GrantType.StdGrantType.client_credentials;
	}

	Optional<Set<String>> scope();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> form = new HashMap<>();
		form.put(grant_type, grantType().name());
		scope()
				.map(Scopes::encodeScopes)
				.ifPresent(scopes -> form.put(scope, scopes));
		return form;
	}

	static ClientTokenRequest fromForm(Map<String, String> form){
		if(!GrantType.StdGrantType.client_credentials.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.StdGrantType.client_credentials.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutableClientTokenRequest.builder()
				.scope(Optional.ofNullable(form.get(scope))
						.map(Scopes::decodeScopes))
				.build();
	}

	static ClientTokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}


}
