package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The parent class of readers of delimited value formats which disallow
 * escaping, namely TSV and the one which uses built-in ASCII delimiter
 * characters.
 * 
 * @author Jude Melton-Houghton
 * @see NoEscapeDSVTableWriter
 */
public class NoEscapeDSVTableReader implements TableReader, Closeable
{
  private Scanner source;
  private String cellDelim;

  /**
   * Instantiate a new reader from the source.
   * 
   * @param source
   *          the source from which to read
   * @param cellDelim
   *          the cell delimiter character (a tab for TSV)
   * @param rowDelim
   *          the row delimiter character (a newline for TSV)
   */
  public NoEscapeDSVTableReader(Scanner source, char cellDelim, char rowDelim)
  {
    this.source = source;
    this.source.useDelimiter(Character.toString(rowDelim));
    this.cellDelim = Character.toString(cellDelim);
  }

  public String[] readRow() throws IOException
  {
    try
    {
      String nextLine = source.next();
      return nextLine.split(cellDelim);
    }
    catch (NoSuchElementException e)
    {
      return null;
    }
  }

  public void close() throws IOException
  {
    source.close();
  }
}
