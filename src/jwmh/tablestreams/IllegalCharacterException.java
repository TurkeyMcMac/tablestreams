package jwmh.tablestreams;

import java.util.Arrays;

/**
 * An exception thrown when one tries to write a delimiter character within a
 * cell in a format which does not support escaping.
 * 
 * @author Jude Melton-Houghton
 */
public class IllegalCharacterException extends TableWriteException
{
  private int cellNumber;
  private int index;
  private String[] cells;

  /**
   * Construct an exception.
   */
  public IllegalCharacterException()
  {
    super();
  }

  IllegalCharacterException(int cellNumber, int index, String... cells)
  {
    super();
    this.cellNumber = cellNumber;
    this.cells = cells;
    this.index = index;
  }

  @Override
  public String getMessage()
  {
    return "The character at index " + index + " in cell " + cellNumber
      + " in the row " + Arrays.toString(cells)
      + " is forbidden by in this table format";
  }

  /**
   * Get the index of the relevant cell in the relevant row.
   * @return the cell index
   */
  public int getCellNumber()
  {
    return cellNumber;
  }

  /**
   * Get the index of the forbidden character in the relevant cell.
   * @return the character index
   */
  public int getIndex()
  {
    return index;
  }

  /**
   * Get the relevant row.
   * @return the row
   */
  public String[] getCells()
  {
    return cells;
  }
}
