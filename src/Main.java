import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Basket basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});

        basket.scroll();

        try {
            while (true) {
                System.out.println("Выберите товар и количество или введите 'end'");
                String input = scanner.nextLine();
                if ("end".equals(input)) {
                    break;
                }
                String[] parst = input.split(" ");
                basket.addToCart(Integer.parseInt(parst[0]) - 1, Integer.parseInt(parst[1]));
                basket.printCart();
            }

        } catch (RuntimeException exception) {
            System.out.println("Введено не верное значение!");
        }
        System.out.println("Итого " + basket.sumProd() + " руб") ;

        basket.saveBin(new File("basket.bin"));
        Basket.loadFromBinFile(new File("basket.bin"));

    }
}
