package com.louis.mango.admin.login;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.louis.mango.admin.model.user.SysUser;
import com.louis.mango.admin.security.utils.PasswordUtils;
import com.louis.mango.admin.security.utils.SecurityUtils;
import com.louis.mango.admin.security.utils.model.JwtAuthenticatioToken;
import com.louis.mango.admin.service.user.impl.SysUserServiceImpl;
import com.louis.mango.common.utils.http.HttpResult;
import com.louis.mango.common.utils.http.HttpResultUtils;
import com.louis.mango.common.utils.http.HttpStatus;
import com.louis.mango.core.exception.BaseException;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关API
 */
@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证码
     * @param response
     * @param request
     */
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存验证码到session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录接口
     * @return
     */
    @PostMapping("/login")
    public HttpResult login(LoginBean loginBean, HttpServletRequest request) {
        String userName = loginBean.getAccount();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();
        // 从session中获取之前保存的验证码，跟前台传来的验证码进行匹配
        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (kaptcha == null) {
            throw new BaseException(HttpStatus.ERROR_PARAMS_VALIDATOR, "验证码已失效");
        }

        if (!kaptcha.equals(captcha)) {
            throw new BaseException(HttpStatus.ERROR_PARAMS_VALIDATOR, "验证码不正确");
        }

        // 用户信息
        SysUser sysUser = sysUserServiceImpl.selectByUserName(userName);

        // 账号或密码错误
        if (sysUser == null || (!PasswordUtils.matches(sysUser.getSalt(), password, sysUser.getPassword()))) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "账号或密码错误");
        }

        // 账号锁定
        if (sysUser.getStatus() == 0) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "账号被锁定");
        }

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, userName, password, authenticationManager);
        if (token == null) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "登录失败");
        }
        return HttpResultUtils.success(token);
    }
}
