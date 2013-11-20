package testcode;

import static java.lang.System.*;
public class Address
{
    private int unit;
    private String street;
    private String city;
    private String postalCode;
    private String print;

    
    public Address (String street, String city, String postalCode) throws Exception
    {
    	this.unit = 0;
    	this.street = street;
    	this.city = city;
    	this.postalCode = postalCode;
    };
    public Address (int unit, String street, String city, String postalCode) throws Exception
    { 
    	this.unit = unit;
    	this.street = street;
    	this.city = city;
    	this.postalCode = postalCode;
    };

    public String toString() { 
    	if (this.unit == 0){
    		print = "Address: " + this.street + ", " + this.city + ". " + this.postalCode;
    		System.out.println(print);
    		return print;
    		}
    	else{
    		print = "Address: " + this.unit +" "+ this.street + ", " + this.city + ". " + this.postalCode;
    		System.out.println(print);
    		return print;
    	}
    }
     
    public boolean equals(Object o) { 
    	if(!(o instanceof Address)){
    		return false;
    	}
    	Address other = (Address)o;
    	if(this.unit == other.unit && this.street == other.street && this.city == other.city && this.postalCode == other.postalCode)
    		return true;
    	return false; 
    }
     
    private boolean isValidPostalCode( String postalCode ) { 
    	return false; 
    }
 
} 

