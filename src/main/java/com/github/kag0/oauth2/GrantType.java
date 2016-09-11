package com.github.kag0.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * https://tools.ietf.org/html/rfc6749#section-1.3
 * https://tools.ietf.org/html/rfc6749#section-8.3
 *
 * Created by nfischer on 9/2/2016.
 */
@JsonDeserialize(using = GrantType.GrantTypeDeserializer.class)
public interface GrantType extends Registry.Named {

	Registry<GrantType> REGISTRY = new Registry<>(GrantType.class);

	enum StdGrantType implements GrantType {
		authorization_code,
		password,
		client_credentials,
		refresh_token
		;

		StdGrantType(){
			REGISTRY.register(this);
		}
	}

	class JwtGrantType implements GrantType{

		public static final JwtGrantType INSTANCE = new JwtGrantType();
		static {
			GrantType.REGISTRY.register(INSTANCE);
		}

		@Override
		public String name() {
			return "urn:ietf:params:oauth:grant-type:jwt-bearer";
		}
	}

	class GrantTypeDeserializer extends NamedDeserializer<GrantType>{
		public GrantTypeDeserializer() {
			super(REGISTRY, GrantType.class);
		}
	}
}
