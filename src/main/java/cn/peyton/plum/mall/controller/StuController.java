package cn.peyton.plum.mall.controller;

import cn.peyton.plum.core.anno.auth.Permission;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.mall.pojo.Stu;
import cn.peyton.plum.mall.service.StuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h4></h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @name cn.peyton.plum.mall.controller.StuController
 * @date 2023年10月14日 12:15
 * @version 1.0.0
 * </pre>
 */
@Controller
public class StuController {
    @Value("${login.username}")
    public String UN;

    @Resource
    private StuService stuService;


    @GetMapping("/add")
    public JSONResult<Stu> add(Stu stu) {
        return JSONResult.success(stuService.add(stu));
    }
    @GetMapping("/finds")
    public JSONResult finds() {
        return JSONResult.success(stuService.finds());
    }

    @Permission(redirect = "/err",key="stu")
    @GetMapping("/checked")
    public String ckecked(HttpServletRequest request) {
        String cp=request.getContextPath();
        return "index";
    }
    @GetMapping("/home")
    public String home() {
        Stu stu = new Stu();
        stu.setId(1);
        stu.setName("Judy");
        stu.setToken("88uudfjsdfsdhj");
        String temp = UN;
        HttpServletRequestUtils.getRequest().getSession().setAttribute("stu", stu);
        return "redirect:/checked";
    }

    @GetMapping("/err")
    public String err(HttpServletRequest request) {
        Object name = request.getAttribute("name");

        return "login2";
    }
}
