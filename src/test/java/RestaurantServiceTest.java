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
}
