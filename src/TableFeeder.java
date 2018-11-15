import java.util.List;

public class TableFeeder implements TableReader
{

  private List<String[]> source;
  private int reading;

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
