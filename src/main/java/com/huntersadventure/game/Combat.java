package com.huntersadventure.game;

import com.huntersadventure.gameobjects.Characters;
import com.huntersadventure.gameobjects.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

class Combat {
    boolean combatEnd = false;
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public String enemyEncounter(Characters enemy, Characters p1) {
        String result = "";
        String enemyName = enemy.getName();
        String input;
        String output;

        try {
            TimeUnit.MILLISECONDS.sleep(750);
            System.out.println("You have entered combat with " + enemyName);
            while (p1.getHealth() > 0 && enemy.getHealth() > 0) {
                TimeUnit.MILLISECONDS.sleep(750);
                System.out.println("Player health & shield: " + p1.getHealth() +
                        ", " + p1.getShield());
                System.out.println("Enemy health & shield: " + enemy.getHealth() +
                        ", " + enemy.getShield());
                System.out.println("What would you like to use?");
                System.out.println(">");
                input = in.readLine();
                output = inputParse(input);
                if (itemInInventory(input,p1)) {
                    TimeUnit.MILLISECONDS.sleep(750);
                    Item itemUsed = getItemFromName(input, p1);
                    playerMoveResolution(itemUsed, enemy, p1);
                }else {
                    TimeUnit.MILLISECONDS.sleep(750);
                    System.out.println("You can't use that");
                    System.out.println("type in a weapon");
                }
            }
            System.out.println("COMBAT END");
            if (p1.getHealth() <= 0) {
                System.out.println("YOU HAVE DIED");
                result = "enemyWin";
            } else {
                System.out.println("ENEMY HAS DIED");
                result = "playerWin";
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void playerMoveResolution(Item item, Characters enemy, Characters p1) throws InterruptedException {
        int playerAttackDmg = item.getValue();
        int enemyAttackDmg = enemy.getDamage();

        int newEnemyHealth = enemy.getHealth() - playerAttackDmg;
        int newPlayerHealth = p1.getHealth() - enemyAttackDmg;
        int newEnemyShield = enemy.getShield() - playerAttackDmg;
        int newPlayerShield = p1.getShield() - enemyAttackDmg;

        System.out.println("You use your " + item.getName() + " and deal " + playerAttackDmg + " damage");
        if (enemy.getShield() > 0) {
            enemy.setShield(newEnemyShield);
        } else {
            enemy.setHealth(newEnemyHealth);
        }
        System.out.println("Enemy health & shield: " + enemy.getHealth() + ", " + enemy.getShield());
        TimeUnit.MILLISECONDS.sleep(750);

        System.out.println(enemy.getName() + " attacks you");
        if (p1.getShield() > 0) {
            p1.setShield(newPlayerShield);
        } else {
            p1.setHealth(newPlayerHealth);
        }
        TimeUnit.MILLISECONDS.sleep(750);
    }

    public boolean itemInInventory(String itemName, Characters p1) {
        boolean res = false;
        for (Item targetItem : p1.getInventory()) {
            if (targetItem.getName().equals(itemName)) {
                res = true;
                break;
            }
        }
        return res;
    }

    public Item getItemFromName(String itemName, Characters p1) {
        Item res = null;
        for (Item targetItem : p1.getInventory()) {
            if (targetItem.getName().equals(itemName)) {
                res = targetItem;
            }
        }
        return res;
    }

    public String inputParse(String input){
        return input.trim().toLowerCase();
    }

}