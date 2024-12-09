package ge.tbcitacademy.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuiteListener implements ISuiteListener {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final ReportListener reportListener = new ReportListener();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onStart(ISuite suite) {
        startDate = LocalDateTime.now();
        System.out.printf("Suite '%s' has started execution at %s.\n", suite.getName(), startDate.format(FORMATTER));
    }

    @Override
    public void onFinish(ISuite suite) {
        endDate = LocalDateTime.now();
        Duration totalTime = Duration.between(startDate, endDate);
        System.out.printf("Suite '%s' has finished execution at %s.\n", suite.getName(), endDate.format(FORMATTER));
        System.out.printf("Total time needed to complete the suite: %d ms.\n", totalTime.toMillis());
        reportListener.logFailedTests(suite);
    }
}