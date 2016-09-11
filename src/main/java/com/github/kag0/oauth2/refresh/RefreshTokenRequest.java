package com.github.kag0.oauth2.refresh;

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
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface RefreshTokenRequest extends TokenRequest, FormCodable, Parameters {
	@Value.Derived
	static GrantType grantType(){
		return GrantType.StdGrantType.refresh_token;
	}
	String refreshToken();
	Optional<Set<String>> scope();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> form = new HashMap<>();
		form.put(grant_type, grantType().name());
		form.put(refresh_token, refreshToken());
		scope().map(Scopes::encodeScopes).ifPresent(s -> form.put(scope, s));
		return form;
	}

	static RefreshTokenRequest fromForm(Map<String, String> form){
		if(!GrantType.StdGrantType.refresh_token.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.StdGrantType.refresh_token.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutableRefreshTokenRequest.builder()
				.refreshToken(form.get(refresh_token))
				.scope(Optional.ofNullable(form.get(scope)).map(Scopes::decodeScopes))
				.build();
	}

	static RefreshTokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}
}
