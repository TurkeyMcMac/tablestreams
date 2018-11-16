package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public abstract class NoEscapeDSVTableWriter implements TableWriter
{
  OutputStream dest;
  boolean started;

  public NoEscapeDSVTableWriter(OutputStream dest)
  {
    this.dest = dest;
    this.started = false;
  }

  public void writeRow(String... cells) throws IOException, TableWriteException
  {

    for (int i = 0; i < cells.length; ++i)
    {
      String cell = cells[i];
      int index;
      if ((index = cell.indexOf(getCellDelimiter())) >= 0
        || (index = cell.indexOf(getRowDelimiter())) >= 0)
      {
        throw new IllegalCharacterException(i, index, cells);
      }
    }
    if (started)
    {
      dest.write(getRowDelimiter());
    }
    started = true;
    dest.write(
      String.join(Character.toString(getCellDelimiter()), cells).getBytes());
  }

  protected abstract char getCellDelimiter();

  protected abstract char getRowDelimiter();

}