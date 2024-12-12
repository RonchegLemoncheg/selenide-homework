import ge.tbcitacademy.retry.RetryAnalyzer;
import ge.tbcitacademy.retry.RetryCount;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    @RetryCount(count = 5)
    public void testMethodToRetry(){

        System.out.println("Retrying...");

        Assert.assertEquals(1,2);
    }
}
