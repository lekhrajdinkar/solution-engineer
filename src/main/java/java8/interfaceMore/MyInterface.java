package java8.interfaceMore;

public interface MyInterface {

    static void print(){
        //System.out.println("Hello "+this.getClass().getName());
        System.out.println("Printing Hello "+MyInterface.class.getName());
    }

    default void SayHello(){
        //System.out.println("Hello "+this.getClass().getName());
        System.out.println("Say Hello "+MyInterface.class.getName());
    }
}
