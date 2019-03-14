package tests;

import java.io.OutputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import jwmh.tablestreams.TableFormatter;
import jwmh.tablestreams.TableReader;
import jwmh.tablestreams.TableWriter;

public class TableFormatterTest
{

  @Test
  void escapes() throws Exception
  {
    OutputStream out = new OutputStreamSimulator(
      "+-------+-----------+-----+",
      "| First | Last      | Age |",
      "+-------+-----------+-----+",
      "| Joe   | John      | 35  |",
      "| Bill  | Robertson | 60  |",
      "+-------+-----------+-----+"
    );
    TableWriter writer = new TableFormatter();
    writer.writeRow("First", "Last",      "Age");
    writer.writeRow("Joe",   "John",      "35");
    writer.writeRow("Bill",  "Robertson", "60");
    out.write(writer.toString().getBytes());
    out.close();
  }
}
