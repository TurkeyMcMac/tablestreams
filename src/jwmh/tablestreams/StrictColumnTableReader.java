package jwmh.tablestreams;

import java.io.Closeable;
import java.io.IOException;

/**
 * A wrapper for another table reader which ensures that every row read has the
 * same number of columns.
 * 
 * @author Jude Melton-Houghton
 */
public class StrictColumnTableReader implements TableReader, Closeable
{
  private TableReader source;
  private int columnCount;

  /**
   * Instantiate the wrapper, requiring a certain column count.
   * 
   * @param source
   *          the place to get read rows
   * @param columnCount
   *          the number of cells each row must have
   */
  public StrictColumnTableReader(TableReader source, int columnCount)
  {
    this.source = source;
    this.columnCount = columnCount;
  }

  /**
   * Instantiate the wrapper, requiring all rows to have the same column count.
   * 
   * @param source
   *          the place to get read rows
   */
  public StrictColumnTableReader(TableReader source)
  {
    this(source, -1);
  }

  /**
   * Get the required number of columns.
   * 
   * @return the number of columns, or -1 if the number has yet to be decided.
   */
  public int getColumnCount()
  {
    return columnCount;
  }

  public String[] readRow() throws IOException, TableReadException
  {
    String[] row = source.readRow();
    if (row == null)
    {
      return null;
    }
    if (columnCount < 0)
    {
      columnCount = row.length;
    }
    else if (row.length != columnCount)
    {
      throw new TableRowSizeException(row, columnCount);
    }
    return row;
  }

  /**
   * Close the backing table if it needs to be closed.
   */
  public void close() throws IOException
  {
    if (source instanceof Closeable)
    {
      ((Closeable) source).close();
    }
  }
}
