package com.ymall.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Steafan on 2018/4/27 0027.
 */
@Controller
@Slf4j
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }
}
