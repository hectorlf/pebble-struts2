package com.hectorlopezfernandez.pebble.struts2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.util.TextProviderHelper;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.ScopeChain;
import com.opensymphony.xwork2.util.ValueStack;

public class TextFunction implements Function {

	public static final String FUNCTION_NAME = "sText";

    public TextFunction() {
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object execute(Map<String, Object> args) {
        EvaluationContext context = (EvaluationContext) args.get("_context");
        ScopeChain scope = context.getScopeChain();
        
        // first argument should be the key
        String key = (String) args.get("0");

        // arguments to the i18n
        int i = 1;
        List<Object> arguments = new ArrayList<>();
        while (args.containsKey(String.valueOf(i))) {
            Object param = args.get(String.valueOf(i));
            arguments.add(param);
            i++;
        }

        // delegate lookup to struts
        HttpServletRequest request = (HttpServletRequest) scope.get(Struts2Extension.HTTP_SERVLET_REQUEST);
        ValueStack stack = TagUtils.getStack(request);
        String msg = TextProviderHelper.getText(key, key, arguments, stack, false);

		return msg;
    }

}