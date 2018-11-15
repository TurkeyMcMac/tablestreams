import java.util.Arrays;
import java.util.Arrays;

/**
 * Virtual High School
 * 
 * @author type your full name here
 */

public class TableRowSizeException extends TableReadException
{

  private String[] row;
  private int expectedSize;

  public TableRowSizeException()
  {
    super();
  }

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

  public String[] getRow()
  {
    return row;
  }

  public int getExpectedSize()
  {
    return expectedSize;
  }
}
