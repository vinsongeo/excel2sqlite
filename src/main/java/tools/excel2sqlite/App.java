package tools.excel2sqlite;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        Excel2Sqlite exc = new Excel2Sqlite();
        exc.operate();
    }
}
