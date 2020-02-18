package com.example.demo.service;

import com.example.demo.bean.Book;
import com.example.demo.dao.BookDao;
import com.example.demo.service.impl.BookServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service("BookServiceImpl")
public class BookService implements BookServiceImpl {

    @Resource
    private BookDao bookDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Book> selectByName(String name,Integer begin,Integer sheet) {
        return bookDao.selectByName(name,begin,sheet);
    }

    @Override
    public int selectNumByName(String bookName) {
        int i = bookDao.selectNumByName(bookName);
        return i;
    }
}
