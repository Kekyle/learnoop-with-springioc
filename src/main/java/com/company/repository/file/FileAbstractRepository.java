package com.company.repository.file;

import java.io.*;

public abstract class FileAbstractRepository {
    protected static final int ID = 0;
    protected static final String SPLITTER = " ";
    protected static final String COMMENT_TAG = "#";
    protected File file;

    public FileAbstractRepository(File file) {
        this.file = file;
    }

    protected int getLastId() {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while (true) {
                String s = bufferedReader.readLine();
                if (s == null) {
                    return Integer.parseInt(getSplit(line)[0]);
                }
                line = s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String[] getSplit(String line) {
        return line.split(SPLITTER);
    }

    public boolean containsByIdOrPrice(int idOrPrice, int index) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = getSplit(s);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (Integer.parseInt(split[index]) == idOrPrice) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean containsByName(String name, int index) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = getSplit(s);
                if (split[ID].equals(COMMENT_TAG)) continue;
                if (split[index].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
