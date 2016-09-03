package io.github.kag0.oauth2.refresh;

import io.github.kag0.oauth2.GrantType;
import javaslang.collection.Set;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Created by nfischer on 9/2/2016.
 */
@Value.Immutable
public interface TokenRequest {
	@Value.Derived
	default GrantType grantType(){
		return GrantType.StdGrantType.refresh_token;
	}
	String refreshToken();
	Optional<Set<String>> scope();
}
