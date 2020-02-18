package com.example.demo.controller;

import com.example.demo.bean.ResourceFile;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.common.tool.HandlerString;
import com.example.demo.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("teacher")
@CrossOrigin
public class TeacherController {

    private static Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Value("${resource.num}")
    private Integer pageNum;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RedisService redisService;


    /**
     * 查看资料
     * @param request
     */
    @RequestMapping("selectResouces")
    @ResponseBody
    public String selectResouces(HttpServletRequest request){
        String result = "";
        String grade = request.getParameter("grade");
        String subject = request.getParameter("subject");
        String type = request.getParameter("type");
        String begin = request.getParameter("begin");
        try {
            List<ResourceFile> resourceFiles = resourceService.selectResourceByLimit(grade, subject, type, Integer.parseInt(begin), pageNum);
            result = WxResult.getSuccessResult(resourceFiles);
        }catch (Exception e){
            result = WxResult.getException(e);
        }finally {
            return result;
        }
    }

    /**
     * 搜索资料teacher/searchResources
     * @param request
     */
    @RequestMapping("searchResources")
    @ResponseBody
    public String searchResources(HttpServletRequest request){
        String result = "";
        String keyword = request.getParameter("keyword");
        String begin = request.getParameter("begin");
        keyword = keyword.replaceAll(" ","");//
        List<ResourceFile> resourceFiles = null;
        try{
            if(keyword.length()>2){
                //处理获取到的年级，学科
                String grades = HandlerString.keywordHandler(keyword, "grade", 1);
                String subjects = HandlerString.keywordHandler(keyword, "subject", 1);
                String keywords = HandlerString.handlerTwoStrToOne(grades, subjects, "|", "%");
                //开始进行查询，解析之后切割的数组长度是0，1，2，4，6类型的，暂时如此
                System.out.println(keywords);
                String[] keywordArr = keywords.split("[|]");
                System.out.println(keywordArr.length);
                if(keywordArr.length==1){
                    resourceFiles = resourceService.selectResourceByKeyword(keywordArr[0], Integer.parseInt(begin), pageNum);
                }else if(keywordArr.length==2){
                    resourceFiles = resourceService.selectResourceByTwoKeywords(keywordArr[0],keywordArr[1],Integer.parseInt(begin),pageNum);
                }else{
                    resourceFiles = resourceService.selectResourceByKeyword(keyword, Integer.parseInt(begin), pageNum);
                }
            }else{
                resourceFiles = resourceService.selectResourceByKeyword(keyword, Integer.parseInt(begin), pageNum);
            }
            result = WxResult.getSuccessResult(resourceFiles);
        }catch (Exception e){
            result = WxResult.getException(e);
        }finally {
            return result;
        }


    }


    /**
     * 下载资料文件
     * @return
     */
    @RequestMapping("downloadResourceFile")
    @ResponseBody
    public String downloadResourceFile(HttpServletRequest request, HttpServletResponse response){
        String result = "";
        String id = request.getParameter("id");
        String fileName = request.getParameter("fileName");
        //String pathBack = request.getParameter("path");
        String fileSize = request.getParameter("fileSize");
        //String path = "C:\\test\\files\\"+id+fileName;
        String path = "/usr/java/book/files/";
        path = path + id + fileName;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            logger.info("进入目录："+path+"；传入文件的长度为："+fileSize);
            File file = new File(path);
            if (file.exists()){
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename="+fileName);
                byte[] buffer = new byte[Integer.parseInt(fileSize)];
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os =response.getOutputStream();
                int i =bis.read(buffer);
                while(i!=-1){
                    os.write(buffer,0,i);
                    i = bis.read(buffer);
                }
            }

        }catch (Exception e){
            result = WxResult.getException(e);
        }finally {
            try {
                bis.close();
                fis.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return result;
        }
    }


    /**
     * 上传资料文件
     * @return
             */
    @RequestMapping("uploadResourceFile")
    @ResponseBody
    public String uploadResourceFile(@ModelAttribute ResourceFile resourceFile,HttpServletRequest request ,@RequestParam(value = "file", required = true) MultipartFile multipartFile) {
        String result = "";
        String fileName = multipartFile.getOriginalFilename();
        Integer userId = (Integer)redisService.get(request.getParameter("token"));
        logger.info("上传文件名是：" +  resourceFile.getFileName());
        try {
            if (!fileName.isEmpty()) {
                //resourceFile.setFileName(fileName);
                resourceFile.setUserId(userId);
                resourceFile.setStatus(1);
                resourceFile.setFileSize((int) multipartFile.getSize());
                resourceFile.setKeyword(resourceFile.getFileName());
                //存入数据库
                int i = resourceService.insertResource(resourceFile);
                logger.info("上传文件已保存到数据库中，其id是：" + i);
                //String path = "C:\\test\\filess\\" + i + resourceFile.getFileName();
                String path = "/usr/java/book/files/";
                path =  path +i+fileName;
                //存入服务器
                File file = new File(path);
                multipartFile.transferTo(file);
                result = WxResult.getSuccessResult("上传成功");
            } else {
                result = WxResult.getException("上传失败");
            }
        } catch (Exception e) {
        } finally {
            return result;
        }
    }



}
