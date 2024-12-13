import org.testng.annotations.Factory;

public class ParametrizedSwoopTest2Factory {
// es klasi vergavige calke unda gametana packageshi tu ara
    @Factory
    public Object[] createInstances() {
        return new Object[] {
                new ParametrizedSwoopTests2("კვება"),
                new ParametrizedSwoopTests2("გართობა"),
                new ParametrizedSwoopTests2("დასვენება")
        };
    }
}