import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class PartyMenu
{
    static Balance balance;

    static int maxPartyMembers = 3;
    static int membersInParty;
    static PartyMembers[] party;

    static int maxOwnedMembers = 100;
    static int membersOwned;
    static PartyMembers[] members;

    static PartyMembers temp;

    public static void party () throws IOException
    {
	balance = new Balance (getBalance ());
    
	party = partyArray ();
	getPartyMembers (party);

	members = membersArray ();
	getMembersOwned (members);

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tParty");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\t1. Members in party ");
	    Stdout.println ("\t\t2. Members owned ");
	    Stdout.println ("\t\t3. Add members to party");
	    Stdout.println ("\t\t4. Remove members from party");
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		membersInParty ();
	    }
	    else if (choice == 2)
	    {
		membersInInventory ();
	    }
	    else if (choice == 3)
	    {
		addMembers ();
	    }
	    else if (choice == 4)
	    {
		deleteMembers ();
	    }
	    else if (choice == 0)
	    {
		storeParty (party);
		break;
	    }
	}
    }


    public static void membersInParty () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tMembers in party");
	    if (membersInParty != 0)
	    {
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\t Equipped:" + party [i].weaponEquipped);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void membersInInventory () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tMembers in inventory");

	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		Stdout.print ("\t\t" + members [i].memberName);
		Stdout.print ("\tHP:" + members [i].currentHealth + "/" + members [i].maxHealth);
		Stdout.print ("\t Mana:" + members [i].currentMana + "/" + members [i].maxMana);
		Stdout.println ("\t Equipped:" + members [i].weaponEquipped);
	    }
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\t1. Delete Members   ");
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		removeMember ();
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void removeMember () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tWho would you like to sell from your inventory?");
	    Stdout.println ("\t\t   Note: You gain 200 currency for selling a member");
	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		Stdout.print ("\t\t" + members [i].memberName);
		Stdout.print ("\tHP:" + members [i].currentHealth + "/" + members [i].maxHealth);
		Stdout.print ("\t Mana:" + members [i].currentMana + "/" + members [i].maxMana);
		Stdout.println ("\t Equipped:" + members [i].weaponEquipped);
	    }
	    //////////////
	    Stdout.println ("\t\t------");
	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		Stdout.println ("\t\t" + (i + 1) + ". " + members [i].memberName);
	    }
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n\n");


	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		String confirm;
		char conf;
		if (choice == (i + 1))
		{
		    if (membersInParty == 0 && membersOwned == 1)
		    {
			Stdout.println ("You cannot delete the last member of your inventory");
		    }
		    else
		    {
			if (members [i].weaponPrice == 0)
			{
			    Stdout.println ("Are you sure you want to remove " + members [i].memberName + " from your party?(Y = yes, N = no)");
			    
			    confirm = Stdin.readString ();
			    confirm = confirm.toUpperCase ();
			    conf = confirm.charAt (0);
			    if (conf == 'Y')
			    {
				balance.setBalance (balance.getBalance () + 200);
				saveBalance (balance.getBalance ());
					
				members [i] = null;
				for (int x = i ; x < membersOwned - 1 ; x++)
				{
				    members [x] = members [x + 1];
				}

				membersOwned--;

				storeMembers (members);
				break;
			    }
			    else
			    {
				break;
			    }
			}
			else
			{
			    Stdout.println (members [i].memberName + " has a weapon equipped, please unequip weapons before selling members");
			    break;
			}
		    }
		}
	    }

	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void addMembers () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tCurrent Party");
	    if (membersInParty != 0)
	    {
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\t Equipped:" + party [i].weaponEquipped);
		} // displays " -x- HP -/- Mana -/- Equipped: -" when no player
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tWho do you want to join the main party?");
	    Stdout.println ("\t\t------");
	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		Stdout.print ("\t\t" + (i + 1) + ". " + members [i].memberName);
		Stdout.print ("\tHP:" + members [i].currentHealth + "/" + members [i].maxHealth);
		Stdout.print ("\t Mana:" + members [i].currentMana + "/" + members [i].maxMana);
		Stdout.println ("\t Equipped:" + members [i].weaponEquipped);
	    }
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersOwned ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (membersInParty < maxPartyMembers)
		    {
			temp = members [i];
			party [membersInParty] = temp;
			membersInParty += 1;
			members [i] = null;
			for (int x = i ; x < membersOwned - 1 ; x++)
			{
			    members [x] = members [x + 1];
			}

			membersOwned--;

			storeMembers (members);
			storeParty (party);
			break;
		    }
		    else
		    {
			Stdout.println ("Your party is full");
			break;
		    }
		}
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void deleteMembers () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tCurrent Party");
	    if (membersInParty != 0)
	    {
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.print ("\t\t" + party [i].memberName);
		Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		Stdout.print ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		Stdout.println ("\t Equipped:" + party [i].weaponEquipped);
	    } // displays " -x- HP -/- Mana -/- Equipped: -" when no player
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.println ("\t\t------");
	    Stdout.println ("\t\tWho do you want to remove from the main party?");
	    Stdout.println ("\t\t------");
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.println ("\t\t" + (i + 1) + ". " + party [i].memberName);
	    }
	    Stdout.println ("\t\t0. Leave   ");
	    Stdout.println ("\t\t------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		if (choice == (i + 1))
		{
		    temp = party [i];
		    members [membersOwned] = temp;
		    membersOwned += 1;
		    party [i] = null;
		    for (int x = i ; x < membersInParty - 1 ; x++)
		    {
			party [x] = party [x + 1];
		    }

		    membersInParty--;

		    storeMembers (members);
		    storeParty (party);
		    break;
		}
	    }
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


    private static PartyMembers[] membersArray ()
    {
	PartyMembers[] temp;
	temp = new PartyMembers [maxOwnedMembers];
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


    public static void getMembersOwned (PartyMembers[] a) throws IOException
    {
	String memberName;
	String nowHealth;
	String mHealth;
	String nowMana;
	String mMana;
	String equippedWeapon;
	String maxDamage;
	String minDamage;

	int currentHealth;
	int maxHealth;
	int currentMana;
	int maxMana;
	int minDMG;
	int maxDMG;
	int fileSize;
	String str;
	String manaCost;
	int mana;
	int wepCost;
	String wepC;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("membersOwned.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	membersOwned = fileSize;

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


    private static void storeParty (PartyMembers[] a) throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("partyMembers.txt"));

	output.println (membersInParty); // totoal number of objects in array
	for (int i = 0 ; i < membersInParty ; i++)
	{
	    output.println (a [i].memberName);  // write
	    output.println (a [i].currentHealth);  // write
	    output.println (a [i].maxHealth);  // write
	    output.println (a [i].currentMana);  // write
	    output.println (a [i].maxMana);  // write
	    output.println (a [i].weaponEquipped);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);

	    output.println (); // write blank line
	}
	output.close ();

    }


    private static void storeMembers (PartyMembers[] a) throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("membersOwned.txt"));

	output.println (membersOwned); // totoal number of objects in array
	for (int i = 0 ; i < membersOwned ; i++)
	{
	    output.println (a [i].memberName);  // write
	    output.println (a [i].currentHealth);  // write
	    output.println (a [i].maxHealth);  // write
	    output.println (a [i].currentMana);  // write
	    output.println (a [i].maxMana);  // write
	    output.println (a [i].weaponEquipped);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);

	    output.println (); // write blank line
	}
	output.close ();

    }

public static double getBalance () throws IOException
    {
	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("playerBal.txt"));
	String bal = infile.readLine ();

	double b1 = (Double.parseDouble (bal));
	return b1;
    }


    public static void saveBalance (double a) throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("playerBal.txt"));

	output.println (a);
	output.close ();
    }


    public static void storeBalance () throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("playerBal.txt"));

	output.println (balance.getBalance ());
	output.close ();
    }
}
