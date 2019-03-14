import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class PartyMembers
{
    protected String memberName;
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;
    protected String weaponEquipped;
    protected int weaponHigh;
    protected int weaponLow;
    protected int weaponMana;
    protected int weaponPrice;

    public PartyMembers (String a, int b, int c, int d, int e, String f, int g, int h, int i, int j)
    {
	this.memberName = a;
	this.currentHealth = b;
	this.maxHealth = c;
	this.currentMana = d;
	this.maxMana = e;
	this.weaponEquipped = f;
	this.weaponLow = g;
	this.weaponHigh = h;
	this.weaponMana = i;
	this.weaponPrice = j;
    }
    
    public PartyMembers (String a, int b, int c, int d, int e)
    {
	this.memberName = a;
	this.currentHealth = b;
	this.maxHealth = c;
	this.currentMana = d;
	this.maxMana = e;
	this.weaponEquipped = "Fists";
	this.weaponLow = 1;
	this.weaponHigh = 1;
	this.weaponMana = 0;
	this.weaponPrice = 0;
    }


    public void setHealth (int a)
    {
	this.maxHealth = a;
    }


    public void setMana (int a)
    {
	this.maxMana = a;
    }

    public void setCurrentHealth (int a)
    {
	this.currentHealth = a;
    }

    public void setCurrentMana (int a)
    {
	this.currentMana = a;
    }


    public void setHighDmg (int a)
    {
	this.weaponHigh = a;
    }


    public void setLowDmg (int a)
    {
	this.weaponLow = a;
    }
    
    public void setWeaponMana (int a)
    {
	this.weaponMana = a;
    }
    
    public void setWeaponPrice (int a)
    {
	this.weaponPrice = a;
    }
}
