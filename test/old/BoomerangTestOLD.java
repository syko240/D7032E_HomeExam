package old;

import org.junit.Assert;
import org.junit.Test;

public class BoomerangTestOLD {

    // 1 %--------------------------------------------------------------------------------------------------

    /*
     * The program is relying on the values in params being valid integers. 
     * If someone were to pass a non-integer value (like "abc"),
     * Integer.valueOf would throw a NumberFormatException.
    */
    @Test
    public void testValidPlayerCount() {
        try {
            String[] params = {"2", "abc"};
            new BoomerangAustralia(params);
            Assert.fail("Expected valid game initialization");
        } catch (Exception e) {
            // This is expected, so do nothing.
        }
    }

    /*@Test
    public void t() {
        try {
            String[] params = {"1", "3"};
            new BoomerangAustralia(params);
        } catch (Exception e) {

        }
    }*/

    // 2 %--------------------------------------------------------------------------------------------------

    /* 
     * Typo in the regions array: "Wester Australia" should be "Western Australia".
     * 
     * Should be "New South Wales" no?
    */

    // 3 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the Third Requirement.
    */

    // 4 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the Forth Requirement.
    */

    // 5 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the Fifth Requirement.
    */

    // 6 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the Sixth Requirement.
    */

    // 7 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the Seventh Requirement.
    */

    // 8 %--------------------------------------------------------------------------------------------------

    /*
     * The code adheres to the eight Requirement.
    */

    // 9 %--------------------------------------------------------------------------------------------------

    
}