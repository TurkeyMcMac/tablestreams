package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;
import jwmh.tablestreams.*;

class StrictColumnTableReaderTest
{

  @Test
  void illegalVariableColumnCount() throws Exception
  {
    try
    {
      assertOutput("a:b:c\nd:e", "\"a\":\"b\":\"c\"\n\"d\":\"e\"");
      fail("Inconsistent line count was supposed to cause an exception.");
    }
    catch (TableReadException e)
    {
    }
  }

  @Test
  void consistentColumnCount() throws Exception
  {
    assertOutput("a:b:c\nd:e:f", "\"a\":\"b\":\"c\"\n\"d\":\"e\":\"f\"");
  }

  void assertOutput(String input, String output) throws Exception
  {
    StringReader source = new StringReader(input);
    TableReader reader =
      new StrictColumnTableReader(new DSVTableReader(source, ':'));
    OutputStreamSimulator dest = new OutputStreamSimulator(output);
    TableWriter writer = new DSVTableWriter(dest, ':');
    writer.writeTable(reader);
    dest.close();
  }
}
