package com.wraith.repository;

import com.wraith.encoding.Encoding;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: rowan.massey
 * Date: 24/03/13
 * Time: 19:24
 */
public class EncodingTest {

    @Test
    public void testEncodePasswordWithNullSalt() throws Exception {
        Encoding encoding = new Encoding();
        String encodedPassword = encoding.encodePassword("Passw0rd");
        Assert.assertEquals(encodedPassword, "d41e98d1eafa6d6011d3a70f1a5b92f0");
    }

    @Test
    public void testEncodePasswordWithStringSalt() throws Exception {
        Encoding encoding = new Encoding();
        String encodedPassword = encoding.encodePassword("Passw0rd");
        Assert.assertEquals(encodedPassword, "f7ceac87f089e9185cf0313f1ef43f60");
    }

    @Test
    public void testValidPasswordWithNullSalt() throws Exception {
        Encoding encoding = new Encoding();
        Assert.assertTrue(encoding.validPassword("Passw0rd", "d41e98d1eafa6d6011d3a70f1a5b92f0"));
    }

    @Test
    public void testValidPasswordWithStringSalt() throws Exception {
        Encoding encoding = new Encoding();
        Assert.assertTrue(encoding.validPassword("Passw0rd", "f7ceac87f089e9185cf0313f1ef43f60"));
    }
}
