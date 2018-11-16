package jwmh.tablestreams;

import java.util.Arrays;

public class TSVIllegalCharacterException extends TableWriteException
{
  private int cellNumber;
  private int index;
  private String[] cells;

  public TSVIllegalCharacterException()
  {
    super();
  }

  TSVIllegalCharacterException(int cellNumber, int index, String... cells)
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
      + " is forbidden by the TSV standard";
  }

  public int getCellNumber()
  {
    return cellNumber;
  }

  public int getIndex()
  {
    return index;
  }

  public String[] getCells()
  {
    return cells;
  }
}
