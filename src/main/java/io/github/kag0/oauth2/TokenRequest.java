package io.github.kag0.oauth2;

import io.github.kag0.oauth2.client.ClientTokenRequest;
import io.github.kag0.oauth2.code.CodeTokenRequest;
import io.github.kag0.oauth2.coding.FormCodable;
import io.github.kag0.oauth2.jwt.JwtTokenRequest;
import io.github.kag0.oauth2.password.PasswordTokenRequest;
import io.github.kag0.oauth2.refresh.RefreshTokenRequest;

import java.util.Map;
import java.util.Optional;

import static io.github.kag0.oauth2.GrantType.StdGrantType.*;
import static io.github.kag0.oauth2.Parameters.grant_type;
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
		GrantType grantType = GrantType.REGISTRY.parse(form.get(grant_type));

		return Match(grantType).option(
				 Case(authorization_code, () -> CodeTokenRequest    .fromForm(form))
				,Case(password,           () -> PasswordTokenRequest.fromForm(form))
				,Case(client_credentials, () -> ClientTokenRequest  .fromForm(form))
				,Case(refresh_token,      () -> RefreshTokenRequest .fromForm(form))
				,Case(instanceOf(GrantType.JwtGrantType.class),
						                  () -> JwtTokenRequest     .fromForm(form))
		).toJavaOptional();
	}

	static Optional<? extends TokenRequest> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}
}
