package cn.peyton.plum.mall.controller;

import cn.peyton.plum.core.err.ValidationException;
import cn.peyton.plum.core.json.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.mall.controller.DemoController
 * @date 2023年10月14日 9:40
 * @version 1.0.0
 * </pre>
 */
@Controller
public class DemoController {

    @GetMapping("/test1")
    public String test1(Integer id) {
        if (id == 1) {
            ;
            throw new ValidationException(JSONResult.fail("DemoController类"));
        }

        return "welcome mall!";
    }
    @GetMapping("/test2")
    public String test2() {
        return "login2";
    }
}
