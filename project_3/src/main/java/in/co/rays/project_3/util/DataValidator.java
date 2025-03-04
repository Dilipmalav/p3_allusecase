package in.co.rays.project_3.util;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * DataValidator class is used to validate the data entered by user
 * 
 * @author Dilip Malav
 *
 */
public class DataValidator {
	 
	
	 public static boolean isFloat(String val) {
			if (isNotNull(val)) {
				try {
					float i = Float.parseFloat(val);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			} else {
				return false;
			}
		}
	 
	 public static boolean isValidAge(String val) {

			boolean pass = false;
			if (isDate(val)) {
				Date cdate = new Date();
				try {
					Date userdate = DataUtility.formatter.parse(val);
					int age = cdate.getYear() - userdate.getYear();
					System.out.println("final age  " + age);
					if (age >= 13) {
						pass = true;
					}
				} catch (ParseException e) {

				}
			}

			return pass;
		}

	public static boolean isLetter(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";

		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	
	public static boolean validateAlphanumeric(String input) {
	    // Check for empty input
	    if (input == null || input.isEmpty()) {
	        return false;
	    }

	    // Check for alphanumeric with spaces
	    if (!input.matches("^[a-zA-Z0-9 ]+$")) {
	        return false;
	    }

	    return true;
	}

	public static boolean isNull(String val){
		System.out.println("oooooooooooooooooooooo");
		if(val==null || val.trim().length()==0){
			return true;
		}else{
			return false ;
		}
	}
	public static boolean isNotNull(String val){
		return !isNull(val);
		
	}
	public static boolean isInteger(String val){
		if(isNotNull(val)){
			try{
				int i=Integer.parseInt(val);
				return true;
			}catch(NumberFormatException e){
					return false;
			}
		}else{
			return false;
		}
	}
	public static boolean isDouble(String val) {
	    if (isNotNull(val)) {
	        try {
	            Double.parseDouble(val);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}

	
	public static boolean isLong(String val){
		if(isNotNull(val)){
			try{
				long i=Long.parseLong(val);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else{
			return false;
		}
	} 
	

	  public static boolean isEmail(String val) {
		  
	        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                                    
	        if (isNotNull(val)) {
	            try {
	                return val.matches(emailreg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }

	
	public static boolean isDate(String val){
	    
    	Date today = new Date();
    	Date enterDate = DataUtility.getDate(val);
    	
    	int age = today.getYear() - enterDate.getYear();

    	if(age > 13 && isNotNull(val)){
    		return true;
    	}else{
    		return false;							
    	}
    }
	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";

		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			
		}
			return false;
		}
	   
	   
		public static boolean isTooLong(String val1, int maxLength) {
		    if (isNotNull(val1)) {
		        return val1.length() >  maxLength;
		    } else {
		        return false;
		    }
		
	}
		


	
	public static boolean isPassword(String val) {

		String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";

		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Phone No.
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if value of Mobile No is 10
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isPhoneLength(String val) {

		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Date
	 * 
	 * @param val
	 * @return
	 */
	
	/**
	 * Checks if Date is on Sunday
	 * 
	 * @param val
	 * @return
	 */
	
	public static boolean isRollNo(String val) {

		String rollreg = "[a-zA-Z]{2}[0-9]{3}";

		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}
	public static boolean isAge(String val){
	    
    	Date today = new Date();
    	Date enterDate = DataUtility.getDate(val);
    	
    	int age = today.getYear() - enterDate.getYear();

    	if(age > 18 && isNotNull(val)){
    		return true;
    	}else{
    		return false;							
    	}
    }
	 public static boolean isMobileNo(String val){
	    	
	    	String mobreg = "^[6-9][0-9]{9}$";
	    	
	    			if (isNotNull(val) && val.matches(mobreg)) {
						
							return true;
	    				}else
	    				{	
	    					return false;
						}	
	    		}
	 
	
	public static void main(String[] args) {
		System.out.println("Not Null 2"+isNotNull("ABC"));
		System.out.println("Not Null 3"+isNotNull(null));
		System.out.println("Not Null 4"+isNull("123"));
		
		
		System.out.println("is int"+isInteger(null));
		System.out.println("Is int"+isInteger("ABC1"));
		System.out.println("Is Int"+isInteger("123"));
		System.out.println("is Int"+isNotNull("123"));
	}

	

}
