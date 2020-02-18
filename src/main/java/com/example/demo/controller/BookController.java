package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Value("${book.num}")
    private Integer bookSheetNum;

    @Resource
    private BookService bookService;


    /**
     * 通过名字分页查询
     *      当页面传过来的书籍参数大于总的数量的时候就表示书已经全部加载完毕
     * @param request
     * @return
     */
    @RequestMapping("/getBookByName")
    @ResponseBody
    public String selectBookByName(HttpServletRequest request){
        String name = request.getParameter("name");
        String num = request.getParameter("num");
        int begin = Integer.parseInt(num);
        System.out.println("前台输入的书名："+name);
        if(name == null)
            name = "";
        int allNum = bookService.selectNumByName(name);
        if(begin <= allNum){
            List<Book> bookList = bookService.selectByName(name,begin,bookSheetNum);
            System.out.println(bookList.size());
            name = JSON.toJSON(bookList).toString();
        }
        System.out.println(name);
        return name;
    }
}
