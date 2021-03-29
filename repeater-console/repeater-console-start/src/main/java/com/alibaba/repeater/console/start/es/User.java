//package com.alibaba.repeater.console.start.es;
//
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import javax.persistence.Id;
//import java.util.List;
//
///**
// * @author: liuheyng
// * @date: 2021/3/29 15:58
// * @description:
// */
//@Document(indexName = "userindex", type = "user")
//public class User {
//
//    @Id
//    private Long id;
//    private String first_name;
//    private String last_name;
//    private int age;
//    private String about;
//    private List<String> interests;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public List<String> getInterests() {
//        return interests;
//    }
//
//    public void setInterests(List<String> interests) {
//        this.interests = interests;
//    }
//
//    @Override
//    public String toString() {
//        return "User[id=" + id + ",first_name=" + first_name + ", last_name=" + last_name + ", age=" + age + ", about =" + about + ", interests=" + interests + "]";
//    }
//
//}
