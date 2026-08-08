# Structural Patterns (Object composition & structure)
- Used to organize classes and objects cleanly.

## Decorator ✔️
- Add behavior dynamically (without inheritance hell)
- https://youtu.be/USLwIwyWVIM bm
- [https://www.perplexity.ai/search/decorator-pattern-9mJUDwF0TvyuNvoDb_9bRg](https://www.perplexity.ai/search/decorator-pattern-9mJUDwF0TvyuNvoDb_9bRg)
- adds new **behaviors** or functionalities to individual **objects** dynamically
- without altering the structure or code of the existing **classes**.

```java
// Step 1: Component Interface
interface Coffee {
    double getCost();
    String getDescription();
}

// Step 2: Concrete Component
class PlainCoffee implements Coffee {
    public double getCost() {        return 2.0;    }
    public String getDescription() {        return "Plain Coffee";    }
}

// Step 3: Decorator Base Class
abstract class CoffeeDecorator implements Coffee 
{
    // Wrap and delegate ◀️◀️
    protected Coffee coffee; // 
    public CoffeeDecorator(Coffee coffee) {        this.coffee = coffee;    }
    public double getCost() {        return coffee.getCost();    }
    public String getDescription() {        return coffee.getDescription();
    }
}

// Step 4: Concrete Decorators
class MilkDecorator extends CoffeeDecorator 
{
    public MilkDecorator(Coffee coffee) {        super(coffee);    }
    public double getCost() {        return super.getCost() + 0.5;    } // + 0.5; 
    public String getDescription() {        return super.getDescription() + ", Milk";    } // + ", Milk";
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {        super(coffee);    }
    public double getCost() {        return super.getCost() + 0.2;    } //+ 0.2;
    public String getDescription() {        return super.getDescription() + ", Sugar";    } // + ", Sugar"; 
}

// Usage Example
public class CoffeeShop 
{
    public static void main(String[] args) {
        Coffee coffee = new PlainCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());
    }
}
```

## Adapter ✔️
- https://youtu.be/USLwIwyWVIM bm
- [https://www.perplexity.ai/search/adaptor-pattern-java-tl9As0vCQxKXe2MF8239nA](https://www.perplexity.ai/search/adaptor-pattern-java-tl9As0vCQxKXe2MF8239nA)
- structural design pattern
- that acts as a **bridge** between two incompatible interfaces
- Key Components of Adapter Pattern:
    - **Target Interface**: The interface expected by the client.
    - **Adaptee**: The existing class with an incompatible interface.
    - **Adapter**:
        - Implements the target interface ◀️◀️
        - and, wraps the adaptee,
        - translating the client's calls to the adaptee.

```java

// Target interface
public interface RowingBoat {    void row();}

// Adaptee
public class FishingBoat {
    public void sail() {
        System.out.println("The fishing boat is sailing");
    }
}

// Adapter
public class FishingBoatAdapter implements RowingBoat {
    private final FishingBoat boat;
    public FishingBoatAdapter() {        boat = new FishingBoat();    }
    @Override    public void row() {        boat.sail();    }
}

// Client
public class Captain {
    private final RowingBoat rowingBoat;
    public Captain(RowingBoat rowingBoat) {        this.rowingBoat = rowingBoat;    }
    public void row() {        rowingBoat.row();    }
}

// Usage
public static void main(String[] args) {
    Captain captain = new Captain(new FishingBoatAdapter());
    captain.row();  // Outputs: The fishing boat is sailing
}

```

## Bridge 
- Separate abstraction from implementation

## Composite  
- Tree structures (files/folders, UI components)

## Facade
Simple interface over complex subsystems

## Flyweight 
- Share objects to save memory

## Proxy
- Control access (lazy loading, security, caching)