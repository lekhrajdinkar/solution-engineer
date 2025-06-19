package java8.functionalApproach;

@FunctionalInterface
interface MouseEventFunctionType {public String handleM();} 
@FunctionalInterface
interface KeyboardEventFunctionType {
    public String handleK();
    public static void utilmethod1(){System.out.println("KeyboardEventFunctionType :: utilMethod1");}
    public default void defaultmethod1(){System.out.println("KeyboardEventFunctionType :: defaultMethod1");}
}

//------- way-2 : Method Ref
interface UtilFunction{
    public static String handleM() {
        return "Method Referernce :: UtilFunction :: mouse";
    }

    public static String handleK() {
        return "Method Referernce :: UtilFunction :: keyboard";
    }
}
public class Functional {
    void processEvent(MouseEventFunctionType m, KeyboardEventFunctionType k, char type){
        switch (type){
            case 'm' : m.handleM(); break;
            case 'k' : k.handleK(); k.defaultmethod1(); KeyboardEventFunctionType.utilmethod1(); break;
            default :  break;
        }
    }
    static void p(Object o){System.out.println(o.toString());}

    //================ RUN====================
    public static void main(String[] ss)
    {
        Functional f = new Functional();

        KeyboardEventFunctionType f_k = null;
        MouseEventFunctionType f_m = null;

        // way-1 - Lambda
        f_k = ()->{ p("lambda expression - implementation for KeyboardEventFunction f-interface / f-type");return "k";} ;
        f_m = ()->{ p("lambda expression - implementation for MouseEventFunction f-interface / f-type");return "m";} ;
        f.processEvent(f_m, f_k, 'k');

        f_k = UtilFunction::handleK;
        f_m = UtilFunction::handleM;
        f.processEvent(f_m, f_k, 'k');

    }
}
