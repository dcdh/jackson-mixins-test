package inheritance;

public final class Dog extends Animal {

    public Dog(final String name) {
        super(name);
    }

    @Override
    public String sayHello() {
        return "Whouf";
    }

}
