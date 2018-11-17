package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * A writer which uses ASCII character 31 "INFORMATION SEPARATOR ONE" to
 * separate cells and character 30 "INFORMATION SEPARATOR TWO" to separate rows.
 * 
 * @author Jude Melton-Houghton
 * @see ASCIITableReader
 */
public class ASCIITableWriter extends NoEscapeDSVTableWriter
{
  public ASCIITableWriter(OutputStream dest)
  {
    super(dest);
  }

  protected char getCellDelimiter()
  {
    return '\u001F';
  }

  protected char getRowDelimiter()
  {
    return '\u001E';
  }

}
