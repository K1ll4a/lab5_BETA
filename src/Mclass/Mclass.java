package Mclass;



import java.util.Objects;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Mclass implements Comparable<Mclass> {
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




    public Mclass(String name, Double price, Coordinates coordinates, UnitOfMeasure unitOfMeasure, Float manufactureCost, Organization manufacturer) {
        this.id = random.nextLong(1, 1001);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(0, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        creationDate = LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
        creationDate = creationDate.atZone(ZoneOffset.UTC).toLocalDateTime();


    }
    public Mclass(String name,Double price,Coordinates coordinates,Float manufactureCost,UnitOfMeasure unitOfMeasure,Organization manufacturer){
        this.id = random.nextLong(1, 1001);
        this.coordinates = coordinates;
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(0, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        creationDate = LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
        creationDate = creationDate.atZone(ZoneOffset.UTC).toLocalDateTime();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;

    }





    public static class Coordinates implements Serializable{
        private float x;
        private float y;
        public Coordinates(float x,float y){
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
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
        public static UnitOfMeasure parseUnitOfMeasure(String input) {
            try {
                return UnitOfMeasure.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная единица измерения. Попробуйте еще раз.");

            }
            return null;
        }

    }

    public static class Organization {
        private static final Random idGenerator = new Random();
        private Integer OrgId;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private String OrgName; //Поле не может быть null, строка не может быть пустой
        private  String fullName;//Длина строки не должна быть больше 1307, Значение этого поля должно быть уникальным, Поле не может быть null
        private OrganizationType type; //Поле может быть null
        public Organization(){



        }
        public Organization(String OrgName, String fullName,OrganizationType type){
            this.OrgId = random.nextInt(1000);
            this.OrgName = validateName(OrgName);
            this.fullName = validatefFullName(fullName);
            this.type = type;
        }
        public Organization(Integer OrgId,String OrgName, String fullName,OrganizationType type){
            this.OrgId = random.nextInt(1000);
            this.OrgName = validateName(OrgName);
            this.fullName = validatefFullName(fullName);
            this.type = type;
        }
        private Integer generateId(){
            Random random = new Random();
            int OrgId;
            do{
                OrgId = random.nextInt(1000);
            }while (OrgId == 0);
            return OrgId;
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

        public void setName(String OrgName) {
            if(OrgName == null || OrgName.isEmpty()){
                throw new IllegalArgumentException("Значение поля не может быть null!");
            }
            this.OrgName = OrgName;
        }

        public String getOrgName() {
            return OrgName;
        }

        public String getFullName() {
            return fullName;
        }

        public int getOrgId() {
            return OrgId;
        }

        public OrganizationType getType() {
            return type;
        }

        public void setFullName(String fullName) {
            if(fullName == null || fullName.isEmpty() || fullName.length() > 1307){
                throw new IllegalArgumentException("Поле не может быть null,длина строки не может быть больше 1307");
            }
            this.fullName = fullName;
        }
        public void setOrganizationType(OrganizationType type){
            this.type = type;
        }

        
        @Override
        public String toString() {
            return "Organization{" + "id=" + OrgId + ", name= " + OrgName  + ", fullName= " + fullName  + ", type= " + type + '}';
        }
    }
    public enum OrganizationType {
        PUBLIC,
        TRUST,
        PRIVATE_LIMITED_COMPANY;

        public static OrganizationType parseOrganizationType(String input){
            try{
                return OrganizationType.valueOf(input);
            }catch (IllegalArgumentException e){
                System.out.println("Неверный тип организации,попробуйте еще раз.");
            }
            return null;
        }
    }

    public void setManufactureCost(float manufactureCost){
        this.manufactureCost = manufactureCost;
    }
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public float getManufactureCost() {
        return manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Organization getManufacturer() {
        return manufacturer;
    }

    public static Mclass parse(String str) {
        String[] parts = str.split(","); // Assuming the string is in the format "name,price,x,y,manufactureCost,unitOfMeasure,orgName,fullName,type"

        String name = parts[0];
        Double price = Double.parseDouble(parts[1]);
        float x = Float.parseFloat(parts[2]);
        float y = Float.parseFloat(parts[3]);
        float manufactureCost = Float.parseFloat(parts[4]);
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(parts[5]);
        String orgName = parts[6];
        String fullName = parts[7];
        OrganizationType type = OrganizationType.valueOf(parts[8]);

        Organization manufacturer = new Organization(orgName, fullName, type);
        Coordinates coordinates = new Coordinates(x, y);

        return new Mclass(name, price, coordinates, manufactureCost, unitOfMeasure, manufacturer);
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ("ID " + id + "," + " Name " + name + "," + " Coordinates " + coordinates.toString() + "," + " Creation date " + creationDate.format(formatter) + "," + " Price " + String.valueOf(price)) + "," + " Manufacture cost "  + String.valueOf(manufactureCost) + "," + " Единица измерения: " + unitOfMeasure.toString() + "," +  manufacturer.toString();
    }


    public int compareTo(Mclass var1) {
        return this.id.compareTo(var1.id);
    }


}

