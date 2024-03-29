package FirstLab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CommandLineApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProcessManager processManager = new ProcessManager();

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim(); // Читаем ввод пользователя и удаляем лишние пробелы

            // Разбиваем ввод на команду и аргументы
            String[] tokens = input.split("\\s+");
            String command = tokens[0];
            String[] arguments = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, arguments, 0, tokens.length - 1);

            // Обработка команд
            switch (command) {
                case "help":
                    System.out.println("Список команд:");
                    System.out.println("1. ps - Показать запущенные процессы");
                    System.out.println("2. threads - Показать запущенные потоки");
                    System.out.println("3. kill <PID> - Завершить процесс по его идентификатору");
                    System.out.println("4. find <filename> - Поиск файла в текущей директории и её поддиректориях");
                    System.out.println("5. top - Вывести информацию о загрузке процессора");
                    System.out.println("6. start <command> - Запустить новый процесс с указанной командой");
                    System.out.println("7. threads-count <PID> - Вывести количество потоков для указанного процесса");
                    System.out.println("8. vmstat - Отобразить нагрузу ОЗУ и ЦПУ.");
                    System.out.println("9. thread-info <PID> - Вывести информацию о потоках для указанного процесса");
                    System.out.println("10. exit - Завершить приложение");
                    break;
                case "ps":
                    processManager.showProcesses();
                    break;
                case "threads":
                    processManager.showThreads();
                    break;
                case "kill":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.killProcess(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды kill");
                    }
                    break;
                case "find":
                    if (arguments.length > 0) {
                        processManager.findFile(arguments[0]);
                    } else {
                        System.out.println("Неверное количество аргументов для команды find");
                    }
                    break;
                case "top":
                    processManager.showCPUUsage();
                    break;
                case "start":
                    if (arguments.length > 0) {
                        processManager.startNewProcess(String.join(" ", arguments));
                    } else {
                        System.out.println("Неверное количество аргументов для команды start");
                    }
                    break;
                case "threads-count":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.showThreadCount(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды threads-count");
                    }
                    break;
                case "vmstat":
                    processManager.showVmStat();
                    break;
                case "thread-info":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.showThreadInfo(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды thread-info");
                    }
                    break;
                case "exit":
                    scanner.close();
                    System.out.println("Выход из приложения.");
                    return;
                default:
                    System.out.println("Команда не распознана. Введите 'help' для просмотра списка команд.");
            }
        }
    }
}

