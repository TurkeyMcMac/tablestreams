package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import jwmh.tablestreams.*;

class TableFeederAndCollectorTest
{

  @Test
  void test() throws Exception
  {
    List<String[]> original = List.of(
      new String[] {"a", "b", "c"},
      new String[] {"d", "e", "f"},
      new String[] {}
    );
    TableFeeder reader = new TableFeeder(original);
    TableCollector writer = new TableCollector();
    writer.writeTable(reader);
    List<String[]> writtenList = writer.getList();
    if (original.size() != writtenList.size())
    {
      fail("Copying was inaccurate.");
    }
    for (int i = 0; i < original.size(); ++i)
    {
      if (!Arrays.deepEquals(original.get(i), writtenList.get(i)))
      {
        fail("Copying was inaccurate.");
      }
    }
  }

}
