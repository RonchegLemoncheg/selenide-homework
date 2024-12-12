package ge.tbcitacademy.retry;

import ge.tbcitacademy.retry.RetryCount;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    // es cotnes rogorc aqvs mase davtove kargia calke anotacia maqsimaluri retrystvis
    int counter = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);

        if (counter < annotation.count()){
            ++counter;
            return true;
        }
        return false;
    }
}