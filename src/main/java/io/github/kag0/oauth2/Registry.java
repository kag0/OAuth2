package io.github.kag0.oauth2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by nfischer on 9/2/2016.
 */
public final class Registry<T extends Registry.Named> {
	private Map<String, T> entries = new ConcurrentSkipListMap<>();

	public Registry<T> register(T entry){
		entries.put(entry.name(), entry);
		return this;
	}

	public T parse(String entry){
		return entries.get(entry);
	}

	public Optional<T> parseOptional(String entry){
		return Optional.ofNullable(parse(entry));
	}

	@JsonSerialize(using = Named.NamedSerializer.class)
	public interface Named {
		String name();

		abstract class NamedDeserializer<T extends Named> extends StdDeserializer<T>{
			private final Registry<T> r;

			public NamedDeserializer(Registry<T> r, Class<T> clazz){
				super(clazz);
				this.r = r;
			}

			@Override
			public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
				String name = jsonParser.getText();
				T value = r.parse(name);
				if(value == null)
					throw new IOException("No Named value registered for " + name + '.');
				return value;
			}
		}

		class NamedSerializer extends StdSerializer<Named>{
			public NamedSerializer() {
				super(Named.class);
			}

			@Override
			public void serialize(Named named, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				serializerProvider.defaultSerializeValue(named.name(), jsonGenerator);
			}
		}
	}
}
