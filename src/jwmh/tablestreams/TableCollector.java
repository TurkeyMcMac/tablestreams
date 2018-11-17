package jwmh.tablestreams;

import java.util.List;
import java.util.ArrayList;

/**
 * A writer which collects all written lines into a list.
 * 
 * @author Jude Melton-Houghton
 * @see TableFeeder
 */
public class TableCollector implements TableWriter
{
  private List<String[]> list;

  /**
   * Instantiate from a given list
   * 
   * @param list
   *          the list to use
   */
  public TableCollector(ArrayList<String[]> list)
  {
    this.list = list;
  }

  /**
   * Instantiate and automatically allocate a list.
   */
  public TableCollector()
  {
    this(new ArrayList<>());
  }

  /**
   * Get the currently used list.
   * 
   * @return the current list
   */
  public List<String[]> getList()
  {
    return list;
  }

  /**
   * Set the list to write into. This can be called at any time.
   * 
   * @param list
   *          the new list
   */
  public void setList(List<String[]> list)
  {
    this.list = list;
  }

  public void writeRow(String... row)
  {
    list.add(row);
  }

}
