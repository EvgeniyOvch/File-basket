import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Boolean load = true;
        String textFile = null;
        String fileForm = null;

        Boolean saveCond = true;
        String textFileToSave = null;
        String fileFormToSave = null;

        Boolean logCond = true;
        String fileNameLog = null;

        if (Files.exists(Path.of(String.valueOf("shop.xml")))) {
            System.out.println("Загружены настройки из shop.xml");
            try {
                File xmlFile = new File("shop.xml");
                DocumentBuilderFactory dfBuild = DocumentBuilderFactory.newInstance();
                DocumentBuilder builderDoc = dfBuild.newDocumentBuilder();
                Document document = builderDoc.parse(xmlFile);
                document.getDocumentElement().normalize();

                NodeList conditions = document.getElementsByTagName("load");
                for (int i = 0; i < conditions.getLength(); i++) {
                    Node node = conditions.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        load = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        textFile = element.getElementsByTagName("fileName").item(0).getTextContent();
                        fileForm = element.getElementsByTagName("format").item(0).getTextContent();
                    }
                }
                NodeList savCond = document.getElementsByTagName("save");
                for (int i = 0; i < savCond.getLength(); i++) {
                    Node node = savCond.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        saveCond = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        textFileToSave = element.getElementsByTagName("fileName").item(0).getTextContent();
                        fileFormToSave = element.getElementsByTagName("format").item(0).getTextContent();
                    }
                }
                NodeList loCond = document.getElementsByTagName("log");
                for (int i = 0; i < loCond.getLength(); i++) {
                    Node node = loCond.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        logCond = Boolean.valueOf(element.getElementsByTagName("enabled").item(0).getTextContent());
                        fileNameLog = element.getElementsByTagName("fileName").item(0).getTextContent();
                    }
                }
            } finally {
            }
        }

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        ClientLog csvLog = new ClientLog();
        Basket basket = new Basket(products, prices);
        if (load && Files.exists(Path.of(String.valueOf(fileForm.equals("json"))))) {
            System.out.println("Корзина восстановлена из .json");
            try {
                basket.LoadFromJson(new File(textFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (load) {
                System.out.println("Корзина восстановлена из .txt");
            }
        }

        basket.scroll();

        try {
            while (true) {
                System.out.println("Выберите товар и количество или введите 'end'");
                String input = scanner.nextLine();
                if (input.equals("end")) {
                    basket.printCart();
                    if (saveCond && fileFormToSave.equals("json")) {
                        basket.saveToJson(textFileToSave);
                    } else {
                        if (saveCond) {
                            basket.saveTxt(textFileToSave);
                        }
                    }
                    if (logCond) {
                        csvLog.exportAsCSV(fileNameLog);
                        break;
                    }

                } else {
                    String[] parst = input.split(" ");
                    int productNum = Integer.parseInt(parst[0]);
                    int amount = Integer.parseInt(parst[1]);
                    basket.addToCart(productNum, amount);
                    csvLog.log(productNum, amount);
                    basket.printCart();
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Введено не верное значение!");
        }
        System.out.println("Итого " + basket.sumProd() + " руб");
    }
}


