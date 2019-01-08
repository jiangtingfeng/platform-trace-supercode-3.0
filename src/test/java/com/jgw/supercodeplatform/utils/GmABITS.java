package com.jgw.supercodeplatform.utils;

import org.mybatis.generator.api.ShellRunner;

/**
 * 描述：
 * <p>
 * Created by corbett on 2018/10/12.
 */
public class GmABITS {
    public static void main(String[] args) {
        String[] argsss = new String[]{"-configfile", "src\\main\\resources\\asdasd.xml", "-overwrite"};
        ShellRunner.main(argsss);
    }
}
