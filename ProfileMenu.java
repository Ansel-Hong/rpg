import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class ProfileMenu
{

    static int maxPartyMembers = 3;
    static int membersInParty;
    static PartyMembers[] party;
    
    static Level lvl;

    public static void profile () throws IOException
    {
	party = partyArray ();
	getPartyMembers (party);
	int totalHP = 0;
	int totalMana = 0;
	int currentHP = 0;
	int currentMana = 0;
	lvl = new Level (getLevel());

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t ---------");
	    Stdout.println ("\t\t  Profile");
	    Stdout.println ("\t\t ---------");
	    Stdout.println ("\t\t     Members in current party");
	    if (membersInParty != 0)
	    {
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\t Equipped:" + party [i].weaponEquipped);
		    totalHP += party [i].maxHealth;
		    totalMana += party [i].maxMana;
		    currentHP += party [i].currentHealth;
		    currentMana += party [i].currentMana;
		}
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.print ("\t\tTotal HP: " + currentHP + "/" + totalHP);
	    Stdout.print ("\t");
	    Stdout.print ("Total Mana: " + currentMana + "/" + totalMana);
	    Stdout.println (" ");
	    Stdout.println ("\t\t Your level: " + lvl.getLevel());
	    Stdout.println ("\t\t ---------");
	    Stdout.println ("\t\t *0. Leave   *");
	    Stdout.println ("\t\t ---------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    private static PartyMembers[] partyArray ()
    {
	PartyMembers[] temp;
	temp = new PartyMembers [maxPartyMembers];
	return temp;
    }


    public static void getPartyMembers (PartyMembers[] a) throws IOException
    {
	String memberName;
	String nowHealth;
	String mHealth;
	String nowMana;
	String mMana;
	String equippedWeapon;
	String maxDamage;
	String minDamage;
	String manaCost;

	int currentHealth;
	int maxHealth;
	int currentMana;
	int maxMana;
	int minDMG;
	int maxDMG;
	int mana;
	int fileSize;
	String str;
	int wepCost;
	String wepC;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("partyMembers.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	membersInParty = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    memberName = infile.readLine ();
	    nowHealth = infile.readLine ();
	    currentHealth = Integer.parseInt (nowHealth);
	    mHealth = infile.readLine ();
	    maxHealth = Integer.parseInt (mHealth);
	    nowMana = infile.readLine ();
	    currentMana = Integer.parseInt (nowMana);
	    mMana = infile.readLine ();
	    maxMana = Integer.parseInt (mMana);
	    equippedWeapon = infile.readLine ();
	    minDamage = infile.readLine ();
	    minDMG = Integer.parseInt (minDamage);
	    maxDamage = infile.readLine ();
	    maxDMG = Integer.parseInt (maxDamage);
	    manaCost = infile.readLine ();
	    mana = Integer.parseInt (manaCost);
	    wepC = infile.readLine ();
	    wepCost = Integer.parseInt (wepC);

	    a [i] = new PartyMembers (memberName, currentHealth, maxHealth, currentMana, maxMana, equippedWeapon, minDMG, maxDMG, mana, wepCost);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }
    
    public static int getLevel () throws IOException
    {
	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("playerLvl.txt"));
	String level = infile.readLine ();

	int lvl = Integer.parseInt (level);
	return lvl;
    }
    
    public static void storeLevel () throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("playerLvl.txt"));

	output.println (lvl.getLevel ());
	output.close ();
    }
}
