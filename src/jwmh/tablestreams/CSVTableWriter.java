package jwmh.tablestreams;

import java.io.OutputStream;

/**
 * A Comma-Separated Value writer.
 * 
 * @author Jude Melton-Houghton
 * @see <a href="https://tools.ietf.org/html/rfc4180">The CSV specification</a>
 * @see CSVTableReader
 */
public class CSVTableWriter extends DSVTableWriter
{
  public CSVTableWriter(OutputStream dest)
  {
    super(dest, ',');
  }
}
 