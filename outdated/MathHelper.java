public class MathHelper {

    // Static class field
    public int simple_int = 7;
    public static int static_int = 11;

    public int multiply(int number, int multiplier){
        return number * multiplier;
    }

    public int calc (int a, int b, char action){
        if (action == '+') {
            return this.plus(a, b);
        } else {
            System.out.println("Wrong action:" + action);
            return 0;
        }
    }

    private int plus(int a, int b){
        return a + b;
    }
}
