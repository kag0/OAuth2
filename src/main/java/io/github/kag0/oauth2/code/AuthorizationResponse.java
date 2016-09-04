package io.github.kag0.oauth2.code;

import io.github.kag0.oauth2.ErrorResponse;
import io.github.kag0.oauth2.Parameters;
import io.github.kag0.oauth2.coding.FormCodable;
import javaslang.control.Either;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface AuthorizationResponse extends FormCodable, Parameters{
	String code();
	Optional<String> state();

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> map = new HashMap<>();
		map.put(code, code());
		state().ifPresent(s -> map.put(state, s));
		return map;
	}

	static AuthorizationResponse fromForm(Map<String, String> form){
		return ImmutableAuthorizationResponse.builder()
				.code(form.get(code))
				.state(Optional.ofNullable(form.get(state)))
				.build();
	}

	static AuthorizationResponse fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}

	static Either<ErrorResponse, AuthorizationResponse> parse(Map<String, String> form){
		if(form.get(code) != null)
			return Either.right(fromForm(form));
		return Either.left(ErrorResponse.fromForm(form));
	}

	static Either<ErrorResponse, AuthorizationResponse> parseEncoded(String form){
		return parse(FormCodable.encodedToForm(form));
	}

}
