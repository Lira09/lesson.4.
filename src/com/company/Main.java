package com.company;

import java.net.PortUnreachableException;
import java.util.Random;

public class Main {
    public static final int GOLEM = 4;
    public static final int LUCKY = 5;
    public static final int BERSERK = 6;
    public static final int THOR = 7;
    public static int bossHealth = 850;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {260, 270, 250, 220, 350, 110, 200, 210};
    public static int[] heroesDamage = {20, 15, 10, 0, 10, 15, 20, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber = 0;
    public static int randomChance = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        Random random = new Random();
        randomChance = random.nextInt(5) + 1;
        roundNumber++;
        System.out.println("ROUND №" + roundNumber);
        System.out.println("randomChance= " + randomChance);
        changeDefence();
        if (bossHealth > 0 && !isThorDeaf()) {
            bossHits();
        }

        berserk();
        lucky();
        golem();
        medicHeal();
        heroesHit();
        printStatistics();

    }

    public static void changeDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }


    public static void printStatistics() {
        System.out.println("_______________________");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
    }


    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(10) + 2;
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    {

    }


    public static void medicHeal() {
        //    boolean healit = false;
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                    //     healit = true;
                    Random r = new Random();
                    int a = r.nextInt(60);
                    heroesHealth[i] += a;
                    System.out.println(heroesAttackType[3] + " is heal " + heroesAttackType[i] + " +" + a + " hp");
                    break;
                }
            }
        }

    }

    public static void golem() {
        if (heroesHealth[GOLEM] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    int damage = bossDamage / 5;
                    heroesHealth[i] += damage;
                    if (heroesHealth[GOLEM] - damage < 0) heroesHealth[GOLEM] = 0;
                    else heroesHealth[GOLEM] -= damage;
                }

            }
        }

    }

    public static void lucky() {
        if (heroesHealth[LUCKY] > 0 && randomChance == 5) {
            heroesHealth[LUCKY] += 50;
            System.out.println(" Lucky is avoid");

        }

    }

    public static void berserk() {
        if (heroesHealth[BERSERK] > 0) {
            int berserk = bossDamage - 25;
            if (bossHealth - berserk < 0) bossHealth = 0;
            else bossHealth -= berserk;
            System.out.println(heroesAttackType[6] + " is blocked!");
        }
    }

    public static boolean isThorDeaf() {
        if (heroesHealth[THOR] > 0 && randomChance == 2) {
            System.out.println("Thor stunned Boss!");
            return true;
        } else return false;
    }

}



