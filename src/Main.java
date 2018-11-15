import java.util.Arrays;

/**
 * Virtual High School
 * 
 * @author type your full name here
 */

public class Main
{
  public static void main(String[] args) throws Throwable
  {
    TableReader tr = new StrictColumnTableReader(new TableFeeder(Arrays.asList(
      new String[] {"a", "b", "c"},
      new String[] {"d", "e"}
    )));
    TableWriter tw = new TSVTableWriter(System.out);
    tw.writeTable(tr);
  }
}
