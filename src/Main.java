import processors.CommandProcessor;
import processors.MclassCollection;
import utils.FilePathReader;


import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String fileName = FilePathReader.getXmlFilePath();

        MclassCollection mclassCollection = new MclassCollection();
        if (fileName != null) {
            mclassCollection.loadFromFile(fileName);
        }

        Scanner scanner = new Scanner(System.in);
        CommandProcessor commandProcessor = new CommandProcessor(mclassCollection, scanner, fileName);
        commandProcessor.run();
    }
}



