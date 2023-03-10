package setup;

import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {
    public Properties prop;
    @BeforeTest
    public void initConfig() throws IOException {
        prop=new Properties();
        FileInputStream file = new FileInputStream("./src/test/resources/config.properties");
        prop.load(file);

    }
}
