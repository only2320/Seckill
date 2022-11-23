package com.example.seckill.utils;

import com.example.seckill.pojo.User;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//生成用户工具类
@SpringBootTest
@Service
public class UserUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private void createUser(int count) throws Exception {
        List<User> users=new ArrayList<User>(count);
//        for(int i=0;i<count;i++){
//            User user=new User();
//            user.setId(13500000000L+i);
//            user.setNickname("user"+i);
//            user.setSlat("1a2b3c4d");
//            user.setPassword(MD5Util.inputPassToDBpass("123456",user.getSlat()));
//            user.setLoginCount(1);
//            user.setRegisterDate(new Date());
//            users.add(user);
//        }
//        System.out.println("creat user");
//
//        //插入数据库
//        Connection conn=getConn();
//        String sql="replace into t_user(login_count, nickname, register_date,slat, password, id)values(?,?,?,?,?,?)";
//        PreparedStatement pstmt =conn.prepareStatement(sql);
//        for(int i=0;i<users.size();i++){
//            User user=users.get(i);
//            pstmt.setInt(1,user.getLoginCount());
//            pstmt.setString(2,user.getNickname());
//            pstmt.setTimestamp(3,new Timestamp(user.getRegisterDate().getTime()));
//            pstmt.setString(4,user.getSlat());
//            pstmt.setString(5,user.getPassword());
//            pstmt.setLong(6,user.getId());
//            //批量添加
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.clearParameters();
//        conn.close();
//        System.out.println("insert into db");

        //生成userticket
        File file=new File("D:\\config.txt");
        if(file.exists()){
            file.delete();
        }
        //
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
        //读取文件所有内容
        raf.seek(0);
        for(int i=0;i<users.size();i++){
            User user=users.get(i);

            String ticket = UUIDUtil.uuid();
            //將用户信息存入redis中
            redisTemplate.opsForValue().set("user:"+ticket,user);

            String row=user.getId()+ "," + ticket;
            raf.seek(raf.length());
            //getBytes把string转化为一个字节数组byte[]的方法
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file :" +user.getId());
        }
        raf.close();
        System.out.println("over");
    }
    //获取数据库链接
    private  static Connection getConn() throws Exception {
        String url="jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username="root";
        String password="root";
        String driver="com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }

    @Test
    public void testCreateUser() throws Exception {
        createUser(5000);
    }
}
