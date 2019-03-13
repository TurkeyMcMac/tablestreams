package jwmh.tablestreams;

import java.util.List;

/**
 * A reader which reads rows from an internal list one by one. Each row is
 * cloned when it is read with readRow.
 * 
 * @author Jude Melton-Houghton
 * @see TableCollector
 */
public class TableFeeder implements TableReader
{

  private List<String[]> source;
  private int reading;

  /**
   * Instantiate from a list
   * 
   * @param source
   *          the backing list
   */
  public TableFeeder(List<String[]> source)
  {
    this.source = source;
  }

  public String[] readRow()
  {
    String[] row = null;
    if (reading < source.size())
    {
      row = source.get(reading);
      if (row != null)
      {
        row = row.clone();
      }
      else
      {
        row = new String[0];
      }
      ++reading;
    }
    return row;
  }

}
