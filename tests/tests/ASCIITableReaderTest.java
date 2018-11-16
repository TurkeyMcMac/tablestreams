package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import jwmh.tablestreams.*;

class ASCIITableReaderTest
{

  @Test
  void test() throws Exception
  {
    assertOutput("a\u001Fb\u001Fc\u001Ed\u001Fe\u001Ff",
      "\"a\":\"b\":\"c\"\n\"d\":\"e\":\"f\"");
  }

  private void assertOutput(String input, String output) throws Exception
  {
    StringReader source = new StringReader(input);
    TableReader reader = new ASCIITableReader(source);
    OutputStreamSimulator dest = new OutputStreamSimulator(output);
    TableWriter writer = new DSVTableWriter(dest, ':');
    writer.writeTable(reader);
    dest.close();
  }

}
