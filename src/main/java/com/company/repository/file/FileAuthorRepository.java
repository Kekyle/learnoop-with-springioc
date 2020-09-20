package com.company.repository.file;

import com.company.entity.Author;
import com.company.repository.AuthorRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAuthorRepository extends FileAbstractRepository implements AuthorRepository {
    private static final int NAME = 1;

    public FileAuthorRepository(File file) {
        super(file);
    }

    @Override
    public boolean saveAuthor(Author author) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("\n");
            int newId = getLastId() + 1;
            author.setId(newId);
            fileWriter.write(author.getId() + SPLITTER + author.getName());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

//    private int getLastId() {
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = null;
//            while (true) {
//                String s = bufferedReader.readLine();
//                if (s == null) {
//                    return Integer.parseInt(line.split(SPLITTER)[0]);
//                }
//                line = s;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    private void addFromFileToAuthorList(List<Author> list, List<String> comments) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) {
                    list.add(null);
                    comments.add(s);
                    continue;
                }
                Author author = new Author(Integer.parseInt(split[ID]), split[NAME]);
                list.add(author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAll(List<Author> list, List<String> comments) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            int count = 0;
            int nullCount = 0;
            for (Author author : list) {
                if (author == null) {
                    bufferedWriter.write(comments.get(nullCount++));
                } else {
                    bufferedWriter.write(author.getId() + SPLITTER + author.getName());
                }
                if (count != list.size() - 1){
                    bufferedWriter.write("\n");
                    count++;
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String updateAuthorName(String name, int id) {
        List<Author> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAuthorList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                String oldName = list.get(i).getName();
                list.get(i).setName(name);
                writeAll(list, listComment);
                return oldName;
            }
        }
        return null;
    }

    @Override
    public void deleteAuthorByName(String name) {
        List<Author> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAuthorList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getName().equals(name)) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public void deleteAuthorById(int id) {
        List<Author> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAuthorList(list, listComment);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).getId() == id) {
                list.remove(i);
                writeAll(list, listComment);
                break;
            }
        }
    }

    @Override
    public Author[] getAllAuthors() {
        List<Author> list = new ArrayList<>();
        List<String> listComment = new ArrayList<>();
        addFromFileToAuthorList(list, listComment);
        return list.toArray(new Author[0]);
    }

    @Override
    public Author getAuthorByName(String name) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[NAME].equals(name)) {
                    return new Author(split[NAME]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author getAuthorById(int id) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (Integer.parseInt(split[ID]) == id) {
                    return new Author(split[NAME]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsAuthor(Author author) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                String[] split = s.split(SPLITTER);
                if (split[ID].equals("#")) continue;
                if (split[NAME].equals(author.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsAuthorById(int id) {
        return super.containsByIdOrPrice(id, ID);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (Integer.parseInt(split[ID]) == id) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    @Override
    public boolean containsAuthorByName(String name) {
        return super.containsByName(name, NAME);
//        try {
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                String[] split = s.split(SPLITTER);
//                if (split[ID].equals("#")) continue;
//                if (split[NAME].equals(name)) {
//                    return true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
    }
}
