import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    @Test
    public void testGetLocalNumber(){
        MainClass oneMainClass = new MainClass();
        Assert.assertEquals("getLocalNumber() doesn't return 14", oneMainClass.getLocalNumber(), 14);
    }

    @Test
    public void testGetClassNumber(){
        MainClass oneMainClass = new MainClass();
        int number = oneMainClass.getClassNumber();
        Assert.assertTrue("getClassNumber doesn't return number greeter than 45: " + number, number > 45);
    }

    @Test
    public void testGetClassString(){
        MainClass oneMainClass = new MainClass();
        String string = oneMainClass.getClassString();
        Assert.assertTrue("getClassString contains neither 'hello' nor 'Hello': " + string,
                string.matches("(.*)[hH]ello(.*)") );
    }
}
