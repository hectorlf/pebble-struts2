package com.hectorlopezfernandez.pebble.struts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

public class Struts2Extension extends AbstractExtension {

	public static final String HTTP_SERVLET_REQUEST = "request";
	public static final String HTTP_SERVLET_RESPONSE = "response";
	
    public Struts2Extension() {
    }

    @Override
    public Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new HashMap<>();
        return filters;
    }
    
    @Override
    public Map<String, Function> getFunctions() {
    	Map<String, Function> functions = new HashMap<>(2);
    	functions.put(TextFunction.FUNCTION_NAME, new TextFunction());
        return functions;
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        List<TokenParser> parsers = new ArrayList<>();
        return parsers;
    }

}