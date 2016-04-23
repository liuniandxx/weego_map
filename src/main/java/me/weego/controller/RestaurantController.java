package me.weego.controller;

import me.weego.model.RestaurantModel;
import me.weego.pojo.ResBody;
import me.weego.service.RestaurantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tcl
 */
@RestController()
@RequestMapping("/restaurant")
public class RestaurantController extends BaseController {
    @Resource
    private RestaurantService restaurantService;

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public ResBody getRestaurants() {
        return ResBody.returnSuccess(restaurantService.query());
    }
}
