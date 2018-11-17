package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import jwmh.tablestreams.*;

class DSVTableReaderTest
{
  @Test
  void escape() throws Exception
  {
    assertOutput("\\\\,\\\\\\,\\\"\"a\",\\,",
      "\"\\\\\":\"\\\\,\\\"\\\"a\\\"\":\",\"");
  }

  @Test
  void internalNewlines() throws Exception
  {
    assertOutput("_,\"_\n_\",_\n_", "\"_\":\"_\n_\":\"_\"\n\"_\"");
  }

  @Test
  void missingClosingQuote() throws Exception
  {
    try
    {
      assertOutput("\"foo\n\n", "\"foo\n\n");
      fail("Missing closing quotes are supposed to cause exceptions.");
    }
    catch (UnclosedQuoteException e)
    {
    }
  }

  @Test
  void completelyEmpty() throws Exception
  {
    assertOutput("", "");
  }

  @Test
  void emptyCells() throws Exception
  {
    assertOutput(",\"\",\n,\"\" ", "\"\":\"\":\"\"\n\"\":\"\"");
  }

  @Test
  void whitespace() throws Exception
  {
    assertOutput(" _ , \" _ \" , _ ", "\" _ \":\" _ \":\" _ \"");
  }

  private void assertOutput(String input, String output) throws Exception
  {
    StringReader source = new StringReader(input);
    TableReader reader = new DSVTableReader(source, ',');
    OutputStreamSimulator dest = new OutputStreamSimulator(output);
    TableWriter writer = new DSVTableWriter(dest, ':');
    writer.writeTable(reader);
    dest.close();
  }
}
