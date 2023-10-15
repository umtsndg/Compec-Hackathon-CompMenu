import java.util.ArrayList;

public class Restaurant {
    public ArrayList<Meal> menu = new ArrayList<>();
    public double x;
    public double y;

    public Restaurant(double x, double y) {

        for (int i = 0; i < (int)(10*Math.random()); i++) {
                menu.add(new Meal());
        }
        this.x = x;
        this.y = y;
    }

    public Restaurant(ArrayList<Meal> menu, double x, double y) {
        this.menu = menu;
        this.x = x;
        this.y = y;
    }

    public double distance(Restaurant restaurant){
        return Math.sqrt(Math.pow(this.x - restaurant.x,2) + Math.pow(this.y - restaurant.y,2));
    }
    public void addMeal(Meal meal){
        menu.add(meal);
    }
}
