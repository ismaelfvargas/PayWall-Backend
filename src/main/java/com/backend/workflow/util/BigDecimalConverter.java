// Essa classe converte as strings em Bigdecimal

package com.backend.workflow.util;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class BigDecimalConverter {

    public BigDecimal converter(String value){
        if(value == null){
            return null;
        }
        value = value.replace(".", "").replace(",", ".");
                return new BigDecimal(value);
    }
}
