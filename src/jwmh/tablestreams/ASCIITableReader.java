package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ASCIITableReader implements TableReader
{
  private static final Pattern ROW_DELIMITER = Pattern.compile("\u001E");
  private static final Pattern CELL_DELIMITER = Pattern.compile("\u001F");

  private Scanner source;

  public ASCIITableReader(InputStream source)
  {
    this(new InputStreamReader(source));
  }
  public ASCIITableReader(BufferedReader source)
  {
    this(new Scanner(source));
  }

  public ASCIITableReader(Reader source)
  {
    this(new BufferedReader(source));
  }

  public ASCIITableReader(Scanner source)
  {
    this.source = source;
    this.source.useDelimiter(ROW_DELIMITER);
  }

  public String[] readRow() throws IOException
  {
    try
    {
      String nextLine = source.next();
      return CELL_DELIMITER.split(nextLine);
    }
    catch (NoSuchElementException e)
    {
      return null;
    }
  }
}
