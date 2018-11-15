import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DSVTableReader implements TableReader
{
  private BufferedReader source;
  private char delim;

  public DSVTableReader(InputStream source, char delim)
  {
    this(new InputStreamReader(source), delim);
  }

  public DSVTableReader(InputStreamReader source, char delim)
  {
    this(new BufferedReader(source), delim);
  }

  public DSVTableReader(BufferedReader source, char delim)
  {
    this.source = source;
    this.delim = delim;
  }

  public String[] readRow() throws IOException
  {
    String line = source.readLine();
    ArrayList<String> cells = new ArrayList<>();
    do
    {
      line = readCell(line, cells);
    }
    while ((line = readSeparator(line)) != null);
    return cells.toArray(new String[0]);
  }

  private String readCell(String line, ArrayList<String> cells)
    throws IOException
  {
    int start = 0, end = 0, restStart;

    while (end < line.length()
      && (line.charAt(end) == ' ' || line.charAt(end) == '\t'))
    {
      ++end;
    }
    if (end >= line.length())
    {
      cells.add(unescape(line));
      return "";
    }
    if (line.charAt(end) == '"')
    {
      start = end + 1;
      do
      {
        ++end;
        if (end >= line.length())
        {
          line += "\n" + source.readLine();
        }
      }
      while (line.charAt(end) != '"' || line.charAt(end - 1) == '\\');
      restStart = end;
      do
      {
        ++restStart;
      }
      while (restStart < line.length() && line.charAt(restStart) != delim);
    }
    else
    {
      while (end < line.length() && (line.charAt(end) != delim
        || (end > 0 && line.charAt(end - 1) == '\\')))
      {
        ++end;
      }
      restStart = end;
    }
    cells.add(unescape(line.substring(start, end)));
    return line.substring(restStart);
  }

  private String unescape(String cell)
  {
    return cell.replace("\\" + delim, Character.toString(delim))
      .replace("\\\"", "\"").replace("\\\\", "\\");
  }

  private String readSeparator(String line)
  {
    if (line.length() > 0 && line.charAt(0) == delim)
    {
      return line.substring(1);
    }
    return null;
  }

}
