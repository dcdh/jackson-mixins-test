package inheritance;

public final class Cat extends Animal {

    public Cat(final String name) {
        super(name);
    }

    @Override
    public String sayHello() {
        return "Miahouu";
    }

}
