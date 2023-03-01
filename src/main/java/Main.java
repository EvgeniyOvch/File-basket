import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] product = {"Хлеб", "Яблоки", "Молоко"};
        int[] price = {100, 200, 300};

        Basket basket = new Basket(product, price);
        ClientLog csvLog = new ClientLog();
        File jsonFile = new File("basket.json");

        if (jsonFile.exists()) {
            basket.LoadFromJson(jsonFile);
            System.out.println("Корзина восстановлена!");
        } else {
            System.out.println("Сохранённой корзины нет!");
        }

        basket.scroll();
        try {
            while (true) {
                System.out.println("Выберите товар и количество или введите 'end'");
                String input = scanner.nextLine();
                if ("end".equals(input)) {
                    basket.saveToJson(new File(String.valueOf(jsonFile)));
                    break;
                }
                String[] parst = input.split(" ");
                int productNum = Integer.parseInt(parst[0]) - 1;
                int amount = Integer.parseInt(parst[1]);
                basket.addToCart(productNum, amount);
                basket.saveToJson(jsonFile);
                csvLog.log(productNum + 1, amount);
                basket.printCart();
            }
        } catch (RuntimeException exception) {
            System.out.println("Введено не верное значение!");
        }
        System.out.println("Итого " + basket.sumProd() + " руб");
        File textFile = new File("list.txt");
        try {
            if (textFile.createNewFile())
                System.out.println("Файл TXT был создан");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        basket.saveTxt(textFile);
        Basket.loadFromTxtFile(textFile);

        File txtFile = new File("txtFile.csv");
        try {
            if (txtFile.createNewFile())
                System.out.println("Файл CSV был создан");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        csvLog.exportAsCSV(txtFile);


    }
}



