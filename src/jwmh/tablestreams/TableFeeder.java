package jwmh.tablestreams;

import java.util.List;

/**
 * A reader which reads rows from an internal list one by one.
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
   * @param source the backing list
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
      ++reading;
    }
    return row;
  }

}
