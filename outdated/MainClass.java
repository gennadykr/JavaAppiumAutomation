public class MainClass {

    private int class_number = 20;

    public int getClassNumber(){
        return this.class_number;
    }

    public int getLocalNumber(){
        final int i = 14;
        return i;
    }

    private String class_string = "Hello, world";

    public String getClassString(){
        return this.class_string;
    }

}
