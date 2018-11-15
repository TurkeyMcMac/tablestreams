import java.io.IOException;

/**
 * Virtual High School
 * @author type your full name here
 */

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
 