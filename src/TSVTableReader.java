import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class TSVTableReader implements TableReader
{
  BufferedReader source;

  public TSVTableReader(InputStream source)
  {
    this.source = new BufferedReader(new InputStreamReader(source));
  }

  public TSVTableReader(InputStreamReader source)
  {
    this.source = new BufferedReader(source);
  }

  public TSVTableReader(BufferedReader source)
  {
    this.source = source;
  }

  public String[] readRow() throws IOException
  {
    String nextLine = source.readLine();
    if (nextLine != null)
    {
      return nextLine.split("\t");
    }
    return null;
  }

}
