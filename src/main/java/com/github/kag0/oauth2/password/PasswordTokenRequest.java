package com.github.kag0.oauth2.password;

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
 * https://tools.ietf.org/html/rfc6749#section-4.3.2
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface PasswordTokenRequest extends TokenRequest, FormCodable, Parameters {

	@Value.Derived
	static GrantType grantType(){
		return GrantType.StdGrantType.password;
	}

	String username();
	String password();
	Optional<Set<String>> scope();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> form = new HashMap<>();
		form.put(grant_type, grantType().name());
		form.put(username, username());
		form.put(password, password());
		scope()
				.map(Scopes::encodeScopes)
				.ifPresent(s -> form.put(scope, s));
		return form;
	}

	static PasswordTokenRequest fromForm(Map<String, String> form){
		if(!GrantType.StdGrantType.password.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.StdGrantType.password.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutablePasswordTokenRequest.builder()
				.username(form.get(username))
				.password(form.get(password))
				.scope(Optional.ofNullable(form.get(scope)).map(Scopes::decodeScopes))
				.build();
	}

	static PasswordTokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}
}
