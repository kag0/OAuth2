package io.github.kag0.oauth2;

import io.github.kag0.oauth2.client.ClientTokenRequest;
import io.github.kag0.oauth2.code.CodeTokenRequest;
import io.github.kag0.oauth2.coding.FormCodable;
import io.github.kag0.oauth2.password.PasswordTokenRequest;
import io.github.kag0.oauth2.refresh.RefreshTokenRequest;

import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/3/2016.
 */
public interface TokenRequest extends FormCodable, Parameters {

	/**
	 *
	 * @param form
	 * @return some token request, or empty if the grant type is not recognized
	 */
	static Optional<TokenRequest> parse(Map<String, String> form){
		GrantType.StdGrantType grantType = GrantType.StdGrantType.valueOf(form.get(grant_type));
		switch (grantType){
			case authorization_code:
				return Optional.of(CodeTokenRequest.fromForm(form));
			case password:
				return Optional.of(PasswordTokenRequest.fromForm(form));
			case client_credentials:
				return Optional.of(ClientTokenRequest.fromForm(form));
			case refresh_token:
				return Optional.of(RefreshTokenRequest.fromForm(form));
			default:
				return Optional.empty();
		}
	}

	static Optional<TokenRequest> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}
}
