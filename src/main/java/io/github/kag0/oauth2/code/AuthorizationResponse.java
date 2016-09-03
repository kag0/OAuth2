package io.github.kag0.oauth2.code;

import io.github.kag0.oauth2.Parameters;
import io.github.kag0.oauth2.coding.FormCodable;
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

	static AuthorizationResponse fromForm(Map<String, String> form){
		return ImmutableAuthorizationResponse.builder()
				.code(form.get(code))
				.state(Optional.ofNullable(form.get(state)))
				.build();
	}

	static AuthorizationResponse fromFormEncoded(String encoded){
		return fromForm(FormCodable.encodedToForm(encoded));
	}

	@Value.Derived
	default Map<String, String> toForm(){
		Map<String, String> map = new HashMap<>();
		map.put(code, code());
		state().ifPresent(s -> map.put(state, s));
		return map;
	}
}
