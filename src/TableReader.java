import java.io.IOException;
import java.util.Iterator;

/**
 * Virtual High School
 * @author type your full name here
 */

public interface TableReader
{
  public String[] readRow() throws IOException, TableReadException;
}
 