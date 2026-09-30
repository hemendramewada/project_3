package in.co.rays.project_3.exception;

/**
 * @author Rajendra Singh
 */
public class RecordNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String msg){
		super(msg);
	}
}
