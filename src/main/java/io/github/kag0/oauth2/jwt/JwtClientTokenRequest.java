package io.github.kag0.oauth2.jwt;

import io.github.kag0.oauth2.Parameters;
import io.github.kag0.oauth2.TokenRequest;
import io.github.kag0.oauth2.TokenRequestDecorator;
import io.github.kag0.oauth2.coding.FormCodable;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/3/2016.
 */
@Value.Immutable
public abstract class JwtClientTokenRequest implements TokenRequestDecorator, Parameters {

	@Value.Parameter
	public abstract TokenRequest base();

	@Value.Derived
	public String clientAssertionType(){
		return client_assertion_type_jwt;
	}

	@Value.Parameter
	public abstract String clientAssertion();

	@Value.Derived
	public Map toForm(){
		Map form = new HashMap<>();
		form.putAll(base().toForm());

		form.put(client_assertion_type, clientAssertionType());
		form.put(client_assertion, clientAssertion());

		return form;
	}

	static Optional<JwtClientTokenRequest> parse(Map<String, String> form){
		if(!client_assertion_type_jwt.equals(form.get(client_assertion_type)))
			throw new IllegalArgumentException("Expected client_assertion_type " +
					client_assertion_type_jwt +
					" but found " + form.get(client_assertion_type)
			);

		return TokenRequest.parse(form)
				.map(base ->
					ImmutableJwtClientTokenRequest.builder()
						.base(base)
						.clientAssertion(form.get(client_assertion))
						.build()
				);
	}

	static Optional<JwtClientTokenRequest> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}
}
