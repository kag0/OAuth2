package com.github.kag0.oauth2.code;

import com.github.kag0.oauth2.GrantType;
import com.github.kag0.oauth2.Parameters;
import com.github.kag0.oauth2.TokenRequest;
import com.github.kag0.oauth2.coding.FormCodable;
import org.immutables.value.Value;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface CodeTokenRequest extends TokenRequest, FormCodable, Parameters {

	@Value.Derived
	static GrantType grantType(){
		return GrantType.StdGrantType.authorization_code;
	}

	String code();
	Optional<URI> redirectUri();
	Optional<String> clientId();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> form = new HashMap<>();
		form.put(grant_type, grantType().name());
		form.put(code, code());
		redirectUri()
				.map(URI::toASCIIString)
				.ifPresent(r -> form.put(redirect_uri, r));
		clientId().ifPresent(id -> form.put(client_id, id));
		return form;
	}

	static CodeTokenRequest fromForm(Map<String, String> form){
		if(!GrantType.StdGrantType.authorization_code.name().equals(form.get(grant_type)))
			throw new IllegalArgumentException("Expected grant_type " +
					GrantType.StdGrantType.authorization_code.name() +
					" but found " + form.get(grant_type)
			);
		return ImmutableCodeTokenRequest.builder()
				.code(form.get(code))
				.redirectUri(Optional.ofNullable(form.get(redirect_uri))
						.map(URI::create))
				.clientId(Optional.ofNullable(form.get(client_id)))
				.build();
	}

	static CodeTokenRequest fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}
}
