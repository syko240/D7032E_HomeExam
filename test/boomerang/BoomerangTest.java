package boomerang;

import org.junit.Assert;
import org.junit.Test;

public class BoomerangTest {
    @Test
    public void test() {
        try {
            String[] params = {"8080", "2", "abc"};
            BoomerangGame game = new BoomerangGame(params);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
