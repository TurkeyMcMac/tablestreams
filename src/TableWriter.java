import java.io.IOException;

public interface TableWriter
{
  public void writeRow(String... row) throws IOException, TableWriteException;
  
  public default void writeTable(TableReader source) throws IOException, TableReadException, TableWriteException {
    String[] row;
    while ((row = source.readRow()) != null) {
      writeRow(row);
    }
  }
}
 
