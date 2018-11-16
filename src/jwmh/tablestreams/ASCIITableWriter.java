package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

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
 