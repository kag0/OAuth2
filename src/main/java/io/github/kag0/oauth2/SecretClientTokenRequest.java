package io.github.kag0.oauth2;

import io.github.kag0.oauth2.coding.FormCodable;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.github.kag0.oauth2.Parameters.client_secret;

/**
 * Created by nfischer on 9/3/2016.
 */
@Value.Immutable
public abstract class SecretClientTokenRequest implements TokenRequestDecorator {

	@Value.Parameter
	public abstract TokenRequest base();

	@Value.Parameter
	public abstract String clientSecret();

	@Value.Derived
	public Map toForm(){
		Map form = new HashMap<>();
		form.putAll(base().toForm());
		form.put(client_secret, clientSecret());
		return form;
	}

	static Optional<SecretClientTokenRequest> parse(Map<String, String> form){
		return TokenRequest.parse(form)
				.map(base ->
						ImmutableSecretClientTokenRequest.builder()
								.base(base)
								.clientSecret(form.get(client_secret))
								.build()
				);
	}

	static Optional<SecretClientTokenRequest> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}
}
