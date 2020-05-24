package inheritance;

import java.util.Objects;

public abstract class Animal {

    private final String name;

    public Animal(final String name) {
        this.name = name;
    }

    public abstract String sayHello();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
