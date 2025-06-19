package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.exception.MyException;

@ControllerAdvice
public class ControllerExceptionAdvisor
{
    @ExceptionHandler(value = {MyException.class})
    ResponseEntity handleMyException(MyException e){
        // ResponseEntity res =  ResponseEntity.ok()BodyBuilder();
        ResponseEntity res = null;
        res=new ResponseEntity<String>("Business generic Exception. please verify request !" + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        return res;
    }

//    @ExceptionHandler(value = {Exception.class})
//    ResponseEntity handleException(Exception e){
//        // ResponseEntity res =  ResponseEntity.ok()BodyBuilder();
//        ResponseEntity res = null;
//        res=new ResponseEntity<String>("Business Global Exception. please verify request !", HttpStatus.BAD_REQUEST);
//        return res;
//    }

}


