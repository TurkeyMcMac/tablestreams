package jwmh.tablestreams;

/**
 * Any table parsing error. This exists for the purpose of exception catching
 * specificity.
 * 
 * @author Jude Melton-Houghton
 * @see TableReader#readRow()
 */
public class TableReadException extends Exception
{
  /**
   * Instantiate with an error message.
   * @param message the message string
   */
  public TableReadException(String message)
  {
    super(message);
  }

  public TableReadException()
  {
    super();
  }
}
