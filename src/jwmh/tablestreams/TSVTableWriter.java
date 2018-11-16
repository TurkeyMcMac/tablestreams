package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class TSVTableWriter extends NoEscapeDSVTableWriter
{
  public TSVTableWriter(OutputStream dest)
  {
    super(dest);
  }

  protected char getCellDelimiter()
  {
    return '\t';
  }

  protected char getRowDelimiter()
  {
    return '\n';
  }

}
