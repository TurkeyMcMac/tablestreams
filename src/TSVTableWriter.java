import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * Virtual High School
 * 
 * @author type your full name here
 */

public class TSVTableWriter implements TableWriter
{
  OutputStream dest;

  public TSVTableWriter(OutputStream dest)
  {
    this.dest = dest;
  }

  public void writeRow(String... cells) throws IOException, TableWriteException
  {
    for (int i = 0; i < cells.length; ++i) {
      String cell = cells[i];
      int index;
      if ((index = cell.indexOf('\n')) >= 0 || (index = cell.indexOf('\t')) >= 0) {
        throw new TSVIllegalCharacterException(i, index, cells);
      }
    }
    dest.write(String.join("\t", cells).getBytes());
    dest.write('\n');
  }

}
