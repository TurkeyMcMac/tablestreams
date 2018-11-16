package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import jwmh.tablestreams.*;

class DSVTableWriterTest
{

  @Test
  void escapes() throws Exception
  {
    TableReader reader =
      new TableFeeder(List.of(
        new String[] {"\\", "\"", ","},
        new String[] {"\\\""}
      ));
    TableWriter writer = new DSVTableWriter(
      new OutputStreamSimulator(
        "\"\\\\\",\"\\\"\",\",\"",
        "\"\\\\\\\"\""
      ),
      ',');
    writer.writeTable(reader);
  }
  
  @Test
  void emptyLines() throws Exception
  {
    TableReader reader =
      new TableFeeder(List.of(
        new String[] {""},
        new String[] {}
      ));
    TableWriter writer = new DSVTableWriter(
      new OutputStreamSimulator("\"\"", ""),
      ',');
    writer.writeTable(reader);
  }

}
