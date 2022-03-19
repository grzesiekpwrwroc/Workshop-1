import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;

    public static void main(String[] args) throws FileNotFoundException {
        tasks = readFromFile("/home/grzesiek/Projects/Workshop-1/Module/05_attachment_Zasoby do projektu.pl/tasks.csv");
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(ConsoleColors.BLUE + "Please select an option");
            System.out.println(ConsoleColors.RESET + "add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {

                case "add": {
                    addTask(tasks);
                    System.out.println("Add task");
                    break;
                }

                case "list": {
                    listTasks(tasks);
                    break;
                }
                case "remove": {
                    removeTask(tasks);
                    System.out.println("remove task");
                    break;
                }
                case "exit": {
                    exit();
                    System.out.println("Program zakończył działanie");
                    isRunning = false;
                    break;
                }
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }

    private static void exit() {
        writeToFile(tasks, "/home/grzesiek/Projects/Workshop-1/Module/05_attachment_Zasoby do projektu.pl/tasks.csv");

    }

    private static void removeTask(String[][] task) {
        String[][] copy = new String[task.length][task[0].length];
        System.out.println("Please select number to remove");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        int k = 0;
        int l = 0;
        for (int i = 0; i < task.length && k < task.length; i++, k++) {
            if (i == index) {
                k--;
                l--;
                continue;
            } else {
                for (int j = 0; j < task[0].length && l < task[0].length; j++, l++) {
                    //if (i != index) {
                    copy[k][l] = task[i][j];
                    //
                }
            }
        }


        tasks = Arrays.copyOf(copy, task.length - 1);
    }


    private static void listTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println("");
        }

    }

    private static String[][] addTask(String[][] task) {
        //String[][] newTasks1=Arrays.copyOf(tasks, tasks.length+1);

        String[][] newTasks = copyArray(task);

        Scanner scanner = new Scanner(System.in);
        //String task = "";
        System.out.println("Please add task description");
        newTasks[newTasks.length - 1][0] = scanner.nextLine();
        //task+= ", ";
        System.out.println("Please add task due date");
        newTasks[newTasks.length - 1][1] = scanner.nextLine();
        // task+=", ";
        System.out.println("Is your task is important? true/false");
        newTasks[newTasks.length - 1][2] = scanner.nextLine();
        tasks = newTasks;

        return tasks;


    }

    public static String[][] readFromFile(String path) throws FileNotFoundException {
        Path path1 = Paths.get(path);
        File file = new File(path);
        String line = new String();
        int size = sizeOfArray();
        String[][] array = new String[size][3];
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < size; i++) {
            line = scanner.nextLine();
            array[i] = line.split(",");
        }

        return array;
    }

    public static int sizeOfArray() {
        int size = 0;
        File file = new File("/home/grzesiek/Projects/Workshop-1/Module/05_attachment_Zasoby do projektu.pl/tasks.csv");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                size++;
                String line = scanner.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Błąd pliku");
        }
        return size;
    }

    public static String[][] copyArray(String[][] array) {
        String[][] copy = new String[array.length + 1][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                copy[i][j] = array[i][j];
            }

        }
        return copy;
    }

    public static void writeToFile(String[][] tasks, String path) {
        //File file = new File(path);
        Path file = Paths.get(path);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                sb.append(tasks[i][j]).append(",");
            }
            sb.append("\n");
        }
        try {
            Files.writeString(file, sb.toString(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
