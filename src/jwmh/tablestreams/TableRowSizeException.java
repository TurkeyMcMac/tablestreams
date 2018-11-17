package jwmh.tablestreams;

import java.util.Arrays;

/**
 * An exception thrown when a StrictColumnTableReader finds the wrong number of
 * cells in a row.
 * 
 * @author Jude Melton-Houghton
 * @see StrictColumnTableReader
 */
public class TableRowSizeException extends TableReadException
{

  private String[] row;
  private int expectedSize;

  public TableRowSizeException()
  {
    super();
  }

  /**
   * Instantiate using a row and a the size expected.
   * 
   * @param row
   *          the row
   * @param expectedSize
   *          the cell count expected
   */
  public TableRowSizeException(String[] row, int expectedSize)
  {
    super();
    this.row = row;
    this.expectedSize = expectedSize;
  }

  @Override
  public String getMessage()
  {
    return "The row " + Arrays.toString(row)
      + " does not have the expected cell count of " + expectedSize;
  }

  /**
   * Get the relevant row.
   * 
   * @return the row
   */
  public String[] getRow()
  {
    return row;
  }

  /**
   * Get the expected row size
   * 
   * @return the size
   */
  public int getExpectedSize()
  {
    return expectedSize;
  }
}
