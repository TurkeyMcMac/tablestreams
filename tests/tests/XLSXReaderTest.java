package tests;

import java.io.File;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import jwmh.tablestreams.*;

public class XLSXReaderTest
{
  @Test
  void test() throws Exception
  {
    String expectedStr =
      String.join("\n", // line break
        "+------------+-----------+------------+",
        "| First Name | Last Name | DOB        |",
        "+------------+-----------+------------+",
        "| Benothy    | Bill      | 2000-05-05 |",
        "| Amelia     | Archibald | 1990-01-01 |",
        "| Harold     | Hewer     | 1950-11-11 |",
        "| Arthur     | Archibald | 1990-01-01 |",
        "| Zach       | Zimbabwe  | 1800-12-12 |",
        "+------------+-----------+------------+");
    TableReader reader =
      new XLSXReader(new File("test-data.xlsx")).getTable("DEF's'");
    TableWriter writer = new TableFormatter();
    writer.writeTable(reader);
    writer.finishWriting();
    String stringified = writer.toString();
    if (!stringified.equals(expectedStr))
    {
      fail("Expected table\n" + expectedStr + "\nGot table\n" + stringified);
    }
  }
}
