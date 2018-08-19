package com.cjw.concurrency;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codeAC
 * @Date: 2018/8/19
 * @Time: 18:10
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
