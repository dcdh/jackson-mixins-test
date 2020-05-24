import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Must be static !!!!
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static abstract class PersonMixin {

        @JsonCreator
        public PersonMixin(@JsonProperty("firstname") String firstname,
                           @JsonProperty("lastname") String lastname) {
        }

    }

    static {
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        OBJECT_MAPPER.addMixIn(Person.class, PersonMixin.class);
    }

    @Test
    public void should_serialized() throws Exception {
        // Given
        final Person person = new Person("bob", "l'eponge");

        // When
        final String serialized = OBJECT_MAPPER.writeValueAsString(person);

        // Then
        Assertions.assertEquals("{\"firstname\":\"bob\",\"lastname\":\"l'eponge\"}", serialized);
    }

    @Test
    public void should_deserialized() throws Exception {
        // Given
        final String serialized = "{\"firstname\":\"bob\",\"lastname\":\"l'eponge\"}";

        // When
        final Person person = OBJECT_MAPPER.readValue(serialized, Person.class);

        // Then
        Assertions.assertEquals(new Person("bob", "l'eponge"), person);
    }

}
