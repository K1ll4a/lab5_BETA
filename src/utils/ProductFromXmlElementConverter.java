package utils;

import Mclass.Mclass;
import org.w3c.dom.Element;
import Mclass.*;
import Mclass.Mclass.*;
import  Mclass.Mclass.Organization;

import java.time.LocalDateTime;


public class ProductFromXmlElementConverter {

    public Mclass createMclassFromElement(Element mclassElement) {
        try {
            Long id = Long.parseLong(mclassElement.getElementsByTagName("id").item(0).getTextContent());
            String name = mclassElement.getElementsByTagName("name").item(0).getTextContent();

            Element coordinatesElement = (Element) mclassElement.getElementsByTagName("coordinates").item(0);
            Integer x = Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent());
            Integer y = Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent());

            String creationDate = mclassElement.getElementsByTagName("creationDate").item(0).getTextContent();
            LocalDateTime localDateTime = LocalDateTime.parse(creationDate);

            Double price = Double.parseDouble(mclassElement.getElementsByTagName("price").item(0).getTextContent());



            Float manufactureCost = null;
            String manufactureCostValue = mclassElement.getElementsByTagName("manufactureCost").item(0).getTextContent();
            if (!manufactureCostValue.isEmpty()) {
                manufactureCost = Float.parseFloat(manufactureCostValue);
            }

            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(mclassElement.getElementsByTagName("unitOfMeasure").item(0).getTextContent());

            Element manufacturerElement = (Element) mclassElement.getElementsByTagName("manufacturer").item(0);

            Integer OrgId = null;
            String OrgName = null;
            String fullName = null;
            OrganizationType type = null;

            // Проверяем, что ownerElement не является null
            if (manufacturerElement != null) {
                // Извлекаем данные о владельце из элемента XML
                OrgId = Integer.valueOf(manufacturerElement.getElementsByTagName("OrgId").item(0).getTextContent());
                OrgName = manufacturerElement.getElementsByTagName("OrgName").item(0).getTextContent();
                fullName = manufacturerElement.getElementsByTagName("fullName").item(0).getTextContent();

                Element typeElement = (Element) manufacturerElement.getElementsByTagName("type").item(0);
                type = typeElement != null ? OrganizationType.valueOf(typeElement.getTextContent()) : null;


            }


// Создаем объект models.Mclass с полученными данными
            Mclass mclass = new Mclass(name, price, new Coordinates(x, y), manufactureCost, unitOfMeasure,
                    new Mclass.Organization(OrgId,OrgName,fullName,type));

            return mclass;
        } catch (Exception e) {
            System.out.println("Продукт не был добавлен в коллекцию по причине: " + e.getMessage());
            return null;
        }
    }
}