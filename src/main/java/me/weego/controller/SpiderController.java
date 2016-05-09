package me.weego.controller;

import com.alibaba.fastjson.JSONObject;
import me.weego.pojo.ResBody;
import me.weego.service.BaseService;
import me.weego.service.SpiderService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author tcl
 */
@RestController
@RequestMapping("/spider/v1")
public class SpiderController extends BaseController {
    @Resource
    SpiderService spiderService;

    @Resource
    BaseService baseService;

    @RequestMapping(value = "key", method = RequestMethod.GET)
    public ResBody getKey(@RequestParam String type) {
        checkArgument(StringUtils.isNotBlank(type), "param should with type");
        return ResBody.returnSuccess(baseService.getKey(type));
    }

    @RequestMapping(value = "google/traffic", method = RequestMethod.GET)
    public ResBody<JSONObject> getGoogleTraffic(@RequestParam String mode,
                                                @RequestParam String origin,
                                                @RequestParam(value = "destination") String dest) {
        checkArgument(StringUtils.isNotBlank(mode), "param should with mode");
        checkArgument(StringUtils.isNotBlank(origin)
                && origin.contains(","), "error param origin");
        checkArgument(StringUtils.isNotBlank(dest)
                && dest.contains(","), "error param destination");
        return ResBody.returnSuccess(JSONObject.parseObject(spiderService.getGoogleTraffic(mode, origin, dest)));
    }

    @RequestMapping(value = "google/traffic1", method = RequestMethod.GET)
    public ResBody<JSONObject> getGoogleTraffic1(@RequestParam String origin,
                                    @RequestParam(value = "destination") String dest ){
            return ResBody.returnSuccess(JSONObject.parseObject(spiderService.getGoogleTraffic(origin,dest)));

    }

    @RequestMapping(value = "translate")
    public String translate(@RequestParam String text
                                     )  {
        try {
            text= StringEscapeUtils.unescapeHtml4(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        String result=spiderService.translate(text);
        JSONObject res= JSONObject.parseObject(result);
        res.put("source","百度");
        return res.toString();

    }
}
