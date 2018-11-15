import java.io.IOException;
import java.io.OutputStream;

/**
 * Virtual High School
 * 
 * @author type your full name here
 */

public class DSVTableWriter implements TableWriter
{

  OutputStream dest;
  char delim;

  public DSVTableWriter(OutputStream dest, char delim)
  {
    this.dest = dest;
    this.delim = delim;
  }

  private String deliminate(String cell)
  {
    return "\"" + cell.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  public void writeRow(String... row) throws IOException
  {
    if (row.length > 0)
    {
      StringBuffer buf = new StringBuffer(deliminate(row[0]));
      for (int i = 1; i < row.length; ++i)
      {
        buf.append(delim);
        buf.append(deliminate(row[i]));
      }
      dest.write(buf.toString().getBytes());
    }
    dest.write('\n');
  }

}
