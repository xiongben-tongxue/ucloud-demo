/*
 * Copyright (C) 2016  HangZhou YuShi Technology Co.Ltd  Holdings Ltd. All rights reserved
 *
 * 本代码版权归杭州宇石科技所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.gws.controllers;

import com.gws.enums.CodeStatus;
import com.gws.enums.SystemCode;
import com.gws.utils.GwsLogger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api请求基类
 */

@ResponseBody
public class BaseController {

    @ExceptionHandler(Exception.class)
    public JsonResult handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        GwsLogger.error(ex, "handle api exception");
        return response(SystemCode.HANDLE_EXCEPTION, null);
    }

    protected JsonResult success(Object data) {
        return new JsonResult(SystemCode.SUCCESS, data);
    }

    protected JsonResult error(CodeStatus codeStatus) {
        return new JsonResult(codeStatus, null);
    }

    protected JsonResult response(CodeStatus codeStatus, Object data) {
        return new JsonResult(codeStatus, data);
    }


}
