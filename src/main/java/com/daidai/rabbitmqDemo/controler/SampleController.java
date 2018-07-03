package com.daidai.rabbitmqDemo.controler;

import com.daidai.rabbitmqDemo.rabbitmq.MQSender;
import com.daidai.rabbitmqDemo.result.CodeMsg;
import com.daidai.rabbitmqDemo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {



	@Autowired
    MQSender sender;
	
	@RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
		sender.sendHeader("hello,header");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
		sender.sendFanout("hello,fanout");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
		sender.sendTopic("hello,topic");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
		sender.send("hello,direct");
        return Result.success("Hello，world");
    }
	
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> home() {
        return Result.success("Hello，world");
    }
    
    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }
    

    
    
}
