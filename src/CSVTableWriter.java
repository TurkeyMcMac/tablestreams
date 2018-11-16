import java.io.OutputStream;

public class CSVTableWriter extends DSVTableWriter
{
  public CSVTableWriter(OutputStream dest)
  {
    super(dest, ',');
  }
}
 