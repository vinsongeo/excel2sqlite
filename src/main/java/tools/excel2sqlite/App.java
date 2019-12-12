package tools.excel2sqlite;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Excel2Sqlite exc = new Excel2Sqlite();
		exc.operate(args[0], args[1], args[2]);
	}
}
