package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * A reader of Delimiter-Separated Values.
 * 
 * @author Jude Melton-Houghton
 * @see DSVTableWriter
 * @see <a href="https://tools.ietf.org/html/rfc4180">The CSV specification</a>
 *      (DSV where the delimiter is ',')
 */
public class DSVTableReader implements TableReader, Closeable
{
  private BufferedReader source;
  private char delim;

  /**
   * Instantiate from an input stream.
   * 
   * @param source
   *          the input stream
   * @param delim
   *          the delimiter
   */
  public DSVTableReader(InputStream source, char delim)
  {
    this(new InputStreamReader(source), delim);
  }

  /**
   * Instantiate from a reader.
   * 
   * @param source
   *          the reader
   * @param delim
   *          the delimiter
   */
  public DSVTableReader(Reader source, char delim)
  {
    this(new BufferedReader(source), delim);
  }

  /**
   * Instantiate from a buffered reader.
   * 
   * @param source
   *          the reader
   * @param delim
   *          the delimiter
   */
  public DSVTableReader(BufferedReader source, char delim)
  {
    this.source = source;
    this.delim = delim;
  }

  public String[] readRow() throws IOException, TableReadException
  {
    String line = source.readLine();
    if (line == null)
    {
      return null;
    }
    ArrayList<String> cells = new ArrayList<>();
    do
    {
      line = readCell(line, cells);
    }
    while (line != null);
    return cells.toArray(new String[0]);
  }

  private String readCell(String line, ArrayList<String> cells)
    throws IOException, TableReadException
  {
    int start = 0, end = 0, restStart;

    while (end < line.length() && line.charAt(end) == ' ')
    {
      ++end;
    }
    if (end >= line.length())
    {
      cells.add(line);
      return null;
    }
    if (line.charAt(end) == '"')
    {
      start = end + 1;
      do
      {
        ++end;
        if (end >= line.length())
        {
          String nextLine = source.readLine();
          if (nextLine == null)
          {
            throw new UnclosedQuoteException(line.substring(start));
          }
          line += "\n" + nextLine;
        }
      }
      while (!hasReachedEnd(line, end, '"'));
      restStart = end;
      do
      {
        ++restStart;
      }
      while (restStart < line.length() && line.charAt(restStart) != delim);
      ++restStart;
    }
    else
    {
      while (!hasReachedEnd(line, end, delim))
      {
        ++end;
      }
      restStart = end + 1;
    }
    cells.add(unescape(line.substring(start, end)));
    return restStart <= line.length() ? line.substring(restStart) : null;
  }

  private boolean hasReachedEnd(String line, int index, char stopAt)
  {
    if (index >= line.length())
    {
      return true;
    }
    if (line.charAt(index) != stopAt)
    {
      return false;
    }
    int backslashCount = 0;
    for (int i = index - 1; i >= 0 && line.charAt(i) == '\\'; --i)
    {
      ++backslashCount;
    }
    return backslashCount % 2 == 0;
  }

  private String unescape(String cell)
  {
    return cell.replace("\\" + delim, Character.toString(delim))
      .replace("\\\"", "\"").replace("\\\\", "\\");
  }

  public void close() throws IOException
  {
    source.close();
  }

}
