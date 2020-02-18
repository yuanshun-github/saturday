package com.example.demo.service.impl;

import com.example.demo.bean.Book;

import java.util.List;

public interface BookServiceImpl {


    List<Book> selectByName(String name,Integer begin,Integer sheet);

    int selectNumByName(String bookName);
}
