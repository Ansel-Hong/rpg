import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Monster
{
    protected String monsterName;
    protected int currentHealth;
    protected int maxHealth;
    protected int damageLow;
    protected int damageHigh;
    protected int special;
    protected int special2;
    protected int lootQual;
    protected int type;

    public Monster (String a, int b, int c, int d, int e,int f,int g,int h,int i)
    {
	this.monsterName = a;
	this.currentHealth = b;
	this.maxHealth = c;
	this.damageLow = d;
	this.damageHigh = e;
	this.special = f;
	this.special2 = g;
	this.lootQual = h;
	this.type = i;
    }

    public void setCurrentHealth (int a)
    {
	this.currentHealth = a;
    }
}
