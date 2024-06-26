package com.buka;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.buka.mapper")
public class ManagementMain {
    public static void main(String[] args) {
        SpringApplication.run(ManagementMain.class,args);
    }
    @Bean
    public RestHighLevelClient client(){
        //9200
        return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.28.167:9200")));
    }
}