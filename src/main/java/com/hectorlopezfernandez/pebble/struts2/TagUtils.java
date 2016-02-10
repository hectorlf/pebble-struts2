package com.hectorlopezfernandez.pebble.struts2;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.ValueStack;

public class TagUtils {

	public static ValueStack getStack(HttpServletRequest request) {
    	// we require an existing ValueStack, you'd better configure Struts properly
        ValueStack stack = (ValueStack) request.getAttribute(ServletActionContext.STRUTS_VALUESTACK_KEY);
        if (stack == null) throw new ConfigurationException("The Struts root ValueStack cannot be found. Configure Struts properly and make sure every request that uses Pebble's Struts extension goes through the Struts filter.");
        return stack;
    }

    public static String buildNamespace(ActionMapper mapper, ValueStack stack, HttpServletRequest request) {
        ActionContext context = new ActionContext(stack.getContext());
        ActionInvocation invocation = context.getActionInvocation();

        if (invocation == null) {
            ActionMapping mapping = mapper.getMapping(request,
                    Dispatcher.getInstance().getConfigurationManager());

            if (mapping != null) {
                return mapping.getNamespace();
            } else {
                // well, if the ActionMapper can't tell us, and there is no existing action invocation,
                // let's just go with a default guess that the namespace is the last the path minus the
                // last part (/foo/bar/baz.xyz -> /foo/bar)

                String path = RequestUtils.getServletPath(request);
                return path.substring(0, path.lastIndexOf("/"));
            }
        } else {
            return invocation.getProxy().getNamespace();
        }
    }

}