package cn.peyton.plum.mall.controller;

import cn.peyton.plum.core.anno.auth.Permission;
import cn.peyton.plum.core.json.JSONResult;
import cn.peyton.plum.core.utils.HttpServletRequestUtils;
import cn.peyton.plum.mall.controller.base.AppController;
import cn.peyton.plum.mall.pojo.Stu;
import cn.peyton.plum.mall.pojo.Sup;
import cn.peyton.plum.mall.service.StuService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
@RestController
public class StuController extends AppController {
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
    @GetMapping("/settoken")
    public String settoken() {
        Sup sup = new Sup();
        sup.setCreateTime(new Date());
        sup.setId(1);
        sup.setSex(0);
        sup.setName("judy");
        String token = saveToken(sup);
        System.out.println(token);
        sup.setToken(token);
        return "ok";
    }

    @GetMapping("/gettoken")
    public JSONResult gettoken(HttpServletRequest re) {
       verifyTokenAndVoluation(re,new Sup());
       Sup _sup = (Sup) baseUser;
        if (null != _sup) {
            System.out.println(_sup.getName());
        }
        return  JSONResult.success(_sup);
    }

    @GetMapping("/err")
    public String err(HttpServletRequest request) {
        Object name = request.getAttribute("name");

        return "login2";
    }
}
