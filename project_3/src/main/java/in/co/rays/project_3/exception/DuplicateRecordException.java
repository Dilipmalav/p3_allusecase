package in.co.rays.project_3.exception;

public class DuplicateRecordException extends Exception{
	   /**
	    * 
	    * @author Dilip Malav
	     */
	private static final long serialVersionUID = 1L;

	public DuplicateRecordException(String msg){
		super(msg);
	}
}
