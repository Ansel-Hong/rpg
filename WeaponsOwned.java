import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class WeaponsOwned
{
    protected String weaponName;
    protected int weaponHigh;
    protected int weaponLow;
    protected int weaponPrice;
    protected int weaponMana;
    protected int equipped;

    public WeaponsOwned (String a, int b, int c, int d,int e, int f) 
    {
	this.weaponName = a;
	this.weaponLow = b;
	this.weaponHigh = c;
	this.weaponMana = d;
	this.weaponPrice = e;
	this.equipped = f;
    }
     
    public void setHigh (int a) 
    {
	this.weaponHigh = a;
    }

    public void setLow (int a) 
    {
	this.weaponLow = a;
    }
    
    public int getHigh () 
    {
	return (weaponHigh);
    }

    public int getLow () 
    {
	return (weaponLow);
    }
}
