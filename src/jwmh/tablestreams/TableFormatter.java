package jwmh.tablestreams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A table writer for formatting a table in a human-readable way. This includes
 * padding the rows to a uniform size and adding visible row boundaries. For
 * example, the CSV
 * 
 * <pre>
 * First,Last,Age
 * Joe,John,35
 * Bill,Robertson,60
 * </pre>
 * 
 * becomes
 * 
 * <pre>
 * +-------+-----------+-----+
 * | First | Last      | Age |
 * +-------+-----------+-----+
 * | Joe   | John      | 35  |
 * | Bill  | Robertson | 60  |
 * +-------+-----------+-----+
 * </pre>
 * </p>
 * <p>
 * Unlike most table writers, TableFormatter does not automatically write its
 * output to a file, since it must consider every line at once before doing any
 * printing. To print it to a file, use its {@link #toString()} function, which
 * does the formatting.
 * </p>
 * 
 * @author Jude Melton-Houghton
 *
 */
public class TableFormatter implements TableWriter
{
  private List<String[]> rows;
  private List<Integer> widths;
  private String colSepLeft, colSep, colSepRight;
  private Character horizSep;
  private String intersectionLeft, intersection, intersectionRight;

  /**
   * Construct a new formatter with the default format settings. These can be
   * changed later using setters.
   */
  public TableFormatter()
  {
    horizSep = '-';
    intersectionLeft = "+-";
    intersection = "-+-";
    intersectionRight = "-+";
    colSepLeft = "| ";
    colSep = " | ";
    colSepRight = " |";
    rows = new ArrayList<String[]>();
    widths = new ArrayList<Integer>();
  }

  /**
   * Set the vertical separator for the table to the specified number of space
   * characters.
   * 
   * @param padding
   *          the number of spaces
   */
  public void setPadding(int padding)
  {
    StringBuilder chars = new StringBuilder(padding);
    appendChars(chars, ' ', padding);
    colSep = chars.toString();
  }

  /**
   * Set the central column separator (used everywhere except the edges.)
   * 
   * @param sep
   *          the separator string, or <code>null</code> for no separator
   */
  public void setColumnSeparator(String sep)
  {
    colSep = sep != null ? sep : "";
  }

  /**
   * Set the left column separator (used on the leftmost edge.)
   * 
   * @param sep
   *          the separator string, or <code>null</code> for no separator
   */
  public void setColumnSeparatorLeft(String sep)
  {
    colSepLeft = sep != null ? sep : "";
  }

  /**
   * Set the right column separator (used on the rightmost edge.)
   * 
   * @param sep
   *          the separator string, or <code>null</code> for no separator
   */
  public void setColumnSeparatorRight(String sep)
  {
    colSepRight = sep != null ? sep : "";
  }

  /**
   * Set the left, right, and center column separators. This is equivalent to
   * calling {@link #setColumnSeparator(String)},
   * {@link #setColumnSeparatorLeft(String)}, and
   * {@link #setColumnSeparatorRight(String)} on the appropriate arguments.
   * 
   * @param left
   *          the left separator string, or <code>null</code> for no separator
   * @param center
   *          the center separator string, or <code>null</code> for no separator
   * @param right
   *          the right separator string, or <code>null</code> for no separator
   */
  public void setColumnSeparators(String left, String center, String right)
  {
    setColumnSeparatorLeft(left);
    setColumnSeparator(center);
    setColumnSeparatorRight(right);
  }

  /**
   * Set the character used to form horizontal lines in the table.
   * 
   * @param sep
   *          the separator character
   */
  public void setHorizontalSeparator(Character sep)
  {
    horizSep = sep;
  }

  /**
   * Set the string to use when horizontal and vertical lines intersect in the
   * middle of the table.
   * 
   * @param intersect
   *          the intersection string, where <code>null</code> indicates that
   *          the column separator should be used
   */
  public void setIntersection(String intersect)
  {
    intersection = intersect != null ? intersect : colSep;
  }

  /**
   * Set the string to use when horizontal and vertical lines intersect on the
   * left of the table.
   * 
   * @param intersect
   *          the intersection string, where <code>null</code> indicates that
   *          the left column separator should be used
   */
  public void setIntersectionLeft(String intersect)
  {
    intersectionLeft = intersect != null ? intersect : colSepLeft;
  }

  /**
   * Set the string to use when horizontal and vertical lines intersect on the
   * right of the table.
   * 
   * @param intersect
   *          the intersection string, where <code>null</code> indicates that
   *          the right column separator should be used
   */
  public void setIntersectionRight(String intersect)
  {
    intersectionRight = intersect != null ? intersect : colSepRight;
  }

  /**
   * Set the left, right, and center intersection strings. This is equivalent to
   * calling {@link #setIntersectionLeft(String)},
   * {@link #setIntersection(String)}, and {@link #setIntersectionRight(String)}
   * on the appropriate arguments.
   * 
   * @param left
   *          the left intersection
   * @param center
   *          the central intersection
   * @param right
   *          the right intersection
   */
  public void setIntersections(String left, String center, String right)
  {
    setIntersectionLeft(left);
    setIntersection(center);
    setIntersectionRight(right);
  }

  public void writeRow(String... row)
  {
    rows.add(row);
    while (widths.size() < row.length)
    {
      widths.add(0);
    }
    for (int i = 0; i < row.length; ++i)
    {
      if (row[i].length() > widths.get(i))
      {
        widths.set(i, row[i].length());
      }
    }
  }

  private void appendRow(StringBuilder to, int idx)
  {
    String[] row = rows.get(idx);
    String sep = colSepLeft;
    for (int i = 0; i < row.length; ++i)
    {
      int pad = widths.get(i) - row[i].length();
      to.append(sep);
      to.append(row[i]);
      appendChars(to, ' ', pad);
      sep = colSep;
    }
    for (int i = row.length; i < widths.size(); ++i)
    {
      int pad = widths.get(i);
      to.append(sep);
      appendChars(to, ' ', pad);
    }
    to.append(colSepRight);
    to.append('\n');
  }

  private void appendHorizontalLine(StringBuilder to)
  {
    String intersect = intersectionLeft;
    for (int width : widths)
    {
      to.append(intersect);
      appendChars(to, horizSep, width);
      intersect = intersection;
    }
    to.append(intersectionRight);
    to.append('\n');
  }

  private static void appendChars(StringBuilder to, char ch, int count)
  {
    while (count-- > 0)
    {
      to.append(ch);
    }
  }

  /**
   * Format the rows written to the table so far in a human-readable way. There
   * is no trailing newline.
   * 
   * @return the string representation of the table
   */
  @Override
  public String toString()
  {
    if (rows.isEmpty() || widths.isEmpty())
    {
      return "";
    }
    StringBuilder printed = new StringBuilder();
    int dataRowsStart = 0;
    appendHorizontalLine(printed);
    if (horizSep != null)
    {
      String intersect = intersectionLeft;
      appendRow(printed, 0);
      dataRowsStart = 1;
      for (int width : widths)
      {
        printed.append(intersect);
        appendChars(printed, horizSep, width);
        intersect = intersection;
      }
      printed.append(intersectionRight);
      printed.append('\n');
    }
    for (int i = dataRowsStart; i < rows.size(); ++i)
    {
      appendRow(printed, i);
    }
    appendHorizontalLine(printed);
    printed.deleteCharAt(printed.length() - 1);
    return printed.toString();
  }

}
