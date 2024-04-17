package processors;

import Mclass.Mclass;
import Mclass.Mclass.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class CommandProcessor {
    private final MclassCollection mclassCollection;
    private Scanner scanner;
    private final String fileName;
    private final Map<String, CommandHandler> commandHandlers;
    private static List<String> executedScripts;
    Random random = new Random();


    public CommandProcessor(MclassCollection var1, Scanner var2, String var3) {
        this.mclassCollection = var1;
        this.scanner = var2;
        this.fileName = var3;
        this.commandHandlers = new HashMap<>();
        this.executedScripts = new ArrayList<>();
        initializeCommandHandlers();
    }

    public void run() {
        while (true) {
            System.out.println("Введите команду (help для справки): ");
            String input = scanner.nextLine().trim();
            this.processCommand(input);
        }
    }

    private void initializeCommandHandlers() {
        this.commandHandlers.put("help", this::help);
        this.commandHandlers.put("info", this::info);
        this.commandHandlers.put("show", this::showCollection);
        this.commandHandlers.put("add", this::addMclass);
        this.commandHandlers.put("update_id", this::updateMclass);
        this.commandHandlers.put("clear", this::clearCollection);
        this.commandHandlers.put("save", this::saveCollectionToFile);
        this.commandHandlers.put("remove_by_id id", this::removeMclassById);
        this.commandHandlers.put("execute_sctipt", this::executeScript);
        this.commandHandlers.put("exit", this::exit);
        this.commandHandlers.put("remove_last", this::removeLast);
        this.commandHandlers.put("remove_greater", this::removeGreater);
        this.commandHandlers.put("min_by_manufacture_cost", this::minByManufactureCost);
        this.commandHandlers.put("max_by_manufacture_cost", this::maxByManufactureCost);
        this.commandHandlers.put("count_less_than_manufacture_cost manufactureCost", this::countLessThanManufactureCost);
    }

    public void processCommand(String var1) {
        String[] var2 = var1.trim().split("\\s+", 2);
        String var3 = var2[0];
        CommandHandler var4 = (CommandHandler) this.commandHandlers.get(var3);
        if (var4 != null) {
            if (var2.length > 1) {
                var4.handle(var2[1]);
            } else {
                var4.handle((String) null);
            }
        } else {
            System.out.println("Неизвестная команда: " + var3 + ". Используйте (help), чтобы посмотреть поддерживаемые команды.");
        }
    }

    public void executeScript(String fileName) {
        if (executedScripts.contains(fileName)) {
            System.out.println("Обнаружена возможность бесконечной рекурсии: скрипт " + fileName + " уже был выполнен ранее.");
        } else {
            executedScripts.add(fileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("add")) {
                        // Чтение данных для создания объекта Mclass
                        String name = reader.readLine();
                        Double price = Double.parseDouble(reader.readLine());
                        float manufactureCost = Float.parseFloat(reader.readLine());
                        long x = Long.parseLong(reader.readLine());
                        float y = Float.parseFloat(reader.readLine());
                        String unitOfMeasureString = reader.readLine();
                        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasureString);
                        Integer OrgId = Integer.parseInt(reader.readLine());
                        String orgName = reader.readLine();
                        String fullName = reader.readLine();
                        String typeString = reader.readLine();
                        OrganizationType type = typeString.isEmpty() ? null : OrganizationType.valueOf(typeString);
                        Organization manufacturer = new Organization(orgName, fullName, type);
                        Coordinates coordinates = new Coordinates(x, y);
                        Mclass mclass = new Mclass(name, coordinates, price, manufactureCost, unitOfMeasure, manufacturer);
                        mclassCollection.addMclass(mclass);
                    } else {
                        processCommand(line);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Ошибка: файл скрипта " + fileName + " не найден.");
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении скрипта: " + e.getMessage());
            } finally {
                executedScripts.remove(fileName);
            }
        }
    }


    private void help(String var1) {
        System.out.println("Список доступных команд:");
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести информацию о коллекции");
        System.out.println("show : вывести все элементы коллекции");
        System.out.println("add {element} : добавить новый элемент в коллекцию");
        System.out.println("update id {element} : обновить значение элемента коллекции");
        System.out.println("remove_by_id id : удалить элемент из коллекции по его id");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : считать и исполнить скрипт из файла");
        System.out.println("exit : завершить программу");
        System.out.println("remove_last : удалить последний элемент из коллекции");
        System.out.println("add_if_min {element} : добавить новый элемент, если он минимальный");
        System.out.println("remove_greater {element} : удалить все элементы, превышающие заданный");
        System.out.println("min_by_manufacture_cost : вывести объект с минимальной стоимостью производства");
        System.out.println("max_by_manufacture_cost : вывести объект с максимальной стоимостью производства");
        System.out.println("count_less_than_manufacture_cost manufactureCost : вывести количество элементов, стоимость производства которых меньше заданной");
    }

    private void info(String var1) {
        this.mclassCollection.info();
    }

    private void minByManufactureCost(String var1){
        this.mclassCollection.minByManufactureCost();
    }

    private void maxByManufactureCost(String var1){
        this.mclassCollection.maxByManufactureCost();
    }

    private void removeMclassById(String var1) {
        int var2 = Integer.parseInt(var1);
        this.mclassCollection.removeMclassById(var2);
    }
    public void removeGreater(String var1) {
        Mclass mclass = Mclass.parse(var1); // предполагается, что класс Mclass имеет статический метод parse
        this.mclassCollection.removeGreater(mclass);
    }

    private void showCollection(String var1) {
        Iterator var2 = this.mclassCollection.getMclasses().iterator();

        while (var2.hasNext()) {
            Mclass var3 = (Mclass) var2.next();
            System.out.println(var3);
        }
    }

    private void countLessThanManufactureCost(String var1){
        Double var2 = Double.parseDouble(var1);
        this.mclassCollection.countLessThanManufactureCost(var2);

    }

    private void clearCollection(String var1) {
        this.mclassCollection.clearCollection();
    }

    private void removeLast(String var1) {
        this.mclassCollection.removeLast();
    }

    private UnitOfMeasure promtUnitOfMeasure() {
        System.out.println("Введите единицу измерения (KILOGRAMS, METERS, PCS, MILLILITERS, GRAMS):");
        while (true) {
            try {
                String var1 = this.scanner.nextLine().toUpperCase();
                return UnitOfMeasure.valueOf(var1);
            } catch (IllegalArgumentException var2) {
                System.out.println("Некорректное значение. Пожалуйста, выберите из предложенных вариантов.");
            }
        }
    }

    private OrganizationType promtOrganizationType(String var1) {
        System.out.println(var1);

        while (true) {
            try {
                String var2 = this.scanner.nextLine().toUpperCase();
                return OrganizationType.valueOf(var2);
            } catch (IllegalArgumentException var3) {
                System.out.println("Некорректное значение. Пожалуйста выберите одно из предложенных вариантов.");
            }
        }

    }


    private Organization promtOrganization() {
        System.out.println("Введите данные об организации: ");
        boolean var1 = false;
        String var2 = "";

        do {
            try {
                System.out.print("Имя: ");
                var2 = this.scanner.nextLine();
                if (var2.length() > 0) {
                    var1 = true;
                } else {
                    System.out.println("Имя не может быть пустым.");
                }
            } catch (IllegalArgumentException var8) {
                System.out.println(var8.getMessage());
            }
        } while (!var1);
        var1 = false;
        String var3 = "";

        do {
            try {
                System.out.println("Полное имя: ");
                var3 = this.scanner.nextLine();
                if (var3.length() < 1307 && var3.length() > 0) {
                    var1 = true;
                } else {
                    System.out.println("Полное имя дожно быть не больше 1307 символов.");
                }
            } catch (IllegalArgumentException var7) {
                System.out.println(var7.getMessage());
            }
        } while (!var1);


        Integer var4 = random.nextInt(((1000 - 1) + 1) + 1);
        OrganizationType var5 = this.promtOrganizationType("Введите тип организации (PUBLIC,TRUST,PRIVATE_LIMITED_COMPANY): ");
        return new Organization(var4, var2, var3, var5);

    }

    private void addMclass(String var1){
        boolean var2 = false;
        String var3 = "";
        do{
            try{
                System.out.print("Введите название товара: ");
                var3 = this.scanner.nextLine();
                if(var3.length() >= 0){
                    var2 = true;
                }else {
                    System.out.println("Ошибка: Имя не может быть пустым. Пожалуйста, введите заново.");
                }
            }catch (IllegalArgumentException var17){
                System.out.println(var17.getMessage());
            }
        }while(!var2);
        var2 = false;
        float var4 = 0;

        do {
            try {
                System.out.print("Введите координату x: ");
                var4 = Float.parseFloat(this.scanner.nextLine());
                var2 = true;
            } catch (NumberFormatException var16) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста,введите число заново");
            }
        } while (!var2);
        var2 = false;
        float var5 = 0;

        do {
            try {
                System.out.print("Введите координату y: ");
                var5 = Float.parseFloat(this.scanner.nextLine());
                var2 = true;
            } catch (NumberFormatException var15) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста,введите число заново");
            }
        } while (!var2);
        var2 = false;
        Double var6 = null;

        do {
            try {
                System.out.print("Введите цену товара: ");
                var6 = Double.parseDouble(this.scanner.nextLine());
                if (var6 > 0) {
                    var2 = true;
                } else {
                    System.out.println("Ошибка: Price должен быть больше 0. Пожалуйста введите число заново");
                }
            } catch (NumberFormatException var14) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!var2);
        var2 = false;
        Float var7 = null;
        do {
            try {
                System.out.print("Введите стоимость производства: ");
                var7 = Float.parseFloat(this.scanner.nextLine());
                var2 = true;
            } catch (NumberFormatException var15) {
                System.out.println("Ошбика: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!var2);

        UnitOfMeasure var8 = this.promtUnitOfMeasure();
        Organization var9 = this.promtOrganization();
        Mclass var10 = new Mclass(var3, new Coordinates(var4, var5), var6, var7, var8, var9);
        this.mclassCollection.addMclass(var10);
        System.out.println("Новый продукт успешно добавлен в коллекцию.");

    }


    private void updateMclass(String var1) {
        long var2;
        try {
            var2 = Long.parseLong(var1);
        } catch (NumberFormatException var19) {
            System.out.println("Некорректный формат id. Пожалуйста,введите целое число.");
            return;
        }

        Mclass var3 = this.findMclassByID(var2);
        if (var3 == null) {
            System.out.println("Продукт с id " + var2 + " не найден.");
        } else {
            System.out.println("Введите данные о продукте: ");
            System.out.print("Введите название товара: ");
            String var4 = this.scanner.nextLine();
            boolean var5 = false;
            float var6 = 0;

            do {
                try {
                    System.out.print("Введите координату x: ");
                    var6 = Float.parseFloat(this.scanner.nextLine());
                    var5 = true;
                } catch (NumberFormatException var18) {
                    System.out.println("Ошибка: Некорректный формат числа. Пожалуйста,введите число заново");
                }
            } while (!var5);

            var5 = false;
            float var7 = 0;

            do {
                try {
                    System.out.print("Введите координату y: ");
                    var7 = Float.parseFloat(this.scanner.nextLine());
                    var5 = true;
                } catch (NumberFormatException var18) {
                    System.out.println("Ошибка: Некорректный формат числа. Пожалуйста,введите число заново");
                }
            } while (!var5);
            var5 = false;
            Double var8 = null;

            do {
                try {
                    System.out.print("Введите цену товара: ");
                    var8 = Double.parseDouble(this.scanner.nextLine());
                    if (var8 > 0) {
                        var5 = true;
                    } else {
                        System.out.println("Ошибка: Price должен быть больше 0. Пожалуйста введите число заново");
                    }
                } catch (NumberFormatException var16) {
                    System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
                }
            } while (!var5);
            var5 = false;
            Float var9 = null;
            do {
                try {
                    System.out.print("Введите стоимость производства: ");
                    var9 = Float.parseFloat(this.scanner.nextLine());
                    var5 = true;
                } catch (NumberFormatException var15) {
                    System.out.println("Ошбика: Некорректный формат числа. Пожалуйста, введите число заново.");
                }
            } while (!var5);


            UnitOfMeasure var11 = this.promtUnitOfMeasure();
            Organization var12 = this.promtOrganization();
            Long var13 = random.nextLong(1000);
            Mclass var14 = new Mclass(var4, new Coordinates(var6, var7), var8, var9, var11, var12);
            this.mclassCollection.updateMclass(var2, var14);
            System.out.println("Продукт с id " + var2 + " успешно обновлен.");

        }




    }
    private Mclass findMclassByID ( long var1){
        Iterator var2 = this.mclassCollection.getMclasses().iterator();

        Mclass var3;
        do {
            if (!var2.hasNext()) {
                return null;
            }
            var3 = (Mclass) var2.next();
        } while (!var3.getId().equals(var1));

        return var3;
    }

    private void saveCollectionToFile (String var1){
        try {
            this.mclassCollection.saveToFile(this.fileName);
        } catch (Exception var3) {
            System.err.println("Ошибка при сохранении коллекции: " + var3.getMessage());
        }
    }

    private void exit (String var1){
        System.out.println("Завершение работы программы.");
        System.exit(0);
    }

    @FunctionalInterface
    private interface CommandHandler {
        void handle(String var1);
    }
}