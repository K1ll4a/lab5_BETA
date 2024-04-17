package utils;

import Mclass.Mclass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProductToXmlElementConverter {
    public Element createMclassElement(Document document,Mclass mclass){
        Element mclassElement = document.createElement("mclass");

        Element idElement = document.createElement("id");
        idElement.appendChild(document.createTextNode(mclass.getId().toString()));
        mclassElement.appendChild(idElement);

        Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(mclass.getName()));
        mclassElement.appendChild(nameElement);

        Element coordinatesElement = document.createElement("coordinates");
        Element xElement = document.createElement("x");
        xElement.appendChild(document.createTextNode(String.valueOf(mclass.getCoordinates().getX())));
        coordinatesElement.appendChild(xElement);
        Element yElement = document.createElement("y");
        yElement.appendChild(document.createTextNode(String.valueOf(mclass.getCoordinates().getY())));
        coordinatesElement.appendChild(yElement);
        mclassElement.appendChild(coordinatesElement);

        Element creationDateElement = document.createElement("creationDate");
        creationDateElement.appendChild(document.createTextNode(mclass.getCreationDate().toString()));
        mclassElement.appendChild(creationDateElement);

        Element priceElement = document.createElement("price");
        priceElement.appendChild(document.createTextNode(mclass.getPrice().toString()));
        mclassElement.appendChild(priceElement);

        Element manufactureCostElement = document.createElement("manufactureCost");
        if (!Float.isNaN(mclass.getManufactureCost())){
            manufactureCostElement.appendChild(document.createTextNode(String.valueOf(mclass.getManufactureCost())));
        }
        mclassElement.appendChild(manufactureCostElement);

        Element unitOfMeasureElement = document.createElement("unitOfMeasure");
        unitOfMeasureElement.appendChild(document.createTextNode(mclass.getUnitOfMeasure().name()));

        Element manufacturerElement = document.createElement("manufacturer");

        Element OrgNameElement = document.createElement("OrgName");
        OrgNameElement.appendChild(document.createTextNode(mclass.getManufacturer().getOrgName()));
        OrgNameElement.appendChild(OrgNameElement);

        Element OrgfullNameElement = document.createElement("fullName");
        OrgfullNameElement.appendChild(document.createTextNode(mclass.getManufacturer().getFullName()));
        OrgfullNameElement.appendChild(OrgfullNameElement);

        Element OrgIdElement = document.createElement("OrgId");
        OrgIdElement.appendChild(document.createTextNode(String.valueOf(mclass.getManufacturer().getOrgId())));
        OrgIdElement.appendChild(OrgIdElement);

        if (mclass.getManufacturer().getType() != null){
            Element typeElement = document.createElement("type");
            typeElement.appendChild(document.createTextNode(mclass.getManufacturer().getType().name()));
            mclassElement.appendChild(typeElement);
        }
        mclassElement.appendChild(manufacturerElement);
        return mclassElement;

    }
}
