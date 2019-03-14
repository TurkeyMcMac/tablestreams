package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A reader of Tab-Separated Values.
 * 
 * @author Jude Melton-Houghton
 * @see <a href=
 *      "https://www.iana.org/assignments/media-types/text/tab-separated-values">The
 *      TSV specification.</a>
 * @see TSVTableWriter
 */
public class TSVTableReader extends NoEscapeDSVTableReader
{

  /**
   * Instantiate from an input stream.
   * 
   * @param source
   *          the input stream
   */
  public TSVTableReader(InputStream source)
  {
    this(new InputStreamReader(source));
  }

  /**
   * Instantiate from a buffered reader.
   * 
   * @param source
   *          the buffered reader
   */
  public TSVTableReader(BufferedReader source)
  {
    this(new Scanner(source));
  }

  /**
   * Instantiate from any old reader.
   * 
   * @param source
   *          the reader
   */
  public TSVTableReader(Reader source)
  {
    this(new BufferedReader(source));
  }

  /**
   * Instantiate from a scanner.
   * 
   * @param source
   *          the scanner
   */
  public TSVTableReader(Scanner source)
  {
    super(source, '\t', '\n');
  }
}
