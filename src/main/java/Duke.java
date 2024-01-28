import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static ChatBot createPaimonChatBot() {
        String logo = "      ___          ___                     ___          ___          ___     \n"+
                "     /\\  \\        /\\  \\         ___       /\\__\\        /\\  \\        /\\__\\    \n"+
                "    /::\\  \\      /::\\  \\       /\\  \\     /::|  |      /::\\  \\      /::|  |   \n"+
                "   /:/\\:\\  \\    /:/\\:\\  \\      \\:\\  \\   /:|:|  |     /:/\\:\\  \\    /:|:|  |   \n"+
                "  /::\\~\\:\\  \\  /::\\~\\:\\  \\     /::\\__\\ /:/|:|__|__  /:/  \\:\\  \\  /:/|:|  |__ \n"+
                " /:/\\:\\ \\:\\__\\/:/\\:\\ \\:\\__\\ __/:/\\/__//:/ |::::\\__\\/:/__/ \\:\\__\\/:/ |:| /\\__\\\n"+
                " \\/__\\:\\/:/  /\\/__\\:\\/:/  //\\/:/  /   \\/__/~~/:/  /\\:\\  \\ /:/  /\\/__|:|/:/  /\n"+
                "      \\::/  /      \\::/  / \\::/__/          /:/  /  \\:\\  /:/  /     |:/:/  / \n"+
                "       \\/__/       /:/  /   \\:\\__\\         /:/  /    \\:\\/:/  /      |::/  /  \n"+
                "                  /:/  /     \\/__/        /:/  /      \\::/  /       /:/  /   \n"+
                "                  \\/__/                   \\/__/        \\/__/        \\/__/    \n";
        String botName = "Paimon";
        List<String> greetings = List.of(
                "Ah, there you are! Hello! Paimon wondered where you were! This is going to be so much fun, right?",
                "Ahoy there! It's great to see you! Paimon's hungry!",
                "Ah, Paimon missed you! It's been so long...",
                "Ad astra abyssosque, welcome to Paimon's house!",
                "Good morning, Traveler. Ah... what's it like out today? Paimon wants to hear your story."
        );

        List<String> farewells = List.of(
                "Farewell, it was fun to meet you! Take care, see you later, and may you find many new treasures",
                "Farewell, until we meet again!",
                "Safe travels, and take care!",
                "Good luck! And don't spend all your Mora in one place.",
                "Adios!"
        );

        return new ChatBot(botName, logo, greetings, farewells);
    }

    private static ChatBot createPaimonChatBotForTesting() {
        String logo = "logo";
        String botName = "Paimon";
        List<String> greetings = List.of("Ad astra abyssosque, welcome to Paimon's house!");
        List<String> farewells = List.of("Safe travels, and take care!");
        return new ChatBot(botName, logo, greetings, farewells);
    }

    public static String[] resolveInput(String input) {
        return input.split("\\s+", 2);
    }

    public static String retrieveCommand(String input) {
        return resolveInput(input)[0];
    }

    public static String retrieveParameters(String input) {
        String[] resolvedInput = resolveInput(input);
        return ((resolvedInput.length > 1) ? resolvedInput[1] : "");
    }


    public static void main(String[] args) throws IOException {
        ChatBot Paimon;
        if (args.length > 0 && args[0].equals("test-mode")) {
            Paimon = createPaimonChatBotForTesting();
        } else {
            Paimon = createPaimonChatBot();
        }
        Paimon.greet();
        Paimon.loadTasksFromFile();
        Scanner scanner = new Scanner(System.in);
        String command, input;
        String parameters;
        while (true) {
            input = scanner.nextLine();
            command = retrieveCommand(input);
            parameters = retrieveParameters(input);
            try {
                switch (command) {
                case "list":
                    Paimon.listTasks();
                    break;
                case "mark":
                    Paimon.markTaskAsDone(parameters);
                    break;
                case "unmark":
                    Paimon.markTaskAsUndone(parameters);
                    break;
                case "todo":
                    Paimon.addToDo(parameters);
                    break;
                case "deadline":
                    Paimon.addDeadline(parameters);
                    break;
                case "event":
                    Paimon.addEvent(parameters);
                    break;
                case "delete":
                    Paimon.deleteTask(parameters);
                    break;
                case "bye":
                    Paimon.saveTasksToFile();
                    Paimon.bye();
                    return;
                case "":
                    throw new ChatBotCommandException("Empty command.");
                default:
                    throw new ChatBotCommandException("Wrong commands.");
                }
            } catch (ChatBotCommandException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
