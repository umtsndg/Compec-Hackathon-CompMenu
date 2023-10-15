import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {

        int targetRadius = 4;// Proximity radius

        int n = 20;// number of restaurants excluding the target

        ArrayList<Meal> targetMenu = new ArrayList<>();//The menu of the restaurant we are providing our services
        targetMenu.add(new Meal("salata", 4, 200, 40));
        targetMenu.add(new Meal("kofte", 3, 150, 40));
        targetMenu.add(new Meal("iskender", 5, 180, 40));
        targetMenu.add(new Meal("borek", 6, 80, 60));
        targetMenu.add(new Meal("pogaca", 6, 50, 80));
        targetMenu.add(new Meal("mitit kofte", 3, 140, 50));


        Restaurant targetRestaurant = new Restaurant(targetMenu, 5, 5);//The restaurant that we are providing our services


        HashMap<Integer, Integer> targetCategories = new HashMap<>();// Categories the target menu contains and number of meal for each

        for (Meal meal : targetMenu) {
            if (!targetCategories.containsKey(meal.category)) {
                targetCategories.put(meal.category, 1);
            } else {
                targetCategories.put(meal.category, targetCategories.get(meal.category) + 1);
            }
        }

        HashMap<Integer, Integer> targetCategoriesRevenue = new HashMap<>();

        for (Meal meal : targetMenu) {
            if (!targetCategoriesRevenue.containsKey(meal.category)) {
                targetCategoriesRevenue.put(meal.category, meal.revenue);
            } else {
                targetCategoriesRevenue.put(meal.category, targetCategoriesRevenue.get(meal.category) + meal.revenue);
            }
        }

        ArrayList<Restaurant> restaurants = new ArrayList<>();// Other restaurants

        restaurants.add(targetRestaurant);

        for (int i = 0; i < n; i++) {
            restaurants.add(new Restaurant(10 * Math.random(), 10 * Math.random()));

        }

        ArrayList<Restaurant> closeRestaurants = new ArrayList<>();// Restaurants that are close to the target

        for (Restaurant restaurant : restaurants) {
            if (targetRestaurant.distance(restaurant) <= targetRadius) {
                closeRestaurants.add(restaurant);
            }
        }

        //System.out.println(closeRestaurants.size());

        HashMap<Integer, Integer> allCategories = new HashMap<>();// Categories of all close restaurants and the number of meals of each

        for (Restaurant closeRes : closeRestaurants) {
            for (Meal meal : closeRes.menu) {
                if (!allCategories.containsKey(meal.category)) {
                    allCategories.put(meal.category, 1);
                } else {
                    allCategories.put(meal.category, allCategories.get(meal.category) + 1);
                }
            }
        }

        //System.out.println(allCategories);

        Collections.sort(targetMenu);

        double sum = 0;
        for (Meal meal : targetMenu) {
            //System.out.println(meal.name);
            sum += meal.revenue;

        }
        double average = sum / targetMenu.size();

        for (Meal meal : targetRestaurant.menu) {
            if (meal.revenue >= average) { //Case 1: restaurant is successful in this meal
                if (allCategories.get(meal.category) > 3) {
                    meal.status = "AR";
                } else if (allCategories.get(meal.category) > 1 && allCategories.get(meal.category) < 3) {
                    meal.status = "ASS";
                } else {
                    meal.status = "MWW1";
                }
            } else { // Case 2: Restaurant is not successful in this meal
                    if (allCategories.get(meal.category) > 3) {
                        meal.status = "DC";
                    } else if (allCategories.get(meal.category) > 1 && allCategories.get(meal.category) < 3) {
                        meal.status = "MC";
                    } else {
                        meal.status = "MWW2";
                }
            }

        }


        for (Meal meal : targetRestaurant.menu){
            if(meal.status.equals("AR")){
                System.out.println(String.format("You are doing well in selling %s 's. But there are other restaurants that might want to compete with you" +
                        "Our suggestion is that you might want to allocate more resources to maintain this state",meal.name));
            }
            else if (meal.status.equals("ASS")){
                System.out.println(String.format("You are doing well in selling %s 's. And you don't have much competitors" +
                        " worth mentioning. Keep up the good work without wasting resources. ",meal.name));
            }
            else if (meal.status.equals("MWW1")){
                System.out.println(String.format("You are doing well in selling %s 's. And you don't have any competitors" +
                        ". Keep up the good work without wasting resources. ",meal.name));
            }
            else if (meal.status.equals("DC")){
                System.out.println(String.format("You are not doing well in selling %s 's. And you have many competitors in this market. " +
                        "You may consider end the production of %s's",meal.name,meal.name));
            }
            else if (meal.status.equals("MC")){
                System.out.println(String.format("You are not doing well in selling %s 's. But you don't have many competitors." +
                        "If you think you can increase your profits you might want to allocate resources and compete",meal.name));
            }
            else if(meal.status.equals("MWW2")) {
                System.out.println(String.format("You are not doing well in selling %s 's. But you are the sole producer." +
                        "You can maintain your current quality without wasting resources",meal.name));
            }
        }

        //Std Draw
        int canvasWidth = 1200;
        int canvasHeight = 600;
        int xScale = 10;
        int yScale = 10;
        StdDraw.setCanvasSize(canvasWidth,canvasHeight);
        StdDraw.setXscale(0,xScale);
        StdDraw.setYscale(0,yScale);

        animation(xScale,yScale,0,targetMenu,restaurants,targetCategories,allCategories, targetRestaurant, targetRadius,closeRestaurants);
    }

    public static void animation( int xScale, int yScale, int status, ArrayList<Meal> targetMenu ,ArrayList<Restaurant> restaurants,HashMap<Integer,Integer> targetCategories,HashMap<Integer,Integer> allCategories, Restaurant targetRestaurant, int r,ArrayList<Restaurant> closeRestaurants){
        StdDraw.clear();


        if(status==0) {
            setUserInterface(xScale, yScale);
            while (true){
                double currentMouseX = StdDraw.mouseX();
                double currentMouseY = StdDraw.mouseY();
                if (StdDraw.isMousePressed()) {
                    if ((currentMouseX > xScale / 6.0 && currentMouseX < xScale * 5 / 6.0) && (currentMouseY > yScale * 0.8 && currentMouseY < xScale * 0.9)) {
                        animation(xScale,yScale,1,targetMenu,restaurants,targetCategories,allCategories, targetRestaurant, r,closeRestaurants);
                    } else if ((currentMouseX > xScale / 6.0 && currentMouseX < xScale * 5 / 6.0) && (currentMouseY > yScale * 0.6 && currentMouseY < xScale * 0.7)) {
                        animation(xScale,yScale,2,targetMenu,restaurants,targetCategories,allCategories, targetRestaurant, r,closeRestaurants);
                    } else if ((currentMouseX > xScale / 6.0 && currentMouseX < xScale * 5 / 6.0) && (currentMouseY > yScale * 0.4 && currentMouseY < xScale * 0.5)) {
                        animation(xScale,yScale,3,targetMenu,restaurants,targetCategories,allCategories, targetRestaurant, r,closeRestaurants);
                    }
                }
                StdDraw.pause(15);
            }
        }else if (status == 1) {
            onClickMenu(targetMenu, xScale, yScale);

        } else if (status == 2) {
            onClickCategory(targetMenu,xScale,yScale);
        } else if (status == 3) {
            int targetCategory;
            StdDraw.setFont(new Font("Helvetica",Font.BOLD,24));
            StdDraw.text(xScale/2.0,yScale/2.0,"Enter the Category_id:(between 1 and 9)");
            while(true){
                if(StdDraw.isKeyPressed(KeyEvent.VK_1)){
                    targetCategory = 1;
                    break;
                } else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
                    targetCategory = 2;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
                    targetCategory = 3;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
                    targetCategory = 4;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
                    targetCategory = 5;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_6)) {
                    targetCategory = 6;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_7)) {
                    targetCategory = 7 ;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_8)) {
                    targetCategory = 8 ;
                    break;
                }
                else if (StdDraw.isKeyPressed(KeyEvent.VK_9)) {
                    targetCategory = 9 ;
                    break;
                }
            }
            StdDraw.clear();
            onClickMap(restaurants,targetMenu,targetCategories,allCategories,xScale,yScale,targetRestaurant,r,closeRestaurants,targetCategory);
        }


        while(true){
            if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)){
                animation(xScale,yScale,0,targetMenu,restaurants,targetCategories,allCategories,targetRestaurant, r,closeRestaurants);
            }
            StdDraw.pause(15);
        }


    }

    public static void setUserInterface(int xScale,int yScale){
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle((double)xScale/2,0.85*yScale,xScale/3.0,yScale/20.0);
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text((double)xScale/2,0.85*yScale,"Menu");

        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle((double)xScale/2,0.65*yScale,xScale/3.0,yScale/20.0);
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text((double)xScale/2,0.65*yScale,"Categories");

        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle((double)xScale/2,0.45*yScale,xScale/3.0,yScale/20.0);
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text((double)xScale/2,0.45*yScale,"Map");

    }

    public static void onClickMenu(ArrayList<Meal> targetMenu, int xScale, int yScale){
        Collections.sort(targetMenu);
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle((double)xScale/4,0.9*yScale,xScale/5.0,yScale/20.0);
        StdDraw.filledRectangle((double)3*xScale/4,0.9*yScale,xScale/5.0,yScale/20.0);
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,18));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text((double)xScale/4,0.9*yScale,"The Best Sellers");
        StdDraw.text((double)3*xScale/4,0.9*yScale,"The Worst Sellers");


        double yFactor = 0.70;
        for (int i = 0; i< 3; i++){
            Meal meal = targetMenu.get(i);
            Meal badMeal = targetMenu.get(targetMenu.size()-i-1);
            if(yFactor <0)
                break;
            StdDraw.setPenColor(StdDraw.PINK);
            StdDraw.filledRectangle((double)xScale/4,yFactor*yScale,xScale/5.0,yScale/20.0);
            StdDraw.filledRectangle((double)3*xScale/4,yFactor*yScale,xScale/5.0,yScale/20.0);
            StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text((double)xScale/4,yFactor*yScale,String.format("%d) %s made %d$",i+1,meal.name.toUpperCase(),meal.revenue));
            StdDraw.text((double)3*xScale/4,yFactor*yScale,String.format("%d) %s made %d$",i+1,badMeal.name.toUpperCase(),badMeal.revenue));
            yFactor-=0.2;
        }

    }

    public static void onClickCategory(ArrayList<Meal> targetMenu, int xScale, int yScale){

        HashMap<Integer, Integer> targetCategoriesRevenue = new HashMap<>();

        for (Meal meal : targetMenu) {
            if (!targetCategoriesRevenue.containsKey(meal.category)) {
                targetCategoriesRevenue.put(meal.category, meal.revenue);
            } else {
                targetCategoriesRevenue.put(meal.category, targetCategoriesRevenue.get(meal.category) + meal.revenue);
            }
        }

        int n = targetCategoriesRevenue.size();

        int max = 0;
        for (int value: targetCategoriesRevenue.values()) {
            if(value > max)
                max = value;
        }

        double currentX = 0 ;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0,yScale/10.0,xScale,yScale/10.0);
        int tmp = 0;
        for (int key: targetCategoriesRevenue.keySet() ) {
            if (tmp == 0){
                StdDraw.setFont(new Font("Helvetica",Font.BOLD,12));
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.textLeft(currentX,yScale/20.0,"Category_id:");
                StdDraw.textLeft(currentX,19*yScale/20.0,"Revenue:");
                tmp ++;
                currentX += xScale/(n*5.0)+(double)xScale/(n+1);
            }

            StdDraw.setPenColor((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
            StdDraw.filledRectangle(currentX,8*yScale/10.0*targetCategoriesRevenue.get(key)/max*(1/2.0)+yScale/10.0,xScale/(n*5.0),8*yScale/10.0*targetCategoriesRevenue.get(key)/max*(1/2.0));
            StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text((double)currentX,yScale/20.0,String.format("%d",key));
            StdDraw.text((double)currentX,19*yScale/20.0,String.format("%d",targetCategoriesRevenue.get(key)));
            currentX += 9*(double)xScale/(n+1)/10;
        }

    }


    public static void onClickMap(ArrayList<Restaurant> restaurants,ArrayList<Meal> targetMenu,HashMap<Integer,Integer> targetCategories,HashMap<Integer,Integer> allCategories,int xScale, int yScale,Restaurant targetRestaurant, int r, ArrayList<Restaurant> closeRestaurants, int targetCategory) {
        if(!targetCategories.containsKey(targetCategory)) {
            StdDraw.clear();
            StdDraw.text((double) xScale / 2, (double) yScale / 2, "Your menu doesn't include that category. Please enter a new one.");

            while(true){
            if(StdDraw.isKeyPressed(KeyEvent.VK_1)){
                targetCategory = 1;
                break;
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
                targetCategory = 2;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
                targetCategory = 3;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
                targetCategory = 4;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
                targetCategory = 5;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_6)) {
                targetCategory = 6;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_7)) {
                targetCategory = 7 ;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_8)) {
                targetCategory = 8 ;
                break;
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_9)) {
                targetCategory = 9 ;
                break;
            }
        }

            onClickMap(restaurants, targetMenu, targetCategories, allCategories, xScale, yScale, targetRestaurant, r, closeRestaurants, targetCategory);
        }


        else{
        StdDraw.picture(xScale / 2.0, yScale / 2.0, "WhatsApp Image 2023-10-15 at 10.52.53.jpeg", xScale, yScale);

        StdDraw.setPenColor(StdDraw.RED);
        for (Restaurant restaurant : restaurants) {

            if(closeRestaurants.contains(restaurant)){
                StdDraw.setPenColor(StdDraw.GREEN);
                for(Meal meal:restaurant.menu){
                    if(meal.category == targetCategory){
                        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                    }
                }
            }
            StdDraw.filledCircle(restaurant.x, restaurant.y, 0.1);
            StdDraw.setPenColor(StdDraw.RED);
        }

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(targetRestaurant.x, targetRestaurant.y, 0.1);
        StdDraw.circle(xScale / 2.0, yScale / 2.0, r);
    }
    }
}