import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/*
public class FileListManager {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean quit = false;
        while (!quit) {
            displayMenu();
            String choice = SafeInput.getRegExString(sc,"Enter your choice: ", "[AaDdOoSsCcVvQq]");
            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "O":
                    openFile();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "V":
                    printList();
                    break;
                case "Q":
                    quit = SafeInput.getYNConfirm(sc,"Are you sure you want to quit? ");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("O - Open File from disk");
        System.out.println("S - Save current list file to disk");
        System.out.println("C - Clear, removes all elements from disk");
        System.out.println("V - View the list");
        System.out.println("Q - Quit the program");
    }

    private static void addItem() {
        String item = getNonEmptyString("Enter the item to add: ");
        itemList.add(item);
        System.out.println("Item added: " + item);
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        int index = getItemIndex("Enter the number of the item to delete: ", itemList.size());
        String item = itemList.remove(index - 1);
        System.out.println("Item deleted: " + item);
    }

    private static void printList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("List:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, itemList.get(i));
        }
    }

    private static String getNonEmptyString(String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
        }
        return input;
    }

    private static int getItemIndex(String prompt, int maxIndex) {
        int index = SafeInput.getRangedInt(sc,prompt, 1, maxIndex);
        if (index > itemList.size()) {
            System.out.println("Invalid item number.");
            return getItemIndex(prompt, maxIndex);
        }
        return index;
    }
*/
    public class FileListManager {
        private static ArrayList<String> itemList = new ArrayList<>();
        private static Scanner sc = new Scanner(System.in);
        private static boolean needsToBeSaved = false;
        private static String currentFileName = "";

        public static void main(String[] args) {
            boolean quit = false;
            while (!quit) {
                displayMenu();
                String choice = SafeInput.getRegExString(sc, "Enter your choice", "[AaDdOoSsCcVvQq]");
                switch (choice.toUpperCase()) {
                    case "A":
                        addItem();
                        needsToBeSaved = true;
                        break;
                    case "D":
                        deleteItem();
                        needsToBeSaved = true;
                        break;
                    case "O":
                        openFile();
                        needsToBeSaved = false;
                        break;
                    case "S":
                        saveList();
                        needsToBeSaved = false;
                        break;
                    case "C":
                        clearList();
                        needsToBeSaved = true;
                        break;
                    case "V":
                        printList();
                        break;
                    case "Q":
                        if(needsToBeSaved){
                            boolean conf = SafeInput.getYNConfirm(sc,"Your List is not saved. Do you still want to quit?");
                            if(conf){
                                quit = true;
                            }else{
                                break;
                            }
                        }else {
                            quit = SafeInput.getYNConfirm(sc, "Are you sure you want to quit?");
                            break;
                        }

                }
            }
        }

        private static void displayMenu() {
            System.out.println("Menu:");
            System.out.println("A - Add an item to the list");
            System.out.println("D - Delete an item from the list");
            System.out.println("O - Open File from disk");
            System.out.println("S - Save current list file to disk");
            System.out.println("C - Clear, removes all elements from disk");
            System.out.println("V - View the list");
            System.out.println("Q - Quit the program");
        }

        private static void addItem() {
            String item = getNonEmptyString("Enter the item to add: ");
            itemList.add(item);
            System.out.println("Item added: " + item);
        }

        private static void deleteItem() {
            if (itemList.isEmpty()) {
                System.out.println("The list is empty.");
                return;
            }
            int index = getItemIndex("Enter the number of the item to delete: ", itemList.size());
            String item = itemList.remove(index - 1);
            System.out.println("Item deleted: " + item);
        }

        private static void printList() {
            if (itemList.isEmpty()) {
                System.out.println("The list is empty.");
                return;
            }
            System.out.println("List:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, itemList.get(i));
            }
        }

        private static String getNonEmptyString(String prompt) {
            String input = "";
            while (input.isEmpty()) {
                System.out.print(prompt);
                input = sc.nextLine().trim();
            }
            return input;
        }

        private static int getItemIndex(String prompt, int maxIndex) {
            int index = SafeInput.getRangedInt(sc, prompt, 1, maxIndex);
            if (index > itemList.size()) {
                System.out.println("Invalid item number.");
                return getItemIndex(prompt, maxIndex);
            }
            return index;
        }

        private static void openFile() {
            if (needsToBeSaved) {
                boolean save = SafeInput.getYNConfirm(sc, "Do you want to save changes to the current list before opening another file? ");
                if (save) {
                    saveList();
                } else {
                    needsToBeSaved = false;
                }
            }
            String fileName = getNonEmptyString("Enter the file name: ");
            try {
                Scanner fileScanner = new Scanner(new File(fileName));
                itemList.clear();
            while (fileScanner.hasNextLine()) {
                String item = fileScanner.nextLine().trim();
                if (!item.isEmpty()) {
                    itemList.add(item);
                }
            }
            System.out.println("File opened successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static void saveList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        String fileName = getNonEmptyString("Enter the file name: ") + ".txt";
        try {
            PrintWriter fileWriter = new PrintWriter((fileName));
            for (String item : itemList) {
                fileWriter.println(item);
            }
            fileWriter.close();
            System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving list to file: " + e.getMessage());
        }
    }

    private static void clearList() {
        itemList.clear();
        System.out.println("List cleared successfully.");
    }
}