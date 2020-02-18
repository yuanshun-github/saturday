package com.example.demo.dao;

import com.example.demo.bean.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookDao {

    List<Book> selectByName(@Param("book_name") String book_name,Integer begin,Integer sheet);

    int selectNumByName(String bookName);
}
