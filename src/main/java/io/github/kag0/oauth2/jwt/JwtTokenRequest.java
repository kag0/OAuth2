package io.github.kag0.oauth2.jwt;

import io.github.kag0.oauth2.GrantType;
import io.github.kag0.oauth2.Parameters;
import io.github.kag0.oauth2.TokenRequest;
import io.github.kag0.oauth2.coding.FormCodable;
import io.github.kag0.oauth2.coding.Scopes;
import javaslang.collection.Set;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/3/2016.
 */
@Value.Immutable
public interface JwtTokenRequest extends TokenRequest, Parameters {

	@Value.Derived
	static GrantType grantType(){
		return GrantType.JwtGrantType.INSTANCE;
	}

	String assertion();
	Optional<Set<String>> scope();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> form = new HashMap<>();
		form.put(grant_type, grantType().name());
		form.put(assertion, assertion());
		scope()
				.map(Scopes::encodeScopes)
				.ifPresent(s -> form.put(scope, s));
		return form;
	}

	static JwtTokenRequest fromForm(Map<String, String> form){
		if(!GrantType.JwtGrantType.INSTANCE.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.JwtGrantType.INSTANCE.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutableJwtTokenRequest.builder()
				.assertion(form.get(assertion))
				.scope(Optional.ofNullable(form.get(scope)).map(Scopes::decodeScopes))
				.build();
	}

	static JwtTokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}

}
