package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A reader which uses ASCII character 31 "INFORMATION SEPARATOR ONE" to
 * separate cells and character 30 "INFORMATION SEPARATOR TWO" to separate rows.
 * 
 * @author Jude Melton-Houghton
 * @see ASCIITableWriter
 */
public class ASCIITableReader extends NoEscapeDSVTableReader
{
  /**
   * Instantiate from an input stream.
   * 
   * @param source
   *          the input stream.
   */
  public ASCIITableReader(InputStream source)
  {
    this(new InputStreamReader(source));
  }

  /**
   * Instantiate from a buffered reader.
   * 
   * @param source
   *          the buffered reader.
   */
  public ASCIITableReader(BufferedReader source)
  {
    this(new Scanner(source));
  }

  /**
   * Instantiate from any old reader.
   * 
   * @param source
   *          the reader.
   */
  public ASCIITableReader(Reader source)
  {
    this(new BufferedReader(source));
  }

  /**
   * Instantiate from a scanner.
   * 
   * @param source
   *          the scanner.
   */
  public ASCIITableReader(Scanner source)
  {
    super(source, '\u001F', '\u001E');
  }
}
