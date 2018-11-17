package jwmh.tablestreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The parent class of readers of delimited value formats which disallow
 * escaping, namely TSV and the one which uses built-in ASCII delimiter
 * characters.
 * 
 * @author Jude Melton-Houghton
 * @see NoEscapeDSVTableWriter
 */
public abstract class NoEscapeDSVTableReader implements TableReader
{
  protected Scanner source;

  public NoEscapeDSVTableReader(Scanner source)
  {
    this.source = source;
    this.source.useDelimiter(getRowDelimiterPattern());
  }

  public String[] readRow() throws IOException
  {
    try
    {
      String nextLine = source.next();
      return getCellDelimiterPattern().split(nextLine);
    }
    catch (NoSuchElementException e)
    {
      return null;
    }
  }

  protected abstract Pattern getCellDelimiterPattern();

  protected abstract Pattern getRowDelimiterPattern();
}
