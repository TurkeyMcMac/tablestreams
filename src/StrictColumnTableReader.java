import java.io.IOException;

public class StrictColumnTableReader implements TableReader
{
  private TableReader source;
  private int columnCount;
  
  public StrictColumnTableReader(TableReader source, int columnCount) {
    this.source = source;
    this.columnCount = columnCount;
  }
  
  public StrictColumnTableReader(TableReader source) {
    this(source, -1);
  }
  
  public int getColumnCount() {
    return columnCount;
  }

  public String[] readRow() throws IOException, TableReadException
  {
    String[] row = source.readRow();
    if (row == null) {
      return null;
    }
    if (columnCount < 0) {
      columnCount = row.length;
    } else if (row.length != columnCount) {
      throw new TableRowSizeException(row, columnCount);
    }
    return row;
  }

}
 