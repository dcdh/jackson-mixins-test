import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import inheritance.Animal;
import inheritance.Cat;
import inheritance.Dog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // /!\ all mixins classes must be static
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "@type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Cat.class, name = "cat"),
            @JsonSubTypes.Type(value = Dog.class, name = "dog")
    })
    public static abstract class AnimalMixIn {
    }

    public static abstract class CatMixIn extends AnimalMixIn {
        @JsonCreator
        public CatMixIn(@JsonProperty("name") String name) {
        }
    }

    public static abstract class DogMixIn extends AnimalMixIn {
        @JsonCreator
        public DogMixIn(@JsonProperty("name") String name) {
        }
    }

    static {
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        OBJECT_MAPPER.addMixIn(Animal.class, AnimalMixIn.class);
        OBJECT_MAPPER.addMixIn(Cat.class, CatMixIn.class);
        OBJECT_MAPPER.addMixIn(Dog.class, DogMixIn.class);
    }

    @Test
    public void should_serialize_a_cat() throws Exception {
        // Given
        final Animal cat = new Cat("catinou");

        // When
        final String serialized = OBJECT_MAPPER.writeValueAsString(cat);

        // Then
        Assertions.assertEquals("{\"@type\":\"cat\",\"name\":\"catinou\"}", serialized);
    }

    @Test
    public void should_deserialize_a_cat() throws Exception {
        // Given
        final String serialized = "{\"@type\":\"cat\",\"name\":\"catinou\"}";

        // When
        final Animal cat = OBJECT_MAPPER.readValue(serialized.getBytes(), Animal.class);

        // Then
        Assertions.assertEquals(new Cat("catinou"), cat);
        Assertions.assertEquals("Miahouu", cat.sayHello());
    }

    @Test
    public void should_serialize_a_dog() throws Exception {
        // Given
        final Animal dog = new Dog("doggy");

        // When
        final String serialized = OBJECT_MAPPER.writeValueAsString(dog);

        // Then
        Assertions.assertEquals("{\"@type\":\"dog\",\"name\":\"doggy\"}", serialized);
    }

    @Test
    public void should_deserialize_a_dog() throws Exception {
        // Given
        final String serialized = "{\"@type\":\"dog\",\"name\":\"doggy\"}";

        // When
        final Animal dog = OBJECT_MAPPER.readValue(serialized.getBytes(), Animal.class);

        // Then
        Assertions.assertEquals(new Dog("doggy"), dog);
        Assertions.assertEquals("Whouf", dog.sayHello());
    }

}
