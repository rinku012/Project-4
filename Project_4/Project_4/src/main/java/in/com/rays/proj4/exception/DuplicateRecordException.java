package in.com.rays.proj4.exception;

public class DuplicateRecordException extends Exception {
	
		/**
		 * @param msg
		 * error msg
		 */
		public DuplicateRecordException(String msg){
			super(msg);
		}
}
