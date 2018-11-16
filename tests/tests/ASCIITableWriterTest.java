package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import jwmh.tablestreams.*;

class ASCIITableWriterTest
{

  @Test
  void unescape() throws Exception
  {
    assertOutput("\\\":\"\\\\\":\\:", "\"\u001F\\\u001F:");
  }

  @Test
  void illegalRowDelimiter() throws Exception
  {
    illegalInput("\"\u001E\"");
  }

  @Test
  void illegalCellDelimiter() throws Exception
  {
    illegalInput("\"\u001F\"");
  }

  private void illegalInput(String input) throws Exception
  {
    try
    {
      assertOutput(input, null);
      fail("The input " + input + " was supposed to cause an exception.");
    }
    catch (TableWriteException e)
    {
    }
  }

  void assertOutput(String input, String output) throws Exception
  {
    StringReader source = new StringReader(input);
    TableReader reader = new DSVTableReader(source, ':');
    OutputStreamSimulator dest = new OutputStreamSimulator(output);
    TableWriter writer = new ASCIITableWriter(dest);
    writer.writeTable(reader);
    dest.close();
  }
}
 