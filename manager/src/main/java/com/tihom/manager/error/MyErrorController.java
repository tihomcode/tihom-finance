package com.tihom.manager.error;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 自定义错误异常Controller
 * @author TiHom
 * create at 2018/8/2 0002.
 */
public class MyErrorController extends BasicErrorController {

    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String,Object> attrs = super.getErrorAttributes(request,includeStackTrace);
        /** 这里面只需要message和返回前端的判断码需要,其他删除就好
         * {
         *     "timestamp": "2018-08-02 13:02:30",
         *     "status": 500,
         *     "error": "Internal Server Error",
         *     "exception": "java.lang.IllegalArgumentException",
         *     "message": "编号不可为空",
         *     "path": "/manager/products"
         *     + code
         *     + canRetry
         * }
         */
        attrs.remove("timestamp");
        attrs.remove("error");
        attrs.remove("exception");
        attrs.remove("path");
        attrs.remove("status");
        //通过返回的message拿到错误对象
        String errorCode = (String) attrs.get("message");
        ErrorEnum errorEnum = ErrorEnum.getByCode(errorCode);
        //放入返回参数中
        attrs.put("message",errorEnum.getMessage());
        attrs.put("code",errorEnum.getCode());
        attrs.put("canRetry",errorEnum.isCanRetry());
        return attrs;
    }
}
