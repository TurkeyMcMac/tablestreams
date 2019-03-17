package jwmh.tablestreams;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A writer of Delimiter-Separated Values.
 * 
 * @author Jude Melton-Houghton
 * @see DSVTableReader
 * @see <a href="https://tools.ietf.org/html/rfc4180">The CSV specification</a>
 *      (DSV where the delimiter is ',')
 */
public class DSVTableWriter implements TableWriter, Closeable
{

  OutputStream dest;
  char delim;
  boolean started;

  /**
   * Instantiate from an output stream as a destination.
   * 
   * @param dest
   *          the output stream
   * @param delim
   *          the delimiter
   */
  public DSVTableWriter(OutputStream dest, char delim)
  {
    this.dest = dest;
    this.delim = delim;
    this.started = false;
  }

  private String deliminate(String cell)
  {
    return "\"" + cell.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  public void writeRow(String... row) throws IOException
  {
    if (started)
    {
      dest.write('\n');
    }
    started = true;
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
  }

  public void close() throws IOException
  {
    dest.close();
  }
}
