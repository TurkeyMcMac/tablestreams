package jwmh.tablestreams;

/**
 * An exception thrown when a translation error occurs as a TableWriter writes.
 * 
 * @author Jude Melton-Houghton
 * @see TableWriter#writeRow(String...)
 */
public class TableWriteException extends Exception
{

  /**
   * Instantiate with a message string.
   * 
   * @param message
   *          the message string
   */
  public TableWriteException(String message)
  {
    super(message);
  }

  public TableWriteException()
  {
    super();
  }

}
