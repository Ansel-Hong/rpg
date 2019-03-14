import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class ShopMenu
{

    static Balance balance;
    static InventoryMenu itemInventory;
    static ItemsOwned[] playerItems;
    static int itemInventorySize = 3;

    static int currentWeaponSize = 0;
    static WeaponsOwned[] weaponAt;

    static int currentWandSize = 0;
    static WeaponsOwned[] wandAt;

    static int storeWeaponSize = 0;
    static WeaponsOwned[] storeWeaponAt;


    static int storeWandSize = 0;
    static WeaponsOwned[] storeWandAt;


    static WeaponsOwned temp;

    static int maxOwnedMembers = 100;
    static int membersOwned;
    static PartyMembers[] members;

    public static void shop () throws IOException
    {
	balance = new Balance (getBalance ());
	playerItems = itemInventory.createItemArray ();
	itemInventory.getItems (playerItems);

	weaponAt = createWeaponArray ();
	getWeapons (weaponAt);

	wandAt = createWeaponArray ();
	getWands (wandAt);

	storeWeaponAt = createWeaponArray ();
	getStoreWeapons (storeWeaponAt);

	storeWandAt = createWeaponArray ();
	getWandWeapons (storeWandAt);

	members = membersArray ();
	getMembersOwned (members);

	while (true)
	{

	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWelcome to the shop!");
	    Stdout.println ("\t\tWhat would you like to buy?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\t\t 1. Weapons ");
	    Stdout.println ("\t\t 2. Items   ");
	    Stdout.println ("\t\t 3. Recruit members   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		weapons ();
	    }
	    else if (choice == 2)
	    {
		items ();
	    }
	    else if (choice == 3)
	    {
		recruitMembers ();
	    }
	    else if (choice == 0)
	    {
		storeBalance ();
		storeItems ();
		break;
	    }
	}
    }


    public static void weapons () throws IOException
    {

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWhat type of weapon would you like to buy?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\t\t 1. Melee ");
	    Stdout.println ("\t\t 2. Wand   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		meleeWeapons ();
	    }
	    else if (choice == 2)
	    {
		wandWeapons ();
	    }
	    else if (choice == 0)
	    {
		break;
	    }
	}
    }


    //////////////////////////////////////////
    public static void meleeWeapons () throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("playerBal.txt"));

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWhich melee weapon do you want to buy?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    if (storeWeaponSize != 0)
	    {
		for (int i = 0 ; i < storeWeaponSize ; i++)
		{
		    Stdout.print ("\t\t " + (i + 1) + ". " + storeWeaponAt [i].weaponName);
		    Stdout.println ("\t$" + storeWeaponAt [i].weaponPrice);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t There are no more weapons in stock");
	    }
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    for (int i = 0 ; i < storeWeaponSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (balance.getBalance () >= storeWeaponAt [i].weaponPrice)
		    {
			balance.setBalance (balance.getBalance () - storeWeaponAt [i].weaponPrice);
			saveBalance (balance.getBalance ());

			Stdout.println ("\n\n\n\n\n\nYou have bough " + storeWeaponAt [i].weaponName);

			temp = storeWeaponAt [i];
			weaponAt [currentWeaponSize] = temp;

			currentWeaponSize += 1;

			storeWeaponAt [i] = null;
			for (int x = i ; x < storeWeaponSize - 1 ; x++)
			{
			    storeWeaponAt [x] = storeWeaponAt [x + 1];
			}

			storeWeaponSize--;

			storeWeapons (weaponAt);
			storeStoreWeapons (storeWeaponAt);
			break;
		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\nYou dont have enough currency");
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


    public static void wandWeapons () throws IOException
    {
	//Balance balance = getBalance (); // the class for balance of players

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWhich wand do you want to buy?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    if (storeWandSize != 0)
	    {
		for (int i = 0 ; i < storeWandSize ; i++)
		{
		    Stdout.print ("\t\t " + (i + 1) + ". " + storeWandAt [i].weaponName);
		    Stdout.println ("\t$" + storeWandAt [i].weaponPrice);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t There are no more wands in stock");
	    }
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < storeWandSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (balance.getBalance () >= storeWandAt [i].weaponPrice)
		    {
			balance.setBalance (balance.getBalance () - storeWandAt [i].weaponPrice);
			saveBalance (balance.getBalance ());

			Stdout.println ("\n\n\n\n\n\nYou have bough " + storeWandAt [i].weaponName);

			temp = storeWandAt [i];
			wandAt [currentWandSize] = temp;

			currentWandSize += 1;

			storeWandAt [i] = null;
			for (int x = i ; x < storeWandSize - 1 ; x++)
			{
			    storeWandAt [x] = storeWandAt [x + 1];
			}

			storeWandSize--;

			storeWands (wandAt);
			storeWandWeapons (storeWandAt);
			break;
		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\nYou dont have enough currency");
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


    public static void items () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWhat item would you like to buy?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\t\t 1. Health Potion x5  $50");
	    Stdout.println ("\t\t 2. Mana Potion  x5  $50 ");
	    Stdout.println ("\t\t 3. Throwing Star   x5  $100");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		if (balance.getBalance () >= 50)
		{
		    balance.setBalance (balance.getBalance () - 50);
		    saveBalance (balance.getBalance ());
		    playerItems [0].setAmount (playerItems [0].getAmount () + 5);
		    storeItems ();
		    Stdout.println ("\n\n\n\n\n\nYou have bough Health Potion x5");
		}
		else
		{
		    Stdout.println ("\n\n\n\n\n\nYou dont have enough currency");
		    break;
		}
	    }
	    else if (choice == 2)
	    {
		if (balance.getBalance () >= 50)
		{
		    balance.setBalance (balance.getBalance () - 50);
		    saveBalance (balance.getBalance ());
		    playerItems [1].setAmount (playerItems [1].getAmount () + 5);
		    storeItems ();
		    Stdout.println ("\n\n\n\n\n\nYou have bough Mana Potion x5");
		}
		else
		{
		    Stdout.println ("\n\n\n\n\n\nYou dont have enough currency");
		    break;
		}
	    }
	    else if (choice == 3)
	    {
		if (balance.getBalance () >= 100)
		{
		    balance.setBalance (balance.getBalance () - 100);
		    saveBalance (balance.getBalance ());
		    playerItems [2].setAmount (playerItems [2].getAmount () + 5);
		    storeItems ();
		    Stdout.println ("\n\n\n\n\n\nYou have bough Throwing Stars x5");
		}
		else
		{
		    Stdout.println ("\n\n\n\n\n\nYou dont have enough currency");
		    break;
		}
	    }
	    else if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void recruitMembers () throws IOException
    {

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t --S H O P--");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\tWho do you want to recruit?");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\t\t 1. Recruit own member  $500");
	    //Stdout.println ("\t\t 2. Member 1 $x");
	    //Stdout.println ("\t\t 3. Member 2 $x");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -------------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    if (choice == 1)
	    {
		if (balance.getBalance () >= 500)
		{
		    balance.setBalance (balance.getBalance () - 500);
		    saveBalance (balance.getBalance ());

		    Stdout.println ("What is the name of the member?");
		    String inputMemberName = Stdin.readLine ();

		    int maxHealth = 20 + ((int) (Math.random () * (28 + 1))) * 10;
		    int maxMana = ((int) (Math.random () *(40 + 1))) * 5;

		    members [membersOwned] = new PartyMembers (inputMemberName, maxHealth, maxHealth, maxMana, maxMana);

		    membersOwned++;
		    storeMembers (members);

		    Stdout.print ("\n\n\n\n\n\nYou have recruited ");
		    Stdout.println (inputMemberName);
		}
		else
		{
		    Stdout.println ("You dont have enough currency");
		    break;
		}
	    }
	    /*if (choice == 2)
	    {
		Stdout.println ("\n\n\n\n\n\nYou have recruited x");
		break;
	    }
	    else if (choice == 3)
	    {
		Stdout.println ("\n\n\n\n\n\nYou have recruited x");
		break;
	    }
	    */
	    if (choice == 0)
	    {
		break;
	    }
	}
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


    public static void storeItems () throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("itemsOwned.txt"));

	output.println (itemInventorySize);
	for (int i = 0 ; i < itemInventorySize ; i++)
	{
	    output.println (playerItems [i].itemName);
	    output.println (playerItems [i].itemAmount);
	    output.println ();
	}
	output.close ();
    }


    public static WeaponsOwned[] createWeaponArray ()
    {
	WeaponsOwned[] temp;
	temp = new WeaponsOwned [100];
	return temp;
    }


    public static void getWeapons (WeaponsOwned[] a) throws IOException
    {
	String weaponName;
	String high;
	String low;
	int highDmg;
	int lowDmg;
	int fileSize;
	String str;
	String wepPrice;
	int price;
	String manaCost;
	int mana;
	String ep;
	int equip;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("weaponsOwned.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	currentWeaponSize = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    weaponName = infile.readLine ();
	    low = infile.readLine ();
	    lowDmg = Integer.parseInt (low);
	    high = infile.readLine ();
	    highDmg = Integer.parseInt (high);
	    manaCost = infile.readLine ();
	    mana = Integer.parseInt (manaCost);
	    wepPrice = infile.readLine ();
	    price = Integer.parseInt (wepPrice);

	    ep = infile.readLine ();
	    equip = Integer.parseInt (ep);
	    a [i] = new WeaponsOwned (weaponName, lowDmg, highDmg, mana, price, equip);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }


    private static void storeWeapons (WeaponsOwned[] a) throws IOException
    {
	for (int i = 1 ; i < currentWeaponSize ; i++)
	{
	    for (int x = 0 ; x < currentWeaponSize - i ; x++)
	    {
		if (a [x].weaponPrice > a [x + 1].weaponPrice)
		{
		    temp = a [x];
		    a [x] = a [x + 1];
		    a [x + 1] = temp;
		}
	    }
	}

	PrintWriter output;
	output = new PrintWriter (new FileWriter ("weaponsOwned.txt"));

	output.println (currentWeaponSize); // total number of objects in array
	for (int i = 0 ; i < currentWeaponSize ; i++)
	{
	    output.println (a [i].weaponName);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);
	    output.println (a [i].equipped);
	    output.println (); // write blank line
	}
	output.close ();

    }


    ///////////////////// ///////////////////// /////////////////////

    public static void getStoreWeapons (WeaponsOwned[] a) throws IOException
    {
	String weaponName;
	String high;
	String low;
	String wepPrice;
	int highDmg;
	int lowDmg;
	int price;
	int fileSize;
	String str;
	String manaCost;
	int mana;
	String ep;
	int equip;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("storeWeapons.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	storeWeaponSize = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    weaponName = infile.readLine ();
	    low = infile.readLine ();
	    lowDmg = Integer.parseInt (low);
	    high = infile.readLine ();
	    highDmg = Integer.parseInt (high);
	    manaCost = infile.readLine ();
	    mana = Integer.parseInt (manaCost);
	    wepPrice = infile.readLine ();
	    price = Integer.parseInt (wepPrice);
	    ep = infile.readLine ();
	    equip = Integer.parseInt (ep);
	    a [i] = new WeaponsOwned (weaponName, lowDmg, highDmg, mana, price, equip);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }


    private static void storeStoreWeapons (WeaponsOwned[] a) throws IOException
    {
	for (int i = 1 ; i < storeWeaponSize ; i++)
	{
	    for (int x = 0 ; x < storeWeaponSize - i ; x++)
	    {
		if (a [x].weaponPrice > a [x + 1].weaponPrice)
		{
		    temp = a [x];
		    a [x] = a [x + 1];
		    a [x + 1] = temp;
		}
	    }
	}

	PrintWriter output;
	output = new PrintWriter (new FileWriter ("storeWeapons.txt"));

	output.println (storeWeaponSize); // total number of objects in array
	for (int i = 0 ; i < storeWeaponSize ; i++)
	{
	    output.println (a [i].weaponName);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);
	    output.println (a [i].equipped);
	    output.println (); // write blank line
	}
	output.close ();

    }


    ///////////////////////////
    public static void getWandWeapons (WeaponsOwned[] a) throws IOException
    {
	String weaponName;
	String high;
	String low;
	String wepPrice;
	int highDmg;
	int lowDmg;
	int price;
	int fileSize;
	String str;
	String manaCost;
	int mana;
	String ep;
	int equip;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("storeWands.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	storeWandSize = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    weaponName = infile.readLine ();
	    low = infile.readLine ();
	    lowDmg = Integer.parseInt (low);
	    high = infile.readLine ();
	    highDmg = Integer.parseInt (high);
	    manaCost = infile.readLine ();
	    mana = Integer.parseInt (manaCost);
	    wepPrice = infile.readLine ();
	    price = Integer.parseInt (wepPrice);
	    ep = infile.readLine ();
	    equip = Integer.parseInt (ep);
	    a [i] = new WeaponsOwned (weaponName, lowDmg, highDmg, mana, price, equip);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }


    private static void storeWandWeapons (WeaponsOwned[] a) throws IOException
    {
	for (int i = 1 ; i < storeWandSize ; i++)
	{
	    for (int x = 0 ; x < storeWandSize - i ; x++)
	    {
		if (a [x].weaponPrice > a [x + 1].weaponPrice)
		{
		    temp = a [x];
		    a [x] = a [x + 1];
		    a [x + 1] = temp;
		}
	    }
	}

	PrintWriter output;
	output = new PrintWriter (new FileWriter ("storeWands.txt"));

	output.println (storeWandSize); // total number of objects in array
	for (int i = 0 ; i < storeWandSize ; i++)
	{
	    output.println (a [i].weaponName);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);
	    output.println (a [i].equipped);
	    output.println (); // write blank line
	}
	output.close ();

    }


    /////////////////////
    public static void getWands (WeaponsOwned[] a) throws IOException
    {
	String weaponName;
	String high;
	String low;
	String wepPrice;
	int highDmg;
	int lowDmg;
	int price;
	int fileSize;
	String str;
	String manaCost;
	int mana;
	String ep;
	int equip;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("wandsOwned.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	currentWandSize = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    weaponName = infile.readLine ();
	    low = infile.readLine ();
	    lowDmg = Integer.parseInt (low);
	    high = infile.readLine ();
	    highDmg = Integer.parseInt (high);
	    manaCost = infile.readLine ();
	    mana = Integer.parseInt (manaCost);
	    wepPrice = infile.readLine ();
	    price = Integer.parseInt (wepPrice);
	    ep = infile.readLine ();
	    equip = Integer.parseInt (ep);
	    a [i] = new WeaponsOwned (weaponName, lowDmg, highDmg, mana, price, equip);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
    }


    private static void storeWands (WeaponsOwned[] a) throws IOException
    {
	for (int i = 1 ; i < currentWandSize ; i++)
	{
	    for (int x = 0 ; x < currentWandSize - i ; x++)
	    {
		if (a [x].weaponPrice > a [x + 1].weaponPrice)
		{
		    temp = a [x];
		    a [x] = a [x + 1];
		    a [x + 1] = temp;
		}
	    }
	}

	PrintWriter output;
	output = new PrintWriter (new FileWriter ("wandsOwned.txt"));

	output.println (currentWandSize); // total number of objects in array
	for (int i = 0 ; i < currentWandSize ; i++)
	{
	    output.println (a [i].weaponName);  // write
	    output.println (a [i].weaponLow);  // write
	    output.println (a [i].weaponHigh);  // write
	    output.println (a [i].weaponMana);
	    output.println (a [i].weaponPrice);
	    output.println (a [i].equipped);
	    output.println (); // write blank line
	}
	output.close ();

    }


    ////////////////////////////////////////////////////////////
    private static PartyMembers[] membersArray ()
    {
	PartyMembers[] temp;
	temp = new PartyMembers [maxOwnedMembers];
	return temp;
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
}
