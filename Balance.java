import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Balance  
{
    protected double balance;

    public Balance (double a) 
    {
	this.balance = a;
    }
     
    public void setBalance (double a) 
    {
	this.balance = a;
    }
    
    public double getBalance () 
    {
	return (balance);
    }
}
