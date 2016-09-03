package io.github.kag0.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 * Created by nfischer on 9/2/2016.
 */
@JsonDeserialize(using = TokenType.TokenTypeDeserializer.class)
public interface TokenType extends Registry.Named {
	Registry<TokenType> REGISTRY = new Registry<>();

	enum StdTokenType implements TokenType {
		Bearer
		;

		StdTokenType(){
			REGISTRY.register(this);
		}
	}

	class TokenTypeDeserializer extends NamedDeserializer<TokenType>{
		public TokenTypeDeserializer() {
			super(REGISTRY, TokenType.class);
		}
	}
}
