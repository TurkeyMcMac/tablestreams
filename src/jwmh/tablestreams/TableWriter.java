package jwmh.tablestreams;

import java.io.IOException;

/**
 * An object which writes rows to a table in some format one by one.
 * 
 * @author Jude Melton-Houghton
 * @see TableReader
 */
public interface TableWriter
{
  /**
   * Write a single row to whatever the backing resource is.
   * 
   * @param row
   *          the list of cells to write
   * @throws IOException
   *           an exception occurred when writing to the file or whatever
   * @throws TableWriteException
   *           a format-specific translation error occurred
   */
  public void writeRow(String... row) throws IOException, TableWriteException;

  /**
   * Translate all of the rows from a given table reader to the destination
   * table.
   * 
   * @param source
   *          the table from which to read
   * @throws IOException
   *           an exception occurred when interacting with an external resource
   * @throws TableReadException
   *           a format-specific parsing error occurred
   * @throws TableWriteException
   *           a format-specific translation error occurred
   */
  public default void writeTable(TableReader source)
    throws IOException, TableReadException, TableWriteException
  {
    String[] row;
    while ((row = source.readRow()) != null)
    {
      writeRow(row);
    }
  }

  /**
   * Finish writing the current table. This shall not close the backing
   * resource. It may or may not do anything at all. However, it is good to call
   * in a generic context.
   * 
   * @throws IOException
   */
  public default void finishWriting() throws IOException
  {
    // Do nothing.
  }
}
