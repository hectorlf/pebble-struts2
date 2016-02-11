package com.hectorlopezfernandez.pebble.struts2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.ScopeChain;
import com.opensymphony.xwork2.util.ValueStack;

public class PropertyFunction implements Function {

	public static final String FUNCTION_NAME = "sProperty";

    private final List<String> argumentNames = new ArrayList<>(2);

    public PropertyFunction() {
        argumentNames.add("value");
        argumentNames.add("default");
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object execute(Map<String, Object> args) {
        EvaluationContext context = (EvaluationContext) args.get("_context");
        ScopeChain scope = context.getScopeChain();
        
        // ognl expression
        Object value = args.get("value");
        if (value == null) {
            value = "top";
        } else if (!(value instanceof String)) {
        	throw new IllegalArgumentException("Only String types are supported for the value argument. Actual type: " + value.getClass().getName());
        }

        // delegate lookup to struts
        HttpServletRequest request = (HttpServletRequest) scope.get(Struts2Extension.HTTP_SERVLET_REQUEST);
        ValueStack stack = TagUtils.getStack(request);
        String actualValue = (String) stack.findValue(value.toString(), String.class, true);

        // default value check
        if (actualValue == null) {
        	Object defaultValue = args.get("default");
        	if (defaultValue != null) actualValue = defaultValue.toString();
        }

		return actualValue;
    }

}