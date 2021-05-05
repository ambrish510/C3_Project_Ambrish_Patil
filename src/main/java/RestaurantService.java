import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class  RestaurantService{
    private static List<Restaurant> restaurants = new ArrayList<>();
    List<Item> selectedItems = new ArrayList<>();
    Restaurant restaurant;
    int totalCost=0;

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException{

        for(Restaurant restaurant : restaurants){
            if(restaurant.getName().equals(restaurantName))
                return restaurant;
        }
        throw new restaurantNotFoundException(restaurantName);
    }

    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime){
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException{
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants(){
        return restaurants;
    }

    // To search for the items added or selected by the user
    public Item findItemsInOrderCartByName(String itemName) throws itemNotFoundException{
        for(Item itemToBeSearched : selectedItems){
            if(itemToBeSearched.getName().equals(itemName))
                return itemToBeSearched;
        }
        throw new itemNotFoundException(itemName);
    }

    //to add or select the items from menu by the user
    public int addItemsToOrderCart(String itemName, int itemPrice) throws itemNotFoundException {
        totalCost=0;
        Item itemToBeAdded = new Item(itemName, itemPrice);
        selectedItems.add(itemToBeAdded);
        return getTotalCost(selectedItems);
    }

    //to remove or unselected items by the user from menu
    public int removeItemsFromOrderCart(String itemName) throws itemNotFoundException{
        totalCost=0;
        Item itemToBeRemoved = findItemsInOrderCartByName(itemName);
        selectedItems.remove(itemToBeRemoved);
        return getTotalCost(selectedItems);
    }

    //to calculate the total of all the items selected or added by the user
    public int getTotalCost(List<Item> selectedItems) throws itemNotFoundException {
        totalCost=0;
        for(Item Item : selectedItems){
            if(restaurant.IsSelectedItemExistsInMenu(Item.getName())==true){
                totalCost = totalCost + Item.getPrice();
            }
            else{
                throw new itemNotFoundException(Item.getName());
            }
        }
        return totalCost;
    }

    //to get all the selected items from order cart
    public List<Item> getSelectedItems(){
        return selectedItems;
    }
}
