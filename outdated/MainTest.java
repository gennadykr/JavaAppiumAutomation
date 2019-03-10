import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends CoreTestCase {

    // Class fields
    int a = 5;
    int b = 11;

    // Class object
    MathHelper Math = new MathHelper();

    @Before
    public void textStartTest(){
        System.out.println("Start test");
    }

    @After
    public void textFinishTest(){
        System.out.println("Finish test");
    }


    @Test
    public void myFirstTest(){
        System.out.println("Hello,QA engineers!");
        int number = 18;
        System.out.println(number);
        int a = 10;
        int b = 15;

        Assert.assertTrue("a >= b!", a < b);
        if(a > b){
            System.out.println("This will never happens");
            Assert.fail();
        } else {
            System.out.println("This is what will happen");
        }

        System.out.println(a);
        System.out.println(b);

        System.out.println(this.a);
        System.out.println(this.b);

        this.typeString();

        a = this.giveMeInt();
        System.out.println(a);

        a = this.multiply(5);
        System.out.println(a);

        a = Math.multiply(10, 15);
        System.out.println(a);
    }

    @Test
    public void mySecondTest() {
        System.out.println("Second, static: " + MathHelper.static_int);
        MathHelper.static_int = 8;

        MathHelper mathObject = new MathHelper();
        System.out.println("Second, simple: " + mathObject.simple_int);
        mathObject.simple_int = 12;
    }

    @Test
    public void myThirdTest() {
        System.out.println("Third, static: " + MathHelper.static_int);
        MathHelper.static_int = 9;

        MathHelper mathObject = new MathHelper();
        System.out.println("Third, simple: " + mathObject.simple_int);
        mathObject.simple_int = 13;
    }


    //Function or class method
    //Camel case
    public void typeString(){
        System.out.println("Hello from typeString()");
    }

    public int giveMeInt(){
        return 5;
    }
}
