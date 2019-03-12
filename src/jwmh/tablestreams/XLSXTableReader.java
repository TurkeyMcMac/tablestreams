package jwmh.tablestreams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XLSXTableReader implements TableReader
{
  private NodeList rows;
  private int index;
  private int width, height;

  private static List<int[]> parseDimensions(String dimension)
  {
    List<int[]> dims = new ArrayList<int[]>(2);
    String[] dimensions = dimension.split(":");
    for (int i = 0; i < Math.min(dimensions.length, 2); ++i)
    {
      dims.add(parseLocation(dimensions[i]));
    }
    return dims;
  }

  private static int[] parseLocation(String string)
  {
    int column = 0, row = 0;
    char nextChar;
    int i;
    for (i = 0; i < string.length(); ++i)
    {
      nextChar = string.charAt(i);
      if (Character.isAlphabetic(nextChar))
      {
        column += Character.toLowerCase(nextChar) - 'a' + 1;
      }
      else
      {
        break;
      }
    }
    for (; i < string.length(); ++i)
    {
      nextChar = string.charAt(i);
      if (Character.isDigit(nextChar))
      {
        row *= 10;
        row += nextChar - '0';
      }
    }
    int[] rowColumn = {row, column};
    return rowColumn;
  }

  public XLSXTableReader(File file) throws IOException, SAXException
  {
    Document doc = null;
    Node sheetData, dimension;
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(file);
    }
    catch (ParserConfigurationException e)
    {
      // Hopefully this never happens.
      assert "Unreachable" == null;
      return;
    }
    sheetData = doc.getElementsByTagName("sheetData").item(0);
    if (sheetData != null)
    {
      rows = sheetData.getChildNodes();
    }
    width = 0;
    height = 0;
    dimension = doc.getElementsByTagName("dimension").item(0);
    if (dimension != null)
    {
      Node dimAttr = dimension.getAttributes().getNamedItem("ref");
      if (dimAttr != null)
      {
        List<int[]> dimensions = parseDimensions(dimAttr.getNodeValue());
        if (dimensions.size() >= 2)
        {
          width = dimensions.get(1)[1] - dimensions.get(0)[1];
          height = dimensions.get(1)[0] - dimensions.get(0)[0];
          if (width > 0) {
            width += 1;
          }
        }
      }
    }
    System.out.println("wh " + width + ", " + height);
    index = 0;
  }

  public String[] readRow() throws IOException, TableReadException
  {
    List<String> parsedRow;
    String[] rowArray;
    Node row = rows.item(index);
    if (row == null) {
      return null;
    }
    NodeList cells = row.getChildNodes();
    parsedRow = new ArrayList<String>(width);
    for (int i = 0; i < cells.getLength(); ++i)
    {
      Node cell = cells.item(i);
      if (cell.getNodeName() == "c")
      {
        Node value = cell.getFirstChild();
        if (value.getNodeName() == "v")
        {
          parsedRow.add(value.getTextContent());
        }
      }
    }
    ++index;
    rowArray = new String[parsedRow.size()];
    return parsedRow.toArray(rowArray);
  }

  public int getExpectedWith()
  {
    return width;
  }

  public int getExpectedHeight()
  {
    return height;
  }
}
