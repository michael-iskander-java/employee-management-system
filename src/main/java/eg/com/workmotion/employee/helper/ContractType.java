package eg.com.workmotion.employee.helper;

public enum ContractType {
	PERMANENT("PERMANENT"),TEMPORARY("TEMPORARY"); 
	
	private String value;

	ContractType(String value) {
	    this.value = value;
	  }
}
