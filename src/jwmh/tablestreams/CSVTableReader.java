package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * A reader of Comma-Separated Values.
 * 
 * @author Jude Melton-Houghton
 * 
 * @see <a href="https://tools.ietf.org/html/rfc4180">The CSV specification</a>
 * @see CSVTableWriter
 */
public class CSVTableReader extends DSVTableReader
{

  /**
   * Instantiate from an input stream.
   * 
   * @param source
   *          the input stream
   */
  public CSVTableReader(InputStream source)
  {
    this(new InputStreamReader(source));
  }

  /**
   * Instantiate from a reader.
   * 
   * @param source
   *          the reader
   */
  public CSVTableReader(Reader source)
  {
    this(new BufferedReader(source));
  }

  /**
   * Instantiate from a buffered reader.
   * 
   * @param source
   *          the reader
   */
  public CSVTableReader(BufferedReader source)
  {
    super(source, ',');
  }

}
