import hsa.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Level 
{
    protected int lvl;

    public Level (int a) 
    {
	this.lvl = a;
    }
     
    public void setLevel (int a) 
    {
	this.lvl = a;
    }
    
    public int getLevel () 
    {
	return (lvl);
    }
}
