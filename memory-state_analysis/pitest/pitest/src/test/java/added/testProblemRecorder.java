package added;

import org.junit.Test;

import static stateprobe.pitest.ProblemRecorder.addOneLine;

public class testProblemRecorder {

    @Test
    public void testAddOneLine() {
        addOneLine(null, "test.txt");
    }
}
