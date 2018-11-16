package jwmh.tablestreams;

import java.util.List;
import java.util.ArrayList;

public class TableCollector implements TableWriter
{
  private List<String[]> list;

  public TableCollector(ArrayList<String[]> list)
  {
    this.list = list;
  }

  public TableCollector()
  {
    this(new ArrayList<>());
  }

  public List<String[]> getList()
  {
    return list;
  }

  public void setList(List<String[]> list)
  {
    this.list = list;
  }

  public void writeRow(String... row)
  {
    list.add(row);
  }

}
