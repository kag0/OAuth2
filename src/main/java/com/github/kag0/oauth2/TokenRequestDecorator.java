package com.github.kag0.oauth2;

import org.immutables.value.Value;

/**
 * Created by nfischer on 9/3/2016.
 */
public interface TokenRequestDecorator extends TokenRequest {
	@Value.Parameter
	TokenRequest base();
}
