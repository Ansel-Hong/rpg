import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class InventoryMenu
{
    static Balance balance;

    static int currentItemSize = 0;
    static ItemsOwned[] itemAt;

    static int currentWeaponSize = 0;
    static WeaponsOwned[] weaponAt;

    static int currentWandSize = 0;
    static WeaponsOwned[] wandAt;

    static int maxPartyMembers = 3;
    static int membersInParty;
    static PartyMembers[] party;

    static int maxOwnedMembers = 100;
    static int membersOwned;
    static PartyMembers[] members;

    static int storeWeaponSize = 0;
    static WeaponsOwned[] storeWeaponAt;

    static int storeWandSize = 0;
    static WeaponsOwned[] storeWandAt;

    static WeaponsOwned temp;

    public static void inventory () throws IOException
    {
	balance = new Balance (getBalance ());

	party = partyArray ();
	getPartyMembers (party);

	members = membersArray ();
	getMembersOwned (members);

	storeWeaponAt = createWeaponArray ();
	getStoreWeapons (storeWeaponAt);

	storeWandAt = createWeaponArray ();
	getWandWeapons (storeWandAt);
	
	weaponAt = createWeaponArray ();
	getWeapons (weaponAt);
	
	wandAt = createWeaponArray ();
	getWands (wandAt);

	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t 1. Weapons ");
	    Stdout.println ("\t\t 2. Wands ");
	    Stdout.println ("\t\t 3. Items   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		weapons ();
	    }
	    if (choice == 2)
	    {
		wands ();
	    }
	    else if (choice == 3)
	    {
		items ();
	    }
	    else if (choice == 0)
	    {
		storeWandWeapons(storeWandAt);
		storeStoreWeapons(storeWeaponAt);
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
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Here are the weapons you own");
	    Stdout.println ("\t\t -----------");
	    if (currentWeaponSize != 0)
	    {
		for (int i = 0 ; i < currentWeaponSize ; i++)
		{
		    Stdout.print ("\t\t" + weaponAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + weaponAt [i].getLow ());
		    Stdout.println ("-" + weaponAt [i].getHigh ());
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your weapon inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t 1. Sell weapons   ");
	    Stdout.println ("\t\t 2. Equip weapons   ");
	    Stdout.println ("\t\t 3. Unequip all weapons   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		sellWeapons ();
	    }
	    if (choice == 2)
	    {
		equipWeapons ();
	    }
	    if (choice == 3)
	    {
		for (int i = 0 ; i < currentWeaponSize ; i++)
		{
		    weaponAt [i].equipped = 0;
		}
		for (int i = 0 ; i < currentWandSize ; i++)
		{
		    wandAt [i].equipped = 0;
		}
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    party [i].weaponEquipped = "Fists";
		    party [i].weaponLow = 1;
		    party [i].weaponHigh = 1;
		    party [i].setWeaponMana (0);
		    party [i].setWeaponPrice (0);
		}
		for (int i = 0 ; i < membersOwned ; i++)
		{
		    members [i].weaponEquipped = "Fists";
		    members [i].weaponLow = 1;
		    members [i].weaponHigh = 1;
		    members [i].setWeaponMana (0);
		    members [i].setWeaponPrice (0);
		}
		storeWands (wandAt);
		storeWeapons (weaponAt);
		storeParty (party);
		storeMembers (members);
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void sellWeapons () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Which weapon do you want to sell?");
	    Stdout.println ("\t\t Note: You can repurchase it at the store after selling");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -----------");
	    if (currentWeaponSize != 0)
	    {
		for (int i = 0 ; i < currentWeaponSize ; i++)
		{
		    Stdout.print ("\t\t" + weaponAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + weaponAt [i].getLow ());
		    Stdout.print ("-" + weaponAt [i].getHigh ());
		    Stdout.println ("\tWeapon Price: $" + weaponAt [i].weaponPrice);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your weapon inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < currentWeaponSize ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + weaponAt [i].weaponName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    for (int i = 0 ; i < currentWeaponSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (weaponAt [i].equipped == 0)
		    {
			balance.setBalance (balance.getBalance () + weaponAt [i].weaponPrice);
			saveBalance (balance.getBalance ());

			Stdout.println ("\n\n\n\n\n\nYou have sold " + weaponAt [i].weaponName);

			temp = weaponAt [i];
			storeWeaponAt [storeWeaponSize] = temp;

			storeWeaponSize += 1;

			weaponAt [i] = null;
			for (int x = i ; x < currentWeaponSize - 1 ; x++)
			{
			    weaponAt [x] = weaponAt [x + 1];
			}

			currentWeaponSize--;

			storeWeapons (weaponAt);
			storeStoreWeapons (storeWeaponAt);
			break;
		    }
		    else
		    {
			Stdout.println (weaponAt [i].weaponName + " is currently equipped");
		    }
		}
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    //////////////////////////////////////
    public static void equipWeapons () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Which weapon do you want to equip");
	    Stdout.println ("\t\t -----------");
	    if (currentWeaponSize != 0)
	    {
		for (int i = 0 ; i < currentWeaponSize ; i++)
		{
		    Stdout.print ("\t\t" + weaponAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + weaponAt [i].getLow ());
		    Stdout.print ("-" + weaponAt [i].getHigh ());
		    Stdout.print ("\t");
		    if (weaponAt [i].equipped == 1)
			Stdout.print ("Weapon equipped");
		    else
			Stdout.print ("Not equipped");
		    Stdout.println ("");
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your weapon inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < currentWeaponSize ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + weaponAt [i].weaponName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    for (int i = 0 ; i < currentWeaponSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (weaponAt [i].equipped == 0)
		    {
			equip (weaponAt [i]);
		    }
		    else
		    {
			Stdout.println (weaponAt [i].weaponName + " is currently equipped");
		    }
		}
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void equip (WeaponsOwned a) throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to equip " + a.weaponName);
	    Stdout.println ("\t\t -----------");
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
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + party [i].memberName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (a.equipped == 0)
		    {
			if (party [i].weaponPrice == 0)
			{
			    party [i].weaponEquipped = a.weaponName;
			    party [i].weaponLow = a.weaponLow;
			    party [i].weaponHigh = a.weaponHigh;
			    party [i].weaponMana = a.weaponMana;

			    a.equipped = 1;
			    party [i].weaponPrice = 1;

			    storeParty (party);
			    storeWeapons (weaponAt);
			    break;
			}
			else
			{
			    Stdout.println (party [i].memberName + " already has a weapon equipped");
			    break;
			}
		    }
		    else
		    {
			Stdout.println (a.weaponName + " is currently equipped");
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


    public static void wands () throws IOException
    {
       
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Here are the wands you own");
	    Stdout.println ("\t\t -----------");
	    if (currentWandSize != 0)
	    {
		for (int i = 0 ; i < currentWandSize ; i++)
		{
		    Stdout.print ("\t\t" + wandAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + wandAt [i].getLow ());
		    Stdout.print ("-" + wandAt [i].getHigh ());
		    Stdout.println ("\tMana cost: " + wandAt [i].weaponMana);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your wand inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t 1. Sell wands   ");
	    Stdout.println ("\t\t 2. Equip wands   ");
	    Stdout.println ("\t\t 3. Unequip all weapons   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		sellWands ();
	    }
	    if (choice == 2)
	    {
		equipWands ();
	    }
	    if (choice == 3)
	    {
		for (int i = 0 ; i < currentWandSize ; i++)
		{
		    wandAt [i].equipped = 0;
		}
		for (int i = 0 ; i < currentWeaponSize ; i++)
		{
		    weaponAt [i].equipped = 0;
		}
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    party [i].weaponEquipped = "Fists";
		    party [i].weaponLow = 1;
		    party [i].weaponHigh = 1;
		    party [i].setWeaponMana (0);
		    party [i].setWeaponPrice (0);
		}
		for (int i = 0 ; i < membersOwned ; i++)
		{
		    members [i].weaponEquipped = "Fists";
		    members [i].weaponLow = 1;
		    members [i].weaponHigh = 1;
		    members [i].setWeaponMana (0);
		    members [i].setWeaponPrice (0);
		}
		storeWeapons (weaponAt);
		storeWands (wandAt);
		storeParty (party);
		storeMembers (members);
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void sellWands () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Which wand do you want to sell?");
	    Stdout.println ("\t\t Note: You can repurchase it at the store after selling");
	    Stdout.print ("\t\t\t |Your currency: $");
	    Stdout.print (balance.getBalance ());
	    Stdout.println ("|");
	    Stdout.println ("\t\t -----------");
	    if (currentWandSize != 0)
	    {
		for (int i = 0 ; i < currentWandSize ; i++)
		{
		    Stdout.print ("\t\t" + wandAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + wandAt [i].getLow ());
		    Stdout.print ("-" + wandAt [i].getHigh ());
		    Stdout.print ("\tMana cost: " + wandAt [i].weaponMana);
		    Stdout.println ("\tWeapon Price: $" + wandAt [i].weaponPrice);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your wand inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < currentWandSize ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + wandAt [i].weaponName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    for (int i = 0 ; i < currentWandSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (wandAt [i].equipped == 0)
		    {
			balance.setBalance (balance.getBalance () + wandAt [i].weaponPrice);
			saveBalance (balance.getBalance ());

			Stdout.println ("\n\n\n\n\n\nYou have sold " + wandAt [i].weaponName);

			temp = wandAt [i];
			storeWandAt [storeWandSize] = temp;

			storeWandSize += 1;

			wandAt [i] = null;
			for (int x = i ; x < currentWandSize - 1 ; x++)
			{
			    wandAt [x] = wandAt [x + 1];
			}

			currentWandSize--;

			storeWands (wandAt);
			storeWandWeapons (storeWandAt);
			break;
		    }
		    else
		    {
			Stdout.println (wandAt [i].weaponName + " is currently equipped");
		    }
		}
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    //////////////////////////////////////////////
    public static void equipWands () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Which wand do you want to equip");
	    Stdout.println ("\t\t -----------");
	    if (currentWandSize != 0)
	    {
		for (int i = 0 ; i < currentWandSize ; i++)
		{
		    Stdout.print ("\t\t" + wandAt [i].weaponName);
		    Stdout.print ("\tDamage range: " + wandAt [i].getLow ());
		    Stdout.print ("-" + wandAt [i].getHigh ());
		    Stdout.print ("\tMana cost: " + wandAt [i].weaponMana);
		    Stdout.print ("\t");
		    if (wandAt [i].equipped == 1)
			Stdout.print ("Weapon equipped");
		    else
			Stdout.print ("Not equipped");
		    Stdout.println ("");
		}
	    }
	    else
	    {
		Stdout.println ("\t\t Your wand inventory is empty.");
	    }
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < currentWandSize ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + wandAt [i].weaponName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    for (int i = 0 ; i < currentWandSize ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (wandAt [i].equipped == 0)
		    {
			equipW (wandAt [i]);
		    }
		    else
		    {
			Stdout.println (wandAt [i].weaponName + " is currently equipped");
		    }
		}
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void equipW (WeaponsOwned a) throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to equip " + a.weaponName);
	    Stdout.println ("\t\t -----------");
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
	    Stdout.println ("\t\t -----------");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + party [i].memberName);
	    }

	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (a.equipped == 0)
		    {
			if (party [i].weaponPrice == 0)
			{
			    party [i].weaponEquipped = a.weaponName;
			    party [i].weaponLow = a.weaponLow;
			    party [i].weaponHigh = a.weaponHigh;
			    party [i].weaponMana = a.weaponMana;
			    a.equipped = 1;
			    party [i].weaponPrice = 1;

			    storeParty (party);
			    storeWands (wandAt);
			    break;
			}
			else
			{
			    Stdout.println (party [i].memberName + " already has a weapon equipped");
			    break;
			}
		    }
		    else
		    {
			Stdout.println (a.weaponName + " is currently equipped");
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


    //////////////////////////////////////////////////////////////


    public static void items () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Here are the items you own");
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < currentItemSize ; i++)
	    {
		Stdout.println ("\t\t" + itemAt [i].itemName);
		Stdout.println ("\t\tx" + itemAt [i].itemAmount);
	    }
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t 1. Use item   ");
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		useItem ();
	    }
	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void useItem () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Which item do you want to use?");
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < currentItemSize ; i++)
	    {
		Stdout.println ("\t\t" + itemAt [i].itemName);
		Stdout.println ("\t\tx" + itemAt [i].itemAmount);
	    }
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < currentItemSize ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + itemAt [i].itemName);
	    }
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");
	    if (choice == 1)
	    {
		if (itemAt [0].itemAmount > 0)
		{
		    healParty ();
		}
		else
		{
		    Stdout.println ("\n\n\n\n\n\nYou dont have enough Health Potions.");
		}
	    }
	    if (choice == 2)
	    {
		if (itemAt [1].itemAmount > 0)
		{
		    restoreMana ();
		}
		else
		{
		    Stdout.println ("\n\n\n\n\n\nYou dont have enough Mana Potions.");
		}
	    }
	    if (choice == 3)
	    {
		Stdout.println ("\n\n\n\n\n\nYou cant use throwing stars here.");
	    }

	    if (choice == 0)
	    {
		break;
	    }
	}
    }


    public static void healParty () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to heal?");
	    Stdout.println ("\t\t -----------");
	    if (membersInParty != 0)
	    {
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.println ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + party [i].memberName);
	    }
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");
	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (party [i].currentHealth < party [i].maxHealth)
		    {
			Stdout.println ("\n\n\n\n\n\nYou have healed " + party [i].memberName);

			if (itemAt [0].itemAmount > 0)
			{
			    itemAt [0].setAmount (itemAt [0].itemAmount - 1);
			    party [i].setCurrentHealth (party [i].currentHealth + 50);
			    if (party [i].currentHealth > party [i].maxHealth)
			    {
				party [i].currentHealth = party [i].maxHealth;
			    }
			    storeItems ();
			    storeParty (party);
			    break;
			}
			else
			{
			    Stdout.println ("\n\n\n\n\n\nYou dont have any more Health Potions.");
			    break;
			}

		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\n" + party [i].memberName + " is at full health");
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


    public static void restoreMana () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t  Inventory");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to restore mana to?");
	    Stdout.println ("\t\t -----------");
	    if (membersInParty != 0)
	    {
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.println ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		}
	    }
	    else
	    {
		Stdout.println ("\t\t You have no members in your party.");
	    }
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.println ("\t\t " + (i + 1) + ". " + party [i].memberName);
	    }
	    Stdout.println ("\t\t 0. Leave   ");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\n\n\n\n");
	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.println ("\n\n\n\n\n\n\n\n\n\n");

	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		if (choice == (i + 1))
		{
		    if (party [i].currentMana < party [i].maxMana)
		    {
			Stdout.println ("\n\n\n\n\n\nYou have restored mana to " + party [i].memberName);

			if (itemAt [1].itemAmount > 0)
			{
			    itemAt [1].setAmount (itemAt [1].itemAmount - 1);

			    party [i].setCurrentMana (party [i].currentMana + 20);
			    if (party [i].currentMana > party [i].maxMana)
			    {
				party [i].currentMana = party [i].maxMana;
			    }
			    storeItems ();
			    storeParty (party);

			    break;
			}
			else
			{
			    Stdout.println ("\n\n\n\n\n\nYou dont have any more Mana Potions.");
			    break;
			}

		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\n" + party [i].memberName + " is at full mana");
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


    //Read and write files
    //////////////////////////////////////////////////////////
    public static void getItems (ItemsOwned[] a) throws IOException
    {
	String itemName;
	String amnt;
	int itemAmount;
	int fileSize;
	String str;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("itemsOwned.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	currentItemSize = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    itemName = infile.readLine ();
	    amnt = infile.readLine ();
	    itemAmount = Integer.parseInt (amnt);
	    a [i] = new ItemsOwned (itemName, itemAmount);
	    //currentItemSize++;
	    str = infile.readLine (); // read the blank line
	}
	infile.close ();
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
	String manap;
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
	    manap = infile.readLine ();
	    mana = Integer.parseInt (manap);
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


    public static void getWands (WeaponsOwned[] a) throws IOException
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
	String manap;
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
	    manap = infile.readLine ();
	    mana = Integer.parseInt (manap);
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


    ////////////////////


    public static void storeItems () throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("itemsOwned.txt"));

	output.println (currentItemSize);
	for (int i = 0 ; i < currentItemSize ; i++)
	{
	    output.println (itemAt [i].itemName);
	    output.println (itemAt [i].itemAmount);
	    output.println ();
	}
	output.close ();
    }


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


    public static ItemsOwned[] createItemArray ()
    {
	ItemsOwned[] temp;
	temp = new ItemsOwned [3];
	return temp;
    }


    public static WeaponsOwned[] createWeaponArray ()
    {
	WeaponsOwned[] temp;
	temp = new WeaponsOwned [100];
	return temp;
    }


    private static PartyMembers[] membersArray ()
    {
	PartyMembers[] temp;
	temp = new PartyMembers [maxOwnedMembers];
	return temp;
    }


    private static PartyMembers[] partyArray ()
    {
	PartyMembers[] temp;
	temp = new PartyMembers [maxPartyMembers];
	return temp;
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
