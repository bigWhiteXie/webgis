package com.ruoyi.web.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.common.core.controller.BaseController;

/**
 * dev-api 接口，用于将/dev-api重定向到Swagger UI
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/dev-api")
public class DevApiController extends BaseController
{
    @GetMapping()
    public String index()
    {
        return redirect("/swagger-ui/index.html");
    }
}