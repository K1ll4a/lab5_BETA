package Mclass;
import java.util.Scanner;

import static Mclass.Mclass.parseOrganizationType;
import static Mclass.Mclass.parseUnitOfMeasure;

public class Filler  {
    public void Fill(Mclass pr) {
        Mclass.Organization org = new Mclass.Organization();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            if (name.equals("")) {
                System.out.println("Значение не может быть null");
            }
            if (name.matches("\\d+")) {
                System.out.println("Строка не должна содержать число!");
            } else {
                pr.setName(name);
                break;
            }
        }
        while (true) {
                   System.out.print("Введите координату x: ");
                   try {
                       long x = Long.parseLong(scanner.nextLine());
                       System.out.print("Введите координату y: ");
                       long y = Long.parseLong(scanner.nextLine());
                        pr.setCoordinates(new Mclass.Coordinates(x, y));

                       break;
                    } catch (NumberFormatException e) {
                        System.out.println("Вводить надо число!");
                   }
                }

        while(true){
            System.out.print("Введите цену: ");
            try{
                double price = Double.parseDouble(scanner.nextLine());
                if(price<0){
                    System.out.println("Цена неверно задана!");
                }else {
                    pr.setPrice(price);
                    break;
                }
            }catch (NumberFormatException e){
                System.out.println("Вводить надо число!");
            }
        }




            System.out.print("Введите стоимость производства: ");
            float manufactureCost = Float.parseFloat(scanner.nextLine());
            pr.setManufactureCost(manufactureCost);
            while(true){
                System.out.print("Введите единицу измерения (KILOGRAMS, METERS, PCS, MILLILITERS, GRAMS):");
                String input = scanner.nextLine().toUpperCase();
                Mclass.UnitOfMeasure unitOfMeasure = parseUnitOfMeasure(input);
                if(unitOfMeasure != null){
                    pr.setUnitOfMeasure(unitOfMeasure);
                    break;
                }

            }

            while(true){
                System.out.print("Введите имя для организации: ");
                String name = scanner.nextLine();
                if(name.equals("")){
                    System.out.println("Значение не может быть null!");
                }else{
                    org.setName(name);
                    break;
                }
            }

            while(true){
                System.out.print("Введите полное имя для организации: ");
                String fullName = scanner.nextLine();
                if(fullName.equals("")){
                    System.out.println("Значение не можеть быть null!");
                }else{
                    org.setFullName(fullName);
                    break;
                }
            }
            while(true){
                System.out.print("Введите тип организации (PUBLIC,TRUST,PRIVATE_LIMITED_COMPANY): ");
                String input = scanner.nextLine().toUpperCase();
                Mclass.OrganizationType organizationType = parseOrganizationType(input);
                if(organizationType != null){
                    org.setOrganizationType(Mclass.OrganizationType.valueOf(input));
                    pr.setManufacturer(org);
                    break;
                }
            }






        }
    }
