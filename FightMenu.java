import java.io.*;
import java.util.*;
import java.awt.*;
import hsa.*;

public class FightMenu
{
    static Balance balance;

    static Level lvl;

    static int currentItemSize = 0;
    static ItemsOwned[] itemAt;

    static int maxPartyMembers = 3;
    static int membersInParty;
    static PartyMembers[] party;

    static int maxMonsters = 100;
    static int monsterTypes;
    static Monster[] monster;
    static int monsterAt;
    static int generatedCheck = 0;
    //static PartyMembers[] party;

    static int monsterDead = 0;


    public static void fight () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);

	party = partyArray ();
	getPartyMembers (party);

	monster = monstersArray ();
	getMonster (monster);

	balance = new Balance (getBalance ());
	lvl = new Level (getLevel ());

	if (generatedCheck == 0)
	{
	    monsterAt = (int) (Math.random () * monsterTypes);
	    generatedCheck = 1;
	}

	if (monsterDead == 1)
	{
	    monsterDead = 0;
	}

	while (true)
	{
	    if (monsterDead != 1)
	    {
		Stdout.println ("\n\n\n\n");
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t" + monster [monsterAt].monsterName + " (lvl. " + monster [monsterAt].lootQual + ") stands in your way.");
		Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t Party:");
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\t Damage Range:" + party [i].weaponLow + "-" + party [i].weaponHigh);
		}
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t1. Attack");
		Stdout.println ("\t\t2. Items");
		Stdout.println ("\t\t0. Run Away");
		Stdout.println ("\t\t---------");
		Stdout.println ("\n\n\n\n");

		Stdout.print ("Enter your selection -->");
		int choice = Stdin.readInt ();
		Stdout.print ("\n\n\n\n\n\n\n\n");
		if (choice == 1)
		{
		    attack ();
		}
		else if (choice == 2)
		{
		    items ();
		}
		else if (choice == 0)
		{
		    Stdout.println ("\n\n\n\n\n\nYou ran away.");
		    generatedCheck = 0;
		    storeParty (party);
		    break;
		}
	    }
	    else if (monsterDead == 1)
		break;
	}

    }


    public static void attack () throws IOException
    {
	while (true)
	{
	    if (monsterDead != 1)
	    {
		Stdout.println ("\n\n\n");
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\tWho do you want to perform the attack?");
		Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t Party:");
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\tMana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\tDamage Range:" + party [i].weaponLow + "-" + party [i].weaponHigh);
		}
		Stdout.println ("\t\t---------");
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.println ("\t\t" + (i + 1) + ". " + party [i].memberName);
		}

		Stdout.println ("\t\t0. Go Back");
		Stdout.println ("\t\t---------");
		Stdout.println ("\n\n\n\n");

		Stdout.print ("Enter your selection -->");
		int choice = Stdin.readInt ();
		Stdout.print ("\n\n\n\n\n\n\n\n\n");
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    if (choice == (i + 1))
		    {
			if (party [i].currentHealth > 0)
			{
			    if (party [i].currentMana >= party [i].weaponMana)
			    {
				party [i].setCurrentMana (party [i].currentMana - party [i].weaponMana);
				attack1 (party [i]);
				break;
			    }
			    else
			    {
				Stdout.println (party [i].memberName + " does not have enough mana");
				break;
			    }
			}
			else
			{
			    Stdout.println (party [i].memberName + " has fainted and cant attack");
			    break;
			}
		    }
		}
		if (choice == 0)
		{
		    break;
		}
	    }

	    else if (monsterDead == 1)
		break;
	}
    }


    public static void attack1 (PartyMembers a) throws IOException
    {
	int randomPlayerDamage;
	int dmgDif = a.weaponHigh - a.weaponLow;
	randomPlayerDamage = ((int) ((Math.random () * (dmgDif + 1))) + a.weaponLow);

	int randomEnemyDamage;
	int dmgDifEnemy = monster [monsterAt].damageHigh - monster [monsterAt].damageLow;
	randomEnemyDamage = ((int) ((Math.random () * (dmgDifEnemy + 1))) + monster [monsterAt].damageLow);

	int attackOn = (int) (Math.random () * membersInParty);


	while (true)
	{
	    if (monsterDead != 1)
	    {
		Stdout.println ("\n\n\n");
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t" + a.memberName + " attacks with " + a.weaponEquipped + " and deals " + randomPlayerDamage + " damage");

		//chance to use ability
		int abilityChance;
		abilityChance = (int) (Math.random () * 5); //make int 0-4

		int randomSpec;
		randomSpec = ((int) ((Math.random () * (monster [monsterAt].special2 - monster [monsterAt].special + 1)))) + monster [monsterAt].special;

		if (abilityChance == 0)
		{
		    //shield types
		    if (monster [monsterAt].type == 1)
		    {
			randomPlayerDamage -= randomSpec;
			if (randomPlayerDamage < 0)
			{
			    randomPlayerDamage = 0;
			}
			Stdout.println ("\t\t" + monster [monsterAt].monsterName + " blocked " + randomSpec + " damage");
			Stdout.println ("\t\t" + a.memberName + " dealt " + randomPlayerDamage + " in total");
		    }
		    //heal types
		    else if (monster [monsterAt].type == 2)
		    {
			if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomSpec))
			{
			    monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomSpec);
			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " regenerated " + randomSpec + " health after the attack");
			}
		    }
		    // drain health
		    else if (monster [monsterAt].type == 3)
		    {
			if (monster [monsterAt].currentHealth != 0)
			{
			    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomEnemyDamage))
			    {
				monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomEnemyDamage);
				if (monster [monsterAt].currentHealth > monster [monsterAt].maxHealth)
				    monster [monsterAt].currentHealth = monster [monsterAt].maxHealth;
				Stdout.println ("\t\t" + monster [monsterAt].monsterName + " drained health equal to damage dealt");
			    }
			}
		    }
		    // dodge attack
		    else if (monster [monsterAt].type == 4)
		    {
			randomPlayerDamage = 0;
			Stdout.println ("\t\t" + monster [monsterAt].monsterName + " dodged the attack");

		    }
		    // area of effect
		    else if (monster [monsterAt].type == 5)
		    {
			if (monster [monsterAt].currentHealth != 0)
			{
			    for (int i = 0 ; i < membersInParty ; i++)
			    {
				party [i].setCurrentHealth (party [i].currentHealth - randomSpec);
				if (party [i].currentHealth < 0)
				{
				    party [i].setCurrentHealth (0);
				}
			    }
			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " released a cloud of toxic gas");
			    Stdout.println ("\t\t" + randomSpec + " damage dealt to all players in party");

			}
		    }
		}

		monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth - randomPlayerDamage);

		if (monster [monsterAt].currentHealth <= 0)
		{
		    monsterKilled ();
		}
		if (monsterDead != 1)
		{
		    while (true)
		    {
			if (party [attackOn].currentHealth > 0)
			{
			    party [attackOn].setCurrentHealth (party [attackOn].currentHealth - randomEnemyDamage);
			    break;
			}
			else
			    attackOn = (int) (Math.random () * membersInParty);
		    }

		    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " attacked and dealt " + randomEnemyDamage + " damage to " + party [attackOn].memberName);

		    if (party [attackOn].currentHealth < 0)
		    {
			party [attackOn].currentHealth = 0;
			Stdout.println ("\t\t" + party [attackOn].memberName + " has fainted");
		    }

		    storeParty (party);

		    int deadCount = 0;

		    for (int i = 0 ; i < membersInParty ; i++)
		    {
			if (party [i].currentHealth == 0)
			    deadCount += 1;
		    }

		    if (deadCount == membersInParty)
		    {
			partyDead ();
			break;
		    }

		    Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
		    Stdout.println ("\t\t---------");
		    Stdout.println ("\t\t Party:");
		    for (int i = 0 ; i < membersInParty ; i++)
		    {
			Stdout.print ("\t\t" + party [i].memberName);
			Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
			Stdout.print ("\tMana:" + party [i].currentMana + "/" + party [i].maxMana);
			Stdout.println ("\tDamage Range:" + party [i].weaponLow + "-" + party [i].weaponHigh);
		    }
		    Stdout.println ("\t\t---------");
		    Stdout.println ("\t\t0. Next turn");
		    Stdout.println ("\t\t---------");
		    Stdout.println ("\n\n\n\n");

		    Stdout.print ("Enter your selection -->");
		    int choice = Stdin.readInt ();
		    Stdout.print ("\n\n\n\n\n\n\n\n");
		    if (choice == 0)
		    {
			break;
		    }
		    else
			break;

		}
		else if (monsterDead == 1)
		    break;
	    }
	    else if (monsterDead == 1)
		break;
	}

    }


    public static void monsterKilled () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " has been slayen!");

	    int lootQ = monster [monsterAt].lootQual;

	    generatedCheck = 0;

	    int goldRange;
	    int lootRange, lootMin;
	    int currencyGained;

	    if (lootQ == 1)
	    {
		lootMin = 50;
		lootRange = 4;
	    }
	    else if (lootQ == 2)
	    {
		lootMin = 50;
		lootRange = 15;
	    }
	    else if (lootQ == 3)
	    {
		lootMin = 100;
		lootRange = 20;
	    }
	    else if (lootQ == 4)
	    {
		lootMin = 200;
		lootRange = 30;
	    }
	    else
	    {
		lootMin = 10;
		lootRange = 9;
	    }

	    currencyGained = ((int) (Math.random () * (lootRange + 1))) * 10 + lootMin;

	    balance.setBalance (balance.getBalance () + currencyGained);
	    saveBalance (balance.getBalance ());

	    lvl.setLevel (lvl.getLevel () + monster [monsterAt].lootQual);
	    storeLevel ();

	    Stdout.println ("\t\tYou have gained " + currencyGained + " currency");
	    if (monster [monsterAt].lootQual > 1)
		Stdout.println ("\t\tYou have gained " + monster [monsterAt].lootQual + " levels");
	    else
		Stdout.println ("\t\tYou have gained " + monster [monsterAt].lootQual + " level");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\t\t0. Go Back To Main Menu");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\n\n\n\n");

	    monsterDead = 1;

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n");

	    if (choice == 0)
	    {
		storeParty (party);
		break;
	    }
	    else
		break;
	}
    }


    public static void partyDead () throws IOException
    {
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\t\t All your party members have fainted");
	    Stdout.println ("\t\t You gain 10 currency as a complementary reward");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\t\t0. Go Back To Main Menu");
	    Stdout.println ("\t\t---------");
	    Stdout.println ("\n\n\n\n");

	    balance.setBalance (balance.getBalance () + 10);
	    saveBalance (balance.getBalance ());

	    generatedCheck = 0;
	    monsterDead = 1;

	    Stdout.print ("Enter your selection -->");
	    int choice = Stdin.readInt ();
	    Stdout.print ("\n\n\n\n\n\n\n\n");

	    if (choice == 0)
	    {
		storeParty (party);
		break;
	    }
	    else
		break;
	}
    }


    public static void items () throws IOException
    {
	int randomEnemyDamage;
	int dmgDifEnemy = monster [monsterAt].damageHigh - monster [monsterAt].damageLow;
	randomEnemyDamage = ((int) ((Math.random () * (dmgDifEnemy + 1))) + monster [monsterAt].damageLow);

	int attackOn = (int) (Math.random () * membersInParty);

	while (true)
	{
	    if (monsterDead != 1)
	    {
		Stdout.println ("\n\n\n");
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\tWhat items do you want to use");
		Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
		Stdout.println ("\t\t---------");
		Stdout.println ("\t\t Party:");
		for (int i = 0 ; i < membersInParty ; i++)
		{
		    Stdout.print ("\t\t" + party [i].memberName);
		    Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		    Stdout.print ("\tMana:" + party [i].currentMana + "/" + party [i].maxMana);
		    Stdout.println ("\tDamage Range:" + party [i].weaponLow + "-" + party [i].weaponHigh);
		}
		Stdout.println ("\t\t---------");
		//for(int i=0;i<totalItems;i++)  // create for loop equal to total items
		for (int i = 0 ; i < currentItemSize ; i++)
		{
		    Stdout.print ("\t\t" + (i + 1) + ". " + itemAt [i].itemName);
		    Stdout.println ("  x" + itemAt [i].itemAmount);
		}
		Stdout.println ("\t\t0. Go Back");
		Stdout.println ("\t\t---------");
		Stdout.println ("\n\n\n\n");

		Stdout.print ("Enter your selection -->");
		int choice = Stdin.readInt ();
		Stdout.print ("\n\n\n\n\n\n\n\n");
		if (choice == 1)
		{
		    if (itemAt [0].itemAmount > 0)
		    {
			healParty ();
			break;
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
			break;
		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\nYou dont have any more Mana Potions.");
			break;
		    }
		}
		if (choice == 3)
		{
		    if (itemAt [2].itemAmount > 0)
		    {
			int randomPlayerDamage;
			randomPlayerDamage = ((int) ((Math.random () * 50)) + 1);
			itemAt [2].setAmount (itemAt [2].itemAmount - 1);
			Stdout.println ("\t\t-------------");
			Stdout.println ("\t\tYou threw a throwing star at " + monster [monsterAt].monsterName + " and dealt " + randomPlayerDamage + " damage");
			storeItems ();

			//chance to use ability
			int abilityChance;
			abilityChance = (int) (Math.random () * 5); //make int 0-4

			int randomSpec;
			randomSpec = (int) ((Math.random () * (monster [monsterAt].special2 - monster [monsterAt].special + 1))) + monster [monsterAt].special;

			if (abilityChance == 0)
			{
			    //shield types
			    if (monster [monsterAt].type == 1)
			    {
				randomPlayerDamage -= randomSpec;
				if (randomPlayerDamage < 0)
				{
				    randomPlayerDamage = 0;
				}
				Stdout.println ("\t\t" + monster [monsterAt].monsterName + " blocked " + randomSpec + " of the damage");
			    }
			    // heal
			    else if (monster [monsterAt].type == 2)
			    {
				if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomSpec))
				{
				    monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomSpec);
				    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " regenerated " + randomSpec + " health after the attack");
				}
			    }
			    // drain health
			    else if (monster [monsterAt].type == 3)
			    {
				if (monster [monsterAt].currentHealth != 0)
				{
				    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomEnemyDamage))
				    {
					monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomEnemyDamage);
					if (monster [monsterAt].currentHealth > monster [monsterAt].maxHealth)
					    monster [monsterAt].currentHealth = monster [monsterAt].maxHealth;
					Stdout.println ("\t\t" + monster [monsterAt].monsterName + " drained health equal to damage dealt");
				    }
				}
			    }
			    // dodge attack
			    else if (monster [monsterAt].type == 4)
			    {
				randomPlayerDamage = 0;
				Stdout.println ("\t\t" + monster [monsterAt].monsterName + " dodged the attack");

			    }
			    // area of effect
			    else if (monster [monsterAt].type == 5)
			    {
				if (monster [monsterAt].currentHealth != 0)
				{
				    for (int i = 0 ; i < membersInParty ; i++)
				    {
					party [i].setCurrentHealth (party [i].currentHealth - randomSpec);
					if (party [i].currentHealth < 0)
					{
					    party [i].setCurrentHealth (0);
					}
				    }
				    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " released a cloud of toxic gas");
				    Stdout.println ("\t\t" + randomSpec + " damage dealt to all players in party");

				}
			    }
			}

			monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth - randomPlayerDamage);
			if (monster [monsterAt].currentHealth <= 0)
			{
			    monsterKilled ();
			    break;
			}

			while (true)
			{
			    if (party [attackOn].currentHealth > 0)
			    {
				party [attackOn].setCurrentHealth (party [attackOn].currentHealth - randomEnemyDamage);
				break;
			    }
			    else
				attackOn = (int) (Math.random () * membersInParty);
			}
			Stdout.println ("\t\t" + monster [monsterAt].monsterName + " attacked and dealt " + randomEnemyDamage + " damage to " + party [attackOn].memberName);

			if (party [attackOn].currentHealth < 0)
			{
			    party [attackOn].currentHealth = 0;
			    Stdout.println ("\t\t" + party [attackOn].memberName + " has fainted");
			}

			storeParty (party);

			int deadCount = 0;

			for (int i = 0 ; i < membersInParty ; i++)
			{
			    if (party [i].currentHealth == 0)
				deadCount += 1;
			}

			if (deadCount == membersInParty)
			{
			    partyDead ();
			    break;
			}
			Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
			Stdout.println ("\t\t---------");
			Stdout.println ("\t\t Party:");
			for (int x = 0 ; x < membersInParty ; x++)
			{
			    Stdout.print ("\t\t" + party [x].memberName);
			    Stdout.print ("\tHP:" + party [x].currentHealth + "/" + party [x].maxHealth);
			    Stdout.print ("\tMana:" + party [x].currentMana + "/" + party [x].maxMana);
			    Stdout.println ("\tDamage Range:" + party [x].weaponLow + "-" + party [x].weaponHigh);
			}
			Stdout.println ("\t\t-------------");
			Stdout.println ("\t\t0. Next turn");
			Stdout.println ("\t\t-------------");
			Stdout.println ("\n\n\n\n");

			Stdout.print ("Enter your selection -->");
			choice = Stdin.readInt ();
			Stdout.print ("\n\n\n\n\n\n\n\n");
			if (choice == 0)
			{
			    break;
			}
			else
			    break;
		    }
		    else
		    {
			Stdout.println ("\n\n\n\n\n\nYou dont have any more Throwing stars.");
			break;
		    }
		}
		if (choice == 0)
		{
		    break;
		}
	    }
	    else
		break;
	}

    }


    public static void healParty () throws IOException
    {
	itemAt = createItemArray ();
	getItems (itemAt);

	int randomEnemyDamage;
	int dmgDifEnemy = monster [monsterAt].damageHigh - monster [monsterAt].damageLow;
	randomEnemyDamage = ((int) ((Math.random () * (dmgDifEnemy + 1))) + monster [monsterAt].damageLow);

	int attackOn = (int) (Math.random () * membersInParty);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to heal?");
	    Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.print ("\t\t" + party [i].memberName);
		Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		Stdout.println ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
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
			if (itemAt [0].itemAmount > 0)
			{
			    Stdout.println ("\n\n\n\n\n\n");
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\t\tYou have healed 50 health to " + party [i].memberName);
			    itemAt [0].setAmount (itemAt [0].itemAmount - 1);
			    party [i].setCurrentHealth (party [i].currentHealth + 50);
			    if (party [i].currentHealth > party [i].maxHealth)
			    {
				party [i].currentHealth = party [i].maxHealth;
			    }

			    storeItems ();

			    //chance to use ability
			    int abilityChance;
			    abilityChance = (int) (Math.random () * 5); //make int 0-4

			    int randomSpec;
			    randomSpec = (int) ((Math.random () * (monster [monsterAt].special2 - monster [monsterAt].special + 1))) + monster [monsterAt].special;


			    if (abilityChance == 0)
			    {
				if (monster [monsterAt].type == 2)
				{
				    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomSpec))
				    {
					monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomSpec);
					Stdout.println ("\t\t" + monster [monsterAt].monsterName + " regenerated " + randomSpec + " health");
				    }
				}
				// drain health
				else if (monster [monsterAt].type == 3)
				{

				    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomEnemyDamage))
				    {
					monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomEnemyDamage);
					if (monster [monsterAt].currentHealth > monster [monsterAt].maxHealth)
					    monster [monsterAt].currentHealth = monster [monsterAt].maxHealth;
					Stdout.println ("\t\t" + monster [monsterAt].monsterName + " drained health equal to damage dealt");
				    }
				}
				// area of effect
				else if (monster [monsterAt].type == 5)
				{
				    for (int x = 0 ; x < membersInParty ; x++)
				    {
					party [x].setCurrentHealth (party [x].currentHealth - randomSpec);
					if (party [x].currentHealth < 0)
					{
					    party [x].setCurrentHealth (0);
					}
				    }
				    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " released a cloud of toxic gas");
				    Stdout.println ("\t\t" + randomSpec + " damage dealt to all players in party");

				}
			    }

			    while (true)
			    {
				if (party [attackOn].currentHealth > 0)
				{
				    party [attackOn].setCurrentHealth (party [attackOn].currentHealth - randomEnemyDamage);
				    break;
				}
				else
				    attackOn = (int) (Math.random () * membersInParty);
			    }

			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " attacked and dealt " + randomEnemyDamage + " damage to " + party [attackOn].memberName);
			    Stdout.println ("\t\t-------------");

			    if (party [attackOn].currentHealth < 0)
			    {
				party [attackOn].currentHealth = 0;
				Stdout.println ("\t\t" + party [attackOn].memberName + " has fainted");
			    }

			    storeParty (party);

			    int deadCount = 0;

			    for (int x = 0 ; x < membersInParty ; x++)
			    {
				if (party [x].currentHealth == 0)
				    deadCount += 1;
			    }

			    if (deadCount == membersInParty)
			    {
				partyDead ();
				break;
			    }
			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
			    Stdout.println ("\t\t---------");
			    Stdout.println ("\t\t Party:");
			    for (int x = 0 ; x < membersInParty ; x++)
			    {
				Stdout.print ("\t\t" + party [x].memberName);
				Stdout.print ("\tHP:" + party [x].currentHealth + "/" + party [x].maxHealth);
				Stdout.print ("\tMana:" + party [x].currentMana + "/" + party [x].maxMana);
				Stdout.println ("\tDamage Range:" + party [x].weaponLow + "-" + party [x].weaponHigh);
			    }
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\t\t0. Next turn");
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\n\n\n\n");

			    Stdout.print ("Enter your selection -->");
			    choice = Stdin.readInt ();
			    Stdout.print ("\n\n\n\n\n\n\n\n");
			    if (choice == 0)
			    {
				break;
			    }
			    else
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

	int randomEnemyDamage;
	int dmgDifEnemy = monster [monsterAt].damageHigh - monster [monsterAt].damageLow;
	randomEnemyDamage = ((int) ((Math.random () * (dmgDifEnemy + 1))) + monster [monsterAt].damageLow);

	int attackOn = (int) (Math.random () * membersInParty);
	while (true)
	{
	    Stdout.println ("\n\n\n");
	    Stdout.println ("\t\t -----------");
	    Stdout.println ("\t\t Who do you want to restore mana to?");
	    Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
	    Stdout.println ("\t\t -----------");
	    for (int i = 0 ; i < membersInParty ; i++)
	    {
		Stdout.print ("\t\t" + party [i].memberName);
		Stdout.print ("\tHP:" + party [i].currentHealth + "/" + party [i].maxHealth);
		Stdout.println ("\t Mana:" + party [i].currentMana + "/" + party [i].maxMana);
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

			if (itemAt [1].itemAmount > 0)
			{
			    Stdout.println ("\n\n\n\n\n\n");
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\t\tYou have restored 20 mana to " + party [i].memberName);

			    itemAt [1].setAmount (itemAt [1].itemAmount - 1);

			    party [i].setCurrentMana (party [i].currentMana + 20);
			    if (party [i].currentMana > party [i].maxMana)
			    {
				party [i].currentMana = party [i].maxMana;
			    }

			    storeItems ();

			    //chance to use ability
			    int abilityChance;
			    abilityChance = (int) (Math.random () * 5); //make int 0-4

			    int randomSpec;
			    randomSpec = ((int) ((Math.random () * (monster [monsterAt].special2 - monster [monsterAt].special + 1)))) + monster [monsterAt].special;


			    if (abilityChance == 0)
			    {
				if (monster [monsterAt].type == 2)
				{
				    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomSpec))
				    {
					monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomSpec);
					Stdout.println ("\t\t" + monster [monsterAt].monsterName + " regenerated " + randomSpec + " health");
				    }
				}
				// drain health
				else if (monster [monsterAt].type == 3)
				{
				    if (monster [monsterAt].maxHealth <= (monster [monsterAt].currentHealth + randomEnemyDamage))
				    {
					monster [monsterAt].setCurrentHealth (monster [monsterAt].currentHealth + randomEnemyDamage);
					if (monster [monsterAt].currentHealth > monster [monsterAt].maxHealth)
					    monster [monsterAt].currentHealth = monster [monsterAt].maxHealth;
					Stdout.println ("\t\t" + monster [monsterAt].monsterName + " drained health equal to damage dealt");
				    }
				}
				// area of effect
				else if (monster [monsterAt].type == 5)
				{
				    for (int x = 0 ; x < membersInParty ; x++)
				    {
					party [x].setCurrentHealth (party [x].currentHealth - randomSpec);
					if (party [x].currentHealth < 0)
					{
					    party [x].setCurrentHealth (0);
					}
				    }
				    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " released a cloud of toxic gas");
				    Stdout.println ("\t\t" + randomSpec + " damage dealt to all players in party");

				}
			    }

			    while (true)
			    {
				if (party [attackOn].currentHealth > 0)
				{
				    party [attackOn].setCurrentHealth (party [attackOn].currentHealth - randomEnemyDamage);
				    break;
				}
				else
				    attackOn = (int) (Math.random () * membersInParty);
			    }

			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + " attacked and dealt " + randomEnemyDamage + " damage to " + party [attackOn].memberName);
			    Stdout.println ("\t\t-------------");

			    if (party [attackOn].currentHealth < 0)
			    {
				party [attackOn].currentHealth = 0;
				Stdout.println ("\t\t" + party [attackOn].memberName + " has fainted");
			    }

			    storeParty (party);

			    int deadCount = 0;

			    for (int x = 0 ; x < membersInParty ; x++)
			    {
				if (party [x].currentHealth == 0)
				    deadCount += 1;
			    }

			    if (deadCount == membersInParty)
			    {
				partyDead ();
				break;
			    }
			    Stdout.println ("\t\t" + monster [monsterAt].monsterName + "'s HP: " + monster [monsterAt].currentHealth + "/" + monster [monsterAt].maxHealth);
			    Stdout.println ("\t\t---------");
			    Stdout.println ("\t\t Party:");
			    for (int x = 0 ; x < membersInParty ; x++)
			    {
				Stdout.print ("\t\t" + party [x].memberName);
				Stdout.print ("\tHP:" + party [x].currentHealth + "/" + party [x].maxHealth);
				Stdout.print ("\tMana:" + party [x].currentMana + "/" + party [x].maxMana);
				Stdout.println ("\tDamage Range:" + party [x].weaponLow + "-" + party [x].weaponHigh);
			    }
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\t\t0. Next turn");
			    Stdout.println ("\t\t-------------");
			    Stdout.println ("\n\n\n\n");

			    Stdout.print ("Enter your selection -->");
			    choice = Stdin.readInt ();
			    Stdout.print ("\n\n\n\n\n\n\n\n");
			    if (choice == 0)
			    {
				break;
			    }
			    else
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
    ///////////////////////////////////////////////////
    public static ItemsOwned[] createItemArray ()
    {
	ItemsOwned[] temp;
	temp = new ItemsOwned [3];
	return temp;
    }


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


    private static Monster[] monstersArray ()
    {
	Monster[] temp;
	temp = new Monster [maxMonsters];
	return temp;
    }


    public static void getMonster (Monster[] a) throws IOException
    {
	String name;
	String nowHealth;
	int nHP;
	String mHealth;
	int mHP;
	String maxDamage;
	int maxDmg;
	String minDamage;
	int minDmg;
	String spec;
	int spe;
	String spec2;
	int spe2;
	String loot;
	int lootq;
	String type;
	int tp;

	int fileSize;
	String str;

	BufferedReader infile;
	infile = new BufferedReader (new FileReader ("monsters.txt"));

	str = infile.readLine ();
	fileSize = Integer.parseInt (str);
	monsterTypes = fileSize;

	for (int i = 0 ; i < fileSize ; i++)
	{
	    name = infile.readLine ();
	    nowHealth = infile.readLine ();
	    nHP = Integer.parseInt (nowHealth);
	    mHealth = infile.readLine ();
	    mHP = Integer.parseInt (mHealth);
	    minDamage = infile.readLine ();
	    minDmg = Integer.parseInt (minDamage);
	    maxDamage = infile.readLine ();
	    maxDmg = Integer.parseInt (maxDamage);
	    spec = infile.readLine ();
	    spe = Integer.parseInt (spec);
	    spec2 = infile.readLine ();
	    spe2 = Integer.parseInt (spec2);
	    loot = infile.readLine ();
	    lootq = Integer.parseInt (loot);
	    type = infile.readLine ();
	    tp = Integer.parseInt (type);

	    a [i] = new Monster (name, nHP, mHP, minDmg, maxDmg, spe, spe2, lootq, tp);
	    str = infile.readLine ();
	}


	infile.close ();
    }


    private static void storeMonster (Monster[] a) throws IOException
    {
	PrintWriter output;
	output = new PrintWriter (new FileWriter ("monsters.txt"));

	output.println (monsterTypes); // totoal number of objects in array
	for (int i = 0 ; i < monsterTypes ; i++)
	{
	    output.println (a [i].monsterName);  // write
	    output.println (a [i].currentHealth);  // write
	    output.println (a [i].maxHealth);  // write
	    output.println (a [i].damageLow);  // write
	    output.println (a [i].damageHigh);  // write
	    output.println (a [i].special);  // write
	    output.println (a [i].special2);  // write
	    output.println (a [i].lootQual);  // write
	    output.println (a [i].type);
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


