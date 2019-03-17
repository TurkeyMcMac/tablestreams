package jwmh.tablestreams;

/**
 * An exception thrown when one tries to write a row to an XLSX file before
 * creating a spreadsheet.
 * 
 * @author Jude Melton-Houghton
 */
public class XLSXNotBegunException extends TableWriteException
{
  public XLSXNotBegunException()
  {
    super();
  }

  public XLSXNotBegunException(String message)
  {
    super(message);
  }
}
