import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class ItemsOwned
{
    protected String itemName;
    protected int itemAmount;

    public ItemsOwned (String a, int b) 
    {
	this.itemName = a;
	this.itemAmount = b;
    }
     
    public void setAmount (int a) 
    {
	this.itemAmount = a;
    }
    
    public int getAmount () 
    {
	return (itemAmount);
    }
}
