package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ExampleTest {

    @Test
    public void sampleTest() {
        System.out.println("Test");
        assertTrue(1 == 1);
    }

    @Test
    public void sampleTest2() {
        assertTrue(4 == 4);
    }
    
    // @BeforeEach to initialize/run lines of code before running each test

}
