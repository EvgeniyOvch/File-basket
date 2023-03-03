import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] basketCount;


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
        basketCount[productNum - 1] += amount;
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

    public void saveTxt(String textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products)
                out.print(product + " ");
            out.println();
            for (int price : prices)
                out.print(price + " ");
            out.println();
            for (int count : basketCount)
                out.print(count + " ");
        }
    }

    static Basket loadFromTxtFile(File textFile) {
        try (FileReader reader = new FileReader(textFile)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.ready()) {
                String[] product = bufferedReader.readLine().split(" ");
                String[] prices1 = bufferedReader.readLine().split(" ");
                String[] basketCount1 = bufferedReader.readLine().split(" ");
                int[] price = new int[prices1.length];
                for (int i = 0; i < prices1.length; i++) {
                    price[i] = Integer.parseInt(prices1[i]);
                }
                int[] basketCount = new int[basketCount1.length];
                for (int i = 0; i < basketCount1.length; i++) {
                    basketCount[i] = Integer.parseInt(basketCount1[i]);
                }

                Basket basket = new Basket(product, basketCount);
                basket.basketCount = basketCount;
                return basket;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void saveToJson(String textfile) {
        Gson gson = new Gson();
        Basket basket = new Basket(products, prices);
        try (Writer wr = new FileWriter(textfile)) {
            basket.basketCount = this.basketCount;
            gson.toJson(basket, wr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadFromJson(File textfile) throws IOException {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        try (Reader reader = new FileReader(textfile)) {
            Basket basketFromJson = gson.fromJson(reader, Basket.class);
            basketFromJson.printCart();

        }
    }
}




