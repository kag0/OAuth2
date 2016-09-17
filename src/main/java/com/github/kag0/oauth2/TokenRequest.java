package com.github.kag0.oauth2;

import com.github.kag0.oauth2.client.ClientTokenRequest;
import com.github.kag0.oauth2.code.CodeTokenRequest;
import com.github.kag0.oauth2.coding.FormCodable;
import com.github.kag0.oauth2.jwt.JwtTokenRequest;
import com.github.kag0.oauth2.password.PasswordTokenRequest;
import com.github.kag0.oauth2.refresh.RefreshTokenRequest;
import javaslang.API;

import java.util.Map;
import java.util.Optional;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * Created by nfischer on 9/3/2016.
 */
public interface TokenRequest extends FormCodable {

	/**
	 *
	 * @param form
	 * @return some token request, or empty if the grant type is not recognized
	 */
	static Optional<? extends TokenRequest> parse(Map<String, String> form){
		GrantType grantType = GrantType.REGISTRY.parse(form.get(Parameters.grant_type));

		return Match(grantType).option(
				 API.Case(GrantType.StdGrantType.authorization_code, () -> CodeTokenRequest.fromForm(form))
				, API.Case(GrantType.StdGrantType.password,           () -> PasswordTokenRequest.fromForm(form))
				, API.Case(GrantType.StdGrantType.client_credentials, () -> ClientTokenRequest.fromForm(form))
				, API.Case(GrantType.StdGrantType.refresh_token,      () -> RefreshTokenRequest.fromForm(form))
				,Case(instanceOf(GrantType.JwtGrantType.class),
						                  () -> JwtTokenRequest.fromForm(form))
		).toJavaOptional();
	}

	static Optional<? extends TokenRequest> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}
}
