package com.example.angel.kalkulatorx;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class Calculator {

    public  Number calculate(String equation){
        if(equation.endsWith("/ 0")){
            return 0;
        }
        JexlEngine tst = new JexlBuilder().cache(512).strict(true).silent(false).create();
        JexlExpression e = tst.createExpression(equation);
        JexlContext context = new MapContext();
        return (Number) e.evaluate(context);
    }

}
