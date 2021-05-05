import org.junit.jupiter.api.*;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest{
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    LocalTime closingTime;
    LocalTime openingTime;
    int initialNumberOfRestaurants;
    int initialNumberOfItems;

    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException{
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant = service.findRestaurantByName("Amelie's cafe");
        restaurant.displayDetails();
        assertNotNull(restaurant);
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException{
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        assertThrows(restaurantNotFoundException.class,
                ()->service.findRestaurantByName("cafe"));
    }

    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException{
        service.getAllSelectedItems().clear();
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException{
        service.getAllSelectedItems().clear();
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        service.getAllSelectedItems().clear();
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }

    @Test
    public void when_user_select_items_from_menu_then_order_cart_size_should_increase_by_1(){
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        initialNumberOfItems = service.getAllSelectedItems().size();
        service.addItemsToOrderCart("noddles",200);
        assertEquals(initialNumberOfItems+1,service.getAllSelectedItems().size());
    }

    @Test
    public void when_user_unselect_order_cart_then_order_cart_size_should_decrease_by_1() throws itemNotFoundException {
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        service.addItemsToOrderCart("noddles",200);
        initialNumberOfItems = service.getAllSelectedItems().size();
        service.removeItemsFromOrderCart("dal fry");
        assertEquals(initialNumberOfItems-1,service.getAllSelectedItems().size());
    }

    @Test
    public void get_all_selected_items_should_return_all_items_in_order_cart(){
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        service.addItemsToOrderCart("noddles",200);
        assertNotNull(service.getAllSelectedItems());
    }

    @Test
    public void when_user_selects_items_from_menu_then_total_cost_of_all_selected_items_should_be_calculated() throws itemNotFoundException {
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fsdjhbk",200);
        service.addItemsToOrderCart("dal fsdjhbk",200);
        System.out.println(service.getTotalCost(service.getAllSelectedItems()));
        assertEquals(400,service.getTotalCost(service.getAllSelectedItems()));
    }

    @Test
    public void when_user_unselects_items_from_order_cart_then_total_cost_of_all_selected_items_should_be_calculated() throws itemNotFoundException {
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        service.addItemsToOrderCart("noddles",200);
        assertEquals(200,service.removeItemsFromOrderCart("dal fry"));
    }

    @Test
    public void find_items_in_order_cart_by_name_should_return_item_object() throws itemNotFoundException {
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        assertNotNull(service.findItemsInOrderCartByName("dal fry"));
    }
    @Test
    public void find_items_in_order_cart_by_name_throw_exception_when_item_is_not_found() throws itemNotFoundException {
        service.getAllSelectedItems().clear();
        service.addItemsToOrderCart("dal fry",100);
        assertThrows(itemNotFoundException.class,
                ()->service.findItemsInOrderCartByName("dal tadka"));
    }
}
