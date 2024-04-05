package orderservice.exception;

public class CustomExceptionMessage extends Exception  {

	public CustomExceptionMessage(String message) {
		super(message);
		
	}

	public CustomExceptionMessage(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CustomExceptionMessage(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	

	public CustomExceptionMessage(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
