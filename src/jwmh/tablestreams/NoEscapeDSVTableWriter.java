package jwmh.tablestreams;

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
public abstract class NoEscapeDSVTableWriter implements TableWriter
{
  OutputStream dest;
  boolean started;

  /**
   * Construct a writer to a destination.
   * 
   * @param dest
   *          The file or other place to write information to.
   */
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

  /**
   * Get the character between cells.
   * 
   * @return the cell delimiter.
   */
  protected abstract char getCellDelimiter();

  /**
   * Get the character between rows.
   * 
   * @return the row delimiter.
   */
  protected abstract char getRowDelimiter();

}