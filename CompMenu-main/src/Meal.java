import java.util.Random;
import java.util.random.*;
public class Meal implements Comparable<Meal>{
    public String name;
    public int category;
    public int price;

    public int sales;

    public int revenue;
    public String status;

    public Meal( int category) {
        this.category = category;
        ;
    }

    public Meal(String name, int category, int price, int sales) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.sales = sales;
        revenue = price * sales;
    }

    @Override
    public int compareTo(Meal o) {
        return o.revenue - revenue;
    }

    public Meal(){
        mealRandomizer();
    }

    private void mealRandomizer(){
        Random rand = new Random();
        category = rand.nextInt(1,9);

    }
}
