package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ASCIITableReader extends NoEscapeDSVTableReader
{
  private static final Pattern ROW_DELIMITER = Pattern.compile("\u001E");
  private static final Pattern CELL_DELIMITER = Pattern.compile("\u001F");

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
    super(source);
  }

  protected Pattern getCellDelimiterPattern()
  {
    return CELL_DELIMITER;
  }

  protected Pattern getRowDelimiterPattern()
  {
    return ROW_DELIMITER;
  }
}
