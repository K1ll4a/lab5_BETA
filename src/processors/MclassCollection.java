package processors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import Mclass.*;
import utils.ProductFromXmlElementConverter;
import utils.ProductToXmlElementConverter;
import utils.XmlFileReader;
import utils.XmlFileWriter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Comparator;

public class MclassCollection  {
    ArrayList<Mclass> mclasses = new ArrayList<>();
    private ZonedDateTime initializationDate;
    private final XmlFileReader xmlFileReader = new XmlFileReader();
    private final XmlFileWriter xmlFileWriter = new XmlFileWriter();
    private final ProductFromXmlElementConverter productFromXmlElementConverter = new ProductFromXmlElementConverter();
    private final ProductToXmlElementConverter productToXmlElementConverter = new ProductToXmlElementConverter();

    public void info(){
        System.out.println("Тип коллекции: " + mclasses.getClass().getSimpleName());
        if(initializationDate != null){
            System.out.println("Дата инициализации: " + initializationDate.format(DateTimeFormatter.ISO_DATE_TIME));
        }else{
            System.out.println("Дата инициализации не указана");
        }
        System.out.println("Количество элементов: " + mclasses.size());
    }

    public void addMclass(Mclass mclass){
        mclasses.add(mclass);
    }

    public void updateMclass(long var1, Mclass var2) {
        for(long var3 = 0; var3 < this.mclasses.size(); ++var3) {
            if (((Mclass)this.mclasses.get((int) var3)).getId().equals(var1)) {
                this.mclasses.set((int) var3, var2);
                break;
            }
        }

    }

    public void loadFromFile(String fileName){
        try{
            Document document = xmlFileReader.readXmlFile(fileName);
            if (document != null){
                NodeList nodeList = document.getElementsByTagName("product");
                for (int i= 0;i < nodeList.getLength();i++){
                    try{
                        Element element = (Element) nodeList.item(i);
                        Mclass mclass = productFromXmlElementConverter.createMclassFromElement(element);
                        if(mclass != null){
                            mclasses.add(mclass);
                        }
                    }catch (Exception e) {
                        System.out.println();
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeLast() {
        if (!mclasses.isEmpty()) {
            mclasses.remove(mclasses.size() - 1);
        }
    }

    /**
     * @param mclass
     */
    public void removeGreater(Mclass mclass) {
        mclasses.removeIf(e -> e.compareTo(mclass) > 0);
        System.out.println("Все элементы, превышающие заданный, были удалены.");
    }

    public void addIfMin(Mclass newElement) {
        try {
            if (mclasses.isEmpty() || newElement.compareTo(mclasses.stream().min(Mclass::compareTo).get()) < 0) {
                mclasses.add(newElement);
                System.out.println("Элемент добавлен.");
            } else {
                System.out.println("Элемент не добавлен, так как не является минимальным.");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при добавлении элемента: " + e.getMessage());
        }
    }

    public void removeMclassById(int var1){
        mclasses.removeIf(mclass -> mclass.getId().equals(var1));
    }

    // Вывести объект с минимальной стоимостью производства
    public void minByManufactureCost() {
        if (mclasses != null && !mclasses.isEmpty()) {
            Mclass minCostMclass = mclasses.stream()
                    .min(Comparator.comparingDouble(Mclass::getManufactureCost))
                    .orElse(null);

            if (minCostMclass != null) {
                System.out.println("Объект с минимальной стоимостью производства: " + minCostMclass);
            } else {
                System.out.println("Коллекция пуста.");
            }
        } else {
            System.out.println("Коллекция не инициализирована или пуста.");
        }
    }

    // Вывести объект с максимальной стоимостью производства
    public void maxByManufactureCost() {
        if (mclasses != null && !mclasses.isEmpty()) {
            Mclass maxCostMclass = mclasses.stream()
                    .max(Comparator.comparingDouble(Mclass::getManufactureCost))
                    .orElse(null);

            if (maxCostMclass != null) {
                System.out.println("Объект с максимальной стоимостью производства: " + maxCostMclass);
            } else {
                System.out.println("Коллекция пуста.");
            }
        } else {
            System.out.println("Коллекция не инициализирована или пуста.");
        }
    }

    // Подсчитать количество элементов, стоимость производства которых меньше заданной
    public void countLessThanManufactureCost(double manufactureCost) {
        if (mclasses != null) {
            long count = mclasses.stream()
                    .filter(mclass -> mclass.getManufactureCost() < manufactureCost)
                    .count();

            System.out.println("Количество элементов, стоимость производства которых меньше " + manufactureCost + ": " + count);
        } else {
            System.out.println("Коллекция не инициализирована.");
        }
    }


    public void clearCollection(){
        mclasses.clear();
    }

    public void saveToFile(String fileName){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("mclasses");
            document.appendChild(rootElement);

            for (Mclass mclass : mclasses) {
                Element mclassElement = productToXmlElementConverter.createMclassElement(document, mclass);
                rootElement.appendChild(mclassElement);
        }

        xmlFileWriter.writeXmlFile(document,fileName);
    }catch (Exception e){
        e.printStackTrace();
        }
    }

    public ArrayList<Mclass> getMclasses() {
        return mclasses;
    }
}
