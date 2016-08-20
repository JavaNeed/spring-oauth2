package com.vivsoft.test;

import java.util.ArrayList;
import java.util.List;

import sun.misc.BASE64Encoder;

/**
 * Created with IntelliJ IDEA.
 * User: vivianvanzyl
 * Date: 6/16/14
 * Time: 7:57 PM
 */
public class Base64Test {
    public Base64Test() {
    }

    public static void main(String[] args) {
        System.err.println(new BASE64Encoder().encode("admin:password".getBytes()));
    }
}
