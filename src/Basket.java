import java.io.*;

public class Basket implements Serializable {

    private String[] products;
    private int[] prices;

    private int[] basketCount;
    private static final long serialVersionUID = 1L;


    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.basketCount = new int[products.length];
    }

    public void scroll() {
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + products[i] + " цена: " + prices[i]);
        }
    }

    public void addToCart(int productNum, int amount) {
        basketCount[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < basketCount.length; i++) {
            System.out.println(products[i] + " " + basketCount[i] + " шт " + prices[i] + " руб/шт " + (basketCount[i] * prices[i]) + " руб в сумме");
        }
    }

    public int sumProd() {
        int sum = 0;
        for (int i = 0; i < products.length; i++) {
            sum += basketCount[i] * prices[i];
        }
        return sum;
    }


    public void saveBin(File file) {
        try {
            FileOutputStream outputStrem = new FileOutputStream("basket.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStrem);
            objectOutputStream.writeObject(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Basket basket = (Basket) objectInputStream.readObject();
            return basket;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}





