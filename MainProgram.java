import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class MainProgram
{

    static final int MAXSIZE = 100;
    static int currentSize = 0;

    static int maxPartyMembers = 3;
    static int membersInParty;
    static PartyMembers[] party;

    public static void main (String[] args) throws IOException
    {
	party = partyArray ();
	getPartyMembers (party);

	FightMenu f1;
	f1 = new FightMenu ();
	PartyMenu p1;
	p1 = new PartyMenu ();
	ShopMenu s1;
	s1 = new ShopMenu ();
	InventoryMenu i1;
	i1 = new InventoryMenu ();
	ProfileMenu pr1;
	pr1 = new ProfileMenu ();

	mainMenu ();
    }


    public static void mainMenu () throws IOException
    {
	int choice;
	while (true)
	{
	    party = partyArray ();
	    getPartyMembers (party);
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t*********");
	    Stdout.println ("\t\tMAIN MENU");
	    Stdout.println ("\t\t*********");
	    Stdout.println ("\t\t1. Fight");
	    Stdout.println ("\t\t2. Party");
	    Stdout.println ("\t\t3. Shop");
	    Stdout.println ("\t\t4. Inventory");
	    Stdout.println ("\t\t5. Profile");
	    Stdout.println ("\t\t0. Log Out");
	    Stdout.println ("\t\t*********");
	    Stdout.println ("\n\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 0)
	    {
		Stdout.println ("\t-------------------");
		Stdout.println ("\tYou have logged out");
		Stdout.println ("\t-------------------");
		Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

		break;
	    }
	    else if (choice == 1)
	    {
		int deadCount = 0;
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    if (party [i].currentHealth == 0)
			deadCount += 1;
		}

		if (membersInParty == 0)
		{
		    Stdout.println ("You need at least one member in your party to fight");
		}
		else if (deadCount == membersInParty)
		{
		    Stdout.println ("You need at least one member with health in your party");
		}
		else
		{
		    FightMenu.fight ();
		}
	    }
	    else if (choice == 2)
	    {
		PartyMenu.party ();
	    }
	    else if (choice == 3)
	    {
		ShopMenu.shop ();
	    }
	    else if (choice == 4)
	    {
		InventoryMenu.inventory ();
	    }
	    else if (choice == 5)
	    {
		ProfileMenu.profile ();
	    }
	}

    } // main method


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

	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }
}
