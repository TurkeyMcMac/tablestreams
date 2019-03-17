package jwmh.tablestreams;

import java.util.List;

/**
 * A reader which reads rows from an internal list one by one. Each row upon
 * reading is the same array as was provided upon instantiation .
 * 
 * @author Jude Melton-Houghton
 * @see TableCollector
 */
public class NonCloningTableFeeder implements TableReader
{

  private List<String[]> source;
  private int reading;

  /**
   * Instantiate from a list
   * 
   * @param source
   *          the backing list
   */
  public NonCloningTableFeeder(List<String[]> source)
  {
    this.source = source;
  }

  public String[] readRow()
  {
    String[] row = null;
    if (reading < source.size())
    {
      row = source.get(reading);
      if (row == null)
      {
        row = new String[0];
      }
      ++reading;
    }
    return row;
  }

}
