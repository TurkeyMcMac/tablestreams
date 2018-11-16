import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class TSVTableWriter implements TableWriter
{
  OutputStream dest;
  boolean started;

  public TSVTableWriter(OutputStream dest)
  {
    this.dest = dest;
    this.started = false;
  }

  public void writeRow(String... cells) throws IOException, TableWriteException
  {

    for (int i = 0; i < cells.length; ++i)
    {
      String cell = cells[i];
      int index;
      if ((index = cell.indexOf('\n')) >= 0
        || (index = cell.indexOf('\t')) >= 0)
      {
        throw new TSVIllegalCharacterException(i, index, cells);
      }
    }
    if (started)
    {
      dest.write('\n');
    }
    started = true;
    dest.write(String.join("\t", cells).getBytes());
  }

}
