package vip.imagin.blast.handler;/**
 * @author lingqu
 * @date 2022/3/6
 * @apiNote
 */

import com.alibaba.fastjson.JSON;
import vip.imagin.blast.utils.Result;
import vip.imagin.blast.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzhi
 * @version 1.0
 * @description TODO
 * @createDate 2022/3/6
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = new Result(HttpStatus.FORBIDDEN.value(), "权限不足");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}
