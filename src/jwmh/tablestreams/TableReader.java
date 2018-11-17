package jwmh.tablestreams;

import java.io.IOException;
import java.util.Iterator;

/**
 * An object which parses the lines of a table in some format one by one.
 * 
 * @author Jude Melton-Houghton
 * @see TableWriter
 */
public interface TableReader
{
  /**
   * Read a row of cells from whatever the backing resource is.
   * 
   * @return the list of all cells in the row
   * @throws IOException
   *           an error occurred reading from the resource
   * @throws TableReadException
   *           a format-specific parsing error occurred
   */
  public String[] readRow() throws IOException, TableReadException;
}
