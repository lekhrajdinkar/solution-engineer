## 🔶Decorator pattern

<iframe width="560" height="315" src="https://www.youtube.com/embed/USLwIwyWVIM?si=vWrpCYhP0PzpSot_" allowfullscreen></iframe>

- eg: [https://www.perplexity.ai/search/decorator-pattern-9mJUDwF0TvyuNvoDb_9bRg](https://www.perplexity.ai/search/decorator-pattern-9mJUDwF0TvyuNvoDb_9bRg)
- Structural design pattern used in OOP
- add new **behaviors** or functionalities to individual objects dynamically—without altering the structure or code of the existing classes. 

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

---
## 🔶Command pattern
- [https://www.perplexity.ai/search/command-pattern-in-oop-J8oD_ouMTaigVow8ijg8mg](https://www.perplexity.ai/search/command-pattern-in-oop-J8oD_ouMTaigVow8ijg8mg)
- behavioral design pattern that encapsulates a request or action as an object.
- Analogy/situation: re-mappable remote to different device. 🗣️
- **component**:
  - **command** interface :: execute() 
    - concrete command 1 ::  execute(){...}
    - concrete command 2 ::  execute(){...}
  - **receiver**  class - contains the actual business logic.
    - b1(){...}
    - b2(){...}
  - **invoker** class - invokes the command
    - remoteControl (with re-mappable buttons)

```java
// Command interface
interface Command {    void execute();}

// Receiver class - contains the actual business logic
class Light {
    public void turnOn() {        System.out.println("Light is ON");    }
    public void turnOff() {        System.out.println("Light is OFF");    }
}

// Concrete Command to turn on the light
class TurnOnCommand implements Command {
    private Light light;
    public TurnOnCommand(Light light) {        this.light = light;    }
    public void execute() {        light.turnOn();    }
}

// Concrete Command to turn off the light
class TurnOffCommand implements Command {
    private Light light;
    public TurnOffCommand(Light light) {        this.light = light;    }
    public void execute() {        light.turnOff();    }
}

// Invoker class - invokes the command
class RemoteControl {
    private Command command;
    public void setCommand(Command command) {        this.command = command;    }
    public void pressButton() {        command.execute();    }
}

// Client - sets up objects and commands
public class CommandPatternDemo {
    public static void main(String[] args) 
    {
        Light livingRoomLight = new Light(); // Receiver-1

        // 1️⃣ The client creates a command object and sets its receiver. 
        Command turnOn = new TurnOnCommand(livingRoomLight);
        Command turnOff = new TurnOffCommand(livingRoomLight);

        RemoteControl remote = new RemoteControl(); //Invoker

        // 2️⃣ client assigns the command to an invoker
        remote.setCommand(turnOn);        remote.pressButton(); //action-11
        remote.setCommand(turnOff);        remote.pressButton(); //action-2
    }
}

```
 
--- 
## 🔶Adapter
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