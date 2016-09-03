package io.github.kag0.oauth2.coding;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * Created by nfischer on 9/2/2016.
 */
public interface Scopes {

	static String encodeScopes(Set<?> scopes){
		return scopes
				.map(String::valueOf)
				.mkString(" ");
	}

	static String encodeScopes(java.util.Set<String> scopes){
		return encodeScopes(HashSet.ofAll(scopes));
	}

	static Set<String> decodeScopes(String encoded){
		return HashSet.ofAll(asList(encoded.split(" ")));
	}

	class ScopeSetSerializer extends StdSerializer<Set>{

		protected ScopeSetSerializer() {
			super(Set.class);
		}

		@Override
		public void serialize(Set value, JsonGenerator gen, SerializerProvider provider) throws IOException {
			provider.defaultSerializeValue(Scopes.encodeScopes(value), gen);
		}
	}

	class ScopeSetDeserializer extends StdDeserializer<Set>{

		protected ScopeSetDeserializer() {
			super(Set.class);
		}

		@Override
		public Set deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return Scopes.decodeScopes(p.getText());
		}
	}
}
