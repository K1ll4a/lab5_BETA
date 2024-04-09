package Mclass;

import java.util.Objects;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import java.time.format.DateTimeFormatter;

public class Mclass {
    private Double price; //Значение поля должно быть больше 0
    private String name;
    private float manufactureCost;
    private Coordinates coordinates;
    private long x;
    private float y;
    private String input;
    private UnitOfMeasure unitOfMeasure;
    private  Long id;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private java.time.LocalDateTime creationDate;
    private Organization manufacturer;




    public Mclass() {
        this.id = random.nextLong(1, 1001);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(0, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        creationDate = LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
        creationDate = creationDate.atZone(ZoneOffset.UTC).toLocalDateTime();


    }




    public static class Coordinates{
        private long x;
        private float y;
        public Coordinates(long x,float y){
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "{" + "x=" + x + ", y=" + y + '}';
        }
    }
    public enum UnitOfMeasure {
        KILOGRAMS,
        METERS,
        PCS,
        MILLILITERS,
        GRAMS;
    }
    public static class Organization{
        private static final Random idGenerator = new Random();
        private int id = generateId();//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private String name; //Поле не может быть null, строка не может быть пустой
        private  String fullName;//Длина строки не должна быть больше 1307, Значение этого поля должно быть уникальным, Поле не может быть null
        private OrganizationType type; //Поле может быть null
        public Organization(){



        }
        public Organization(String name, String fullName,OrganizationType type){
            this.id = id;
            this.name = validateName(name);
            this.fullName = validatefFullName(fullName);
            this.type = type;
        }
        private Integer generateId(){
            Random random = new Random();
            int id;
            do{
                id = random.nextInt(1000);
            }while (id == 0);
            return id;
        }
        private String validateName(String name){
            Objects.requireNonNull(name,"Значение этого поля не может быть null!");
            if(name.isEmpty()){
                throw new IllegalArgumentException("Поле не может быть пустым");
            }
            return name;
        }
        private String validatefFullName(String fullName){
            Objects.requireNonNull(fullName,"Значение этого поля не может быть null!");
            if(fullName.isEmpty()){
                throw new IllegalArgumentException("Поле не может быть пустым");
            }
            return fullName;
        }

        public void setName(String name) {
            if(name == null || name.isEmpty()){
                throw new IllegalArgumentException("Значение поля не может быть null!");
            }
            this.name = name;
        }

        public void setFullName(String fullName) {
            if(fullName == null || fullName.isEmpty() || fullName.length() > 1307){
                throw new IllegalArgumentException("Поле не может быть null,длина строки не может быть больше 1307");
            }
            this.fullName =fullName;
        }
        public void setOrganizationType(OrganizationType type){
            this.type = type;
        }
        @Override
        public String toString() {
            return "Organization{" + "id=" + id + ", name= " + name  + ", fullName= " + fullName  + ", type= " + type + '}';
        }
    }
    public enum OrganizationType {
        PUBLIC,
        TRUST,
        PRIVATE_LIMITED_COMPANY;
    }

    public void setManufactureCost(float manufactureCost){
        this.manufactureCost = manufactureCost;
    }
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
      }
    static UnitOfMeasure parseUnitOfMeasure(String input) {
                try {
                     return UnitOfMeasure.valueOf(input);
              } catch (IllegalArgumentException e) {
                   System.out.println("Неверная единица измерения. Попробуйте еще раз.");

           }
        return null;
    }
    static OrganizationType parseOrganizationType(String input){
        try{
            return OrganizationType.valueOf(input);
        }catch (IllegalArgumentException e){
            System.out.println("Неверный тип организации,попробуйте еще раз.");
        }
        return null;
    }
    private String validateName(String name){
        Objects.requireNonNull(name,"Значение этого поля не может быть null!");
        if(name.isEmpty()){
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
        return name;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setManufacturer(Organization manufacturer){
        this.manufacturer = manufacturer;
    }



    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ("ID " + id + " Name " + name + " Coordinates " + coordinates.toString() + " Creation date " + creationDate.format(formatter) +  " Price " + String.valueOf(price)) + " Manufacture cost "  + String.valueOf(manufactureCost) + " Единица измерения: " + unitOfMeasure.toString() + " " +  manufacturer.toString();
    }


}

