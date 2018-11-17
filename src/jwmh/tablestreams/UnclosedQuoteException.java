/**
 * Virtual High School
 * @author type your full name here
 */

package jwmh.tablestreams;

/**
 * An exception thrown when a DSV file has an unclosed quote (").
 * 
 * @author Jude Melton-Houghton
 */
public class UnclosedQuoteException extends TableReadException
{

  String quoted;

  public UnclosedQuoteException(String quoted)
  {
    super();
    this.quoted = quoted;
  }

  @Override
  public String getMessage()
  {
    return "The quoted section '" + quoted + "' is never closed.";
  }

  /**
   * Get the unclosed quoted section.
   * @return the quoted section
   */
  public String getQuoted()
  {
    return quoted;
  }
}
