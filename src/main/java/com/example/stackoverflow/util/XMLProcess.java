package com.example.stackoverflow.util;

import com.example.stackoverflow.dao.Dao;
import com.example.stackoverflow.domain.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class XMLProcess {
    @Autowired
    Dao dao;

    @GetMapping("/index")
    public String index(){
        System.out.println("开始。");

        try {
            dao.parsePosts("E:\\stackoverflow\\Posts.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        System.out.println("结束。");
        return "hello";
    }


}
