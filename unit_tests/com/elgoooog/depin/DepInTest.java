package com.elgoooog.depin;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Nicholas Hauschild
 *         Date: 2/19/11
 *         Time: 4:07 PM
 */
public class DepInTest {
    @Test
    public void loadConfigurationTest() throws Exception {
        System.setProperty("depinConfigurationFile", "config/depinTest.xml");
        DepIn depIn = new DepIn(new DepInFileLoaderStub(UNIT_TEST_EXPECTED));

        depIn.loadConfiguration();
        System.clearProperty("depinConfigurationFile");
    }

    @Test
    public void loadConfigurationTest_defaultFile() throws Exception {
        File defaultFile = new File("config/depin.xml");
        FileInputStream fis = new FileInputStream(defaultFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while(line != null) {
            builder.append(line).append("\r\n");
            line = reader.readLine();
        }

        DepIn depIn = new DepIn(new DepInFileLoaderStub(builder.toString().trim()));
        depIn.loadConfiguration();
    }

    @Test
    public void loadConfigurationTest_file() throws Exception {
        DepIn depIn = new DepIn(new DepInFileLoaderStub(UNIT_TEST_EXPECTED));
        depIn.loadConfiguration("config/depinTest.xml");
    }

    @Test
    public void loadConfigurationTest_inputStream() throws Exception {
        DepIn depIn = new DepIn(new DepInFileLoaderStub(UNIT_TEST_EXPECTED));
        depIn.loadConfiguration(new FileInputStream("config/depinTest.xml"));
    }

    private static final String UNIT_TEST_EXPECTED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<depin>\r\n" +
            "    <bean id=\"testAnimal\" class=\"com.elgoooog.depin.test.Animal\"/>\r\n" +
            "</depin>";

    private class DepInFileLoaderStub extends DepInFileLoader {
        private String expected;

        private DepInFileLoaderStub(String e) {
            super(null);
            expected = e;
        }

        @Override
        public void load(InputStream is, Beans beans) {
            try {
                int available = is.available();
                byte[] bytes = new byte[available];
                is.read(bytes, 0, available);
                String actual = new String(bytes);
                assertEquals(expected, actual);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
