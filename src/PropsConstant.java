import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsConstant {
	public static String server;
	public static int port;
	static {
		setup();

	}
	private static void setup() {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			port = Integer.valueOf(prop.getProperty("port"));
			server = String.valueOf(prop.getProperty("server"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
