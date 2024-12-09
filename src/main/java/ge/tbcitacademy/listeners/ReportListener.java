package ge.tbcitacademy.listeners;
import org.testng.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map.Entry;

public class ReportListener {
    // amaze imito armiweria onstart da onfinish rom ideashi amis funqcionalurobas vumateb suitelisteners class
    // aq ubralod es methodi miweria
    public void logFailedTests(ISuite suite) {
        for (Entry<String, ISuiteResult> entry : suite.getResults().entrySet()) {
            ITestContext testContext = entry.getValue().getTestContext();

            for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
                System.out.printf("Test '%s' failed in Suite '%s'.%n", failedTest.getName(), suite.getName());
                System.out.println("Failure Reason: " + failedTest.getThrowable().getMessage());
            }
        }
    }
}
