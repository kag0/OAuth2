package com.github.kag0.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * https://tools.ietf.org/html/rfc6749#section-1.3
 * https://tools.ietf.org/html/rfc6749#section-8.3
 *
 * Created by nfischer on 9/2/2016.
 */
@JsonDeserialize(using = ResponseType.ResponseTypeDeserializer.class)
public interface ResponseType extends Registry.Named {

	Registry<ResponseType> REGISTRY = new Registry<>(ResponseType.class);

	enum StdResponseType implements ResponseType {
		code,
		token
		;

		StdResponseType(){
			REGISTRY.register(this);
		}
	}

	class ResponseTypeDeserializer extends NamedDeserializer<ResponseType>{
		public ResponseTypeDeserializer() {
			super(REGISTRY, ResponseType.class);
		}
	}
}
