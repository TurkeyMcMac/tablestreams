package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * A writer of Tab-Separated Values.
 * 
 * @author Jude Melton-Houghton
 * @see <a href=
 *      "https://www.iana.org/assignments/media-types/text/tab-separated-values">The
 *      TSV specification.</a>
 * @see TSVTableReader
 */
public class TSVTableWriter extends NoEscapeDSVTableWriter
{
  /**
   * Construct a writer to a destination.
   * 
   * @param dest
   *          The file or other place to write information to.
   */
  public TSVTableWriter(OutputStream dest)
  {
    super(dest, '\t', '\n');
  }
}
