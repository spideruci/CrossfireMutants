package ProxyAssertions.customized;

import static spiderauxiliary.ProbeRecord.isAssertionError;
import static spiderauxiliary.TestExtension.*;

public class Customized {
    public static void runAssertion(Runnable runnable, String loc) {
        try {
            recordExecutedOracle(loc);
            runnable.run();
        } catch (Throwable t) {
            if (isAssertionError(t)) {
                recordFailedThrowable(loc);
            } else {
                throw t;
            }
        }
    }

    public static void runAssertion(Runnable runnable) {
        runnable.run();
    }

}
