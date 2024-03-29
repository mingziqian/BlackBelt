package com.example.amill.cse5911dojo;

import android.os.Bundle;

import java.util.Vector;

import android.os.Bundle;
import java.util.Vector;

public class LevelManager
{
    private int currentLevel;
    private Vector levelList;

    public void saveState(Bundle map)
    {
        map.putInt("LevelManager-currentLevel", currentLevel);
    }

    public void restoreState(Bundle map)
    {
        currentLevel = map.getInt("LevelManager-currentLevel");
    }

    public LevelManager(byte[] levels, int startingLevel)
    {
        String allLevels = new String(levels);
        allLevels = allLevels.replaceAll("(\\r)","");
        currentLevel = startingLevel;
        levelList = new Vector();

        int nextLevel = allLevels.indexOf("\n\n");
        if (nextLevel == -1 && allLevels.trim().length() != 0)
        {
            nextLevel = allLevels.length();
        }

        while (nextLevel != -1)
        {
            String currentLevel = allLevels.substring(0, nextLevel).trim();

            levelList.addElement(getLevel(currentLevel));

            allLevels = allLevels.substring(nextLevel).trim();

            if (allLevels.length() == 0)
            {
                nextLevel = -1;
            }
            else
            {
                nextLevel = allLevels.indexOf("\n\n");

                if (nextLevel == -1)
                {
                    nextLevel = allLevels.length();
                }
            }
        }

        if (currentLevel >= levelList.size())
        {
            currentLevel = 0;
        }
    }

    private byte[][] getLevel(String data)
    {
        byte[][] temp = new byte[8][12];

        for (int j=0 ; j<12 ; j++)
        {
            for (int i=0 ; i<8 ; i++)
            {
                temp[i][j] = -1;
            }
        }

        int tempX = 0;
        int tempY = 0;

        for (int i=0 ; i<data.length() ; i++)
        {
            if (data.charAt(i) >= 48 && data.charAt(i) <= 55)
            {
                temp[tempX][tempY] = (byte)(data.charAt(i) - 48);
                tempX++;
            }
            else if (data.charAt(i) == 45)
            {
                temp[tempX][tempY] = -1;
                tempX++;
            }

            if (tempX == 8)
            {
                tempY++;

                if (tempY == 12)
                {
                    return temp;
                }

                tempX = tempY % 2;
            }
        }

        return temp;
    }

    public byte[][] getCurrentLevel()
    {
        if (currentLevel < levelList.size())
        {
            return (byte[][])levelList.elementAt(currentLevel);
        }

        return null;
    }

    public void goToNextLevel()
    {
        currentLevel++;
        if (currentLevel >= levelList.size()) {
            currentLevel = 0;
        }
    }

    public void goToFirstLevel()
    {
        currentLevel = 0;
    }

    public int getLevelIndex()
    {
        return currentLevel;
    }
}

