package com.sheefee.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zorke on 2017/11/5.
 */
public class JsonController {
    private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

    private static Map<String, HttpStatus> httpStatusMap = new HashMap<String, HttpStatus>() {
        {
            put(Exception.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
            put(RuntimeException.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
            put(SecurityException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
            put(IllegalArgumentException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
            put(IllegalStateException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
            put(SysException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
            put(AccessControlException.class.getSimpleName(), HttpStatus.UNAUTHORIZED);
        }
    };

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponseBody> onException(Exception e) {
        HttpStatus httpStatus = httpStatusForException(e);
        ExceptionResponseBody body = responseForException(e);
        return new ResponseEntity<>(body, httpStatus);
//        return new ResponseEntity<ExceptionResponseBody>(body, httpStatus);
    }

    private ExceptionResponseBody responseForException(Exception e) {
        String exception = e.getClass().getSimpleName();
        String exceptionText = e.getMessage();
        logger.error("错误原因:", e);
        return new ExceptionResponseBody(exception, exceptionText);
    }

    protected HttpStatus httpStatusForException(Throwable e) {
        HttpStatus result = httpStatusForClass(e.getClass());
        if (result == null && e.getCause() != null) {
            result = httpStatusForException(e.getCause());
        }
        if (result == null) {
            result = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return result;
    }

    private HttpStatus httpStatusForClass(Class<?> clazz) {
        if (clazz == Object.class) {
            return null;
        }
        HttpStatus result = httpStatusMap.get(clazz.getSimpleName());
        if (result == null) {
            result = httpStatusForClass(clazz.getSuperclass());
        }
        return result;
    }

    public static class ExceptionResponseBody {
        private String responseCode;
        private String responseMsg;

        public ExceptionResponseBody(String responseCode, String responseMsg) {
            this.responseCode = responseCode;
            this.responseMsg = responseMsg;
        }

        public String getResponseCode() {
            return responseCode;
        }

        public String getResponseMsg() {
            return responseMsg;
        }
    }
}
