package com.lijian.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPrropNames = {
            "jdbc.username", "jdbc.password"
    };

    @Override
    protected String convertProperty(String propertyName, String propertyvalue) {

        if (isEncryptProp(propertyName)) {
            String decryptValue = DESUtil.getDecryptString(propertyvalue);
            return  decryptValue;


        }else {
            return  propertyvalue;
        }
    }

    private boolean isEncryptProp(String propertyName) {

        for (String encryptpropertyName : encryptPrropNames) {
            if (encryptpropertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}
