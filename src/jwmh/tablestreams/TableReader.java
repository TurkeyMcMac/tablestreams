package jwmh.tablestreams;

import java.io.IOException;
import java.util.Iterator;

public interface TableReader
{
  public String[] readRow() throws IOException, TableReadException;
}
 