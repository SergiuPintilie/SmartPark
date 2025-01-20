package IC.SmartPark.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("/")
    private String test(){
        return "Hello!";
    }
    @GetMapping("/test")
    private String test1(){
        return "Hello world!";
    }
}
