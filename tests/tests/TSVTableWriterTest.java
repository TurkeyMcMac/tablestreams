package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import jwmh.tablestreams.*;

class TSVTableWriterTest
{

  @Test
  void unescape() throws Exception
  {
    assertOutput("\\\":\"\\\\\":\\:", "\"\t\\\t:");
  }

  @Test
  void illegalNewline() throws Exception
  {
    illegalInput("\"\n\"");
  }

  @Test
  void illegalTab() throws Exception
  {
    illegalInput("\"\t\"");
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
    TableWriter writer = new TSVTableWriter(dest);
    writer.writeTable(reader);
    dest.close();
  }
}
