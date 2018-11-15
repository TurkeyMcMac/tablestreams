import java.util.Arrays;

public class Main
{
  public static void main(String[] args) throws Throwable
  {
    TableReader tr = new StrictColumnTableReader(new DSVTableReader(System.in, ','));
    TableWriter tw = new TSVTableWriter(System.out);
    tw.writeTable(tr);
  }
}
