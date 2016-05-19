package me.weego.controller;

import com.alibaba.fastjson.JSONObject;
import me.weego.pojo.ResBody;
import me.weego.service.UberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ln
 */
@RestController
@RequestMapping("/uber/v1")
public class UberController {
    @Resource
    private UberService uberService;

    @RequestMapping(value = "estimate/price")
    public ResBody<JSONObject> getEstimatePrice(@RequestParam String start, @RequestParam String end) {
        return ResBody.returnSuccess(JSONObject.parseObject(uberService.getEstimatePrice(start, end)));
    }

    @RequestMapping(value = "/fixed_seat/estimate/price")
    public ResBody<JSONObject> getEstimatePrice(@RequestParam String start,
                                                @RequestParam String end,
                                                @RequestParam(value = "seatCount") int seat) {
        return ResBody.returnSuccess(JSONObject.parseObject(uberService.getEstimatePrice(start, end, seat)));
    }
}
