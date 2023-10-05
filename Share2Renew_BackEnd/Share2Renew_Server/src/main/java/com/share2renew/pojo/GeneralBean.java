package com.share2renew.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: Share2Renew_BackEnd
 * @description:
 * @author: Junxian Cai
 **/


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralBean implements Serializable{

        private long code;
        private String message;
        private Object obj;

        /**
         * 成功返回结果
         * @param message
         * @return
         */
        public static GeneralBean success(String message){
            return new GeneralBean(200 , message , null);
        }

        /**
         * 成功返回结果
         * @param message
         * @param obj
         * @return
         */
        public static GeneralBean success(String message , Object obj){
            return new GeneralBean(200 , message , obj);
        }

        public static GeneralBean success(Object obj){
            return success("Successful data retrieval from Back-End" , obj);
        }

        /**
         * 失败返回结果
         * @param message
         * @return
         */
        public static GeneralBean error(String message){
            return new GeneralBean(500 , message , null);
        }

        /**
         * 成功返回结果
         * @param message
         * @param obj
         * @return
         */
        public static GeneralBean error(String message , Object obj){
            return new GeneralBean(500 , message , obj);
        }




}
