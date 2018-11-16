package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;
import jwmh.tablestreams.*;

class TSVTableReaderTest
{

  @Test
  void trailingLine() throws Exception
  {
    assertOutput("a\tb\tc\nd\te\tf\n", "\"a\":\"b\":\"c\"\n\"d\":\"e\":\"f\"");
  }

  @Test
  void noTrailingLine() throws Exception
  {
    assertOutput("a\tb\tc\nd\te\tf", "\"a\":\"b\":\"c\"\n\"d\":\"e\":\"f\"");
  }

  void assertOutput(String input, String output) throws Exception
  {
    StringReader source = new StringReader(input);
    TableReader reader = new TSVTableReader(source);
    OutputStreamSimulator dest = new OutputStreamSimulator(output);
    TableWriter writer = new DSVTableWriter(dest, ':');
    writer.writeTable(reader);
    dest.close();
  }

}
