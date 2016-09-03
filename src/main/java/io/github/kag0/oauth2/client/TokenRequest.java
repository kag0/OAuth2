package io.github.kag0.oauth2.client;

import io.github.kag0.oauth2.GrantType;
import io.github.kag0.oauth2.Parameters;
import io.github.kag0.oauth2.coding.FormCodable;
import io.github.kag0.oauth2.coding.Scopes;
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
public interface TokenRequest extends FormCodable, Parameters{

	@Value.Derived
	default GrantType grantType(){
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

	static TokenRequest fromForm(Map<String, String> form){
		if(!GrantType.StdGrantType.client_credentials.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.StdGrantType.client_credentials.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutableTokenRequest.builder()
				.scope(Optional.ofNullable(form.get(scope))
						.map(Scopes::decodeScopes))
				.build();
	}

	static TokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}


}
