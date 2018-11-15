import java.io.OutputStream;

/**
 * Virtual High School
 * @author type your full name here
 */

public class CSVTableWriter extends DSVTableWriter
{
  public CSVTableWriter(OutputStream dest)
  {
    super(dest, ',');
  }
}
 