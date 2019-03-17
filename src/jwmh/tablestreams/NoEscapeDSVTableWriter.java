package jwmh.tablestreams;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * The parent class of writer of delimited value formats which disallow
 * escaping, namely TSV and the one which uses built-in ASCII delimiter
 * characters.
 * 
 * @author Jude Melton-Houghton
 * @see NoEscapeDSVTableReader
 */
public class NoEscapeDSVTableWriter implements TableWriter, Closeable
{
  private OutputStream dest;
  private boolean started;
  private char cellDelim, rowDelim;

  /**
   * Construct a writer to a destination.
   * 
   * @param dest
   *          The file or other place to write information to.
   * @param cellDelim
   *          the cell delimiter character (a tab for TSV)
   * @param rowDelim
   *          the row delimiter character (a newline for TSV)
   */
  public NoEscapeDSVTableWriter(OutputStream dest, char cellDelim,
    char rowDelim)
  {
    this.dest = dest;
    this.started = false;
    this.cellDelim = cellDelim;
    this.rowDelim = rowDelim;
  }

  public void writeRow(String... cells) throws IOException, TableWriteException
  {

    for (int i = 0; i < cells.length; ++i)
    {
      String cell = cells[i];
      int index;
      if ((index = cell.indexOf(cellDelim)) >= 0
        || (index = cell.indexOf(rowDelim)) >= 0)
      {
        throw new IllegalCharacterException(i, index, cells);
      }
    }
    if (started)
    {
      dest.write(rowDelim);
    }
    started = true;
    dest.write(String.join(Character.toString(cellDelim), cells).getBytes());
  }

  public void close() throws IOException
  {
    dest.close();
  }
}