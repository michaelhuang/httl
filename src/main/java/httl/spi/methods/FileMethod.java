/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.spi.methods;

import httl.Context;
import httl.Engine;
import httl.Expression;
import httl.Resource;
import httl.Template;
import httl.spi.Resolver;
import httl.util.Digest;
import httl.util.IOUtils;
import httl.util.UrlUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

/**
 * FileMethod. (SPI, Singleton, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class FileMethod {

    private Engine engine;

	private Resolver resolver;

    /**
     * httl.properties: engine=httl.spi.engines.DefaultEngine
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

	/**
	 * httl.properties: resolver=httl.spi.resolvers.EngineResolver
	 */
    public void setResolver(Resolver resolver) {
		this.resolver = resolver;
	}

    private String getLocale() {
		Object value = resolver.get("locale");
		return value == null ? null : value.toString();
	}

    public String locale(String name) {
    	return locale(name, getLocale());
    }

    public String locale(String name, Locale locale) {
    	return locale(name, locale == null ? getLocale() : locale.toString());
    }

    public String locale(String name, String locale) {
    	if (name != null && name.length() > 0
    			&& locale != null && locale.length() > 0) {
    		int i = name.lastIndexOf('.');
    		String prefix;
    		String suffix;
    		if (i > 0) {
    			prefix = name.substring(0, i);
    			suffix = name.substring(i);
    		} else {
    			prefix = name;
    			suffix = "";
    		}
	    	for (;;) {
	    		String path = prefix + "_" + locale + suffix;
	        	if (engine.hasResource(path)) {
	        		return path;
	        	}
	        	int j = locale.lastIndexOf('_');
	        	if (j > 0) {
	        		locale = locale.substring(0, j);
	        	} else {
	        		break;
	        	}
	    	}
    	}
        return name;
    }

    public Expression evaluate(Object source) throws IOException, ParseException {
    	if (source instanceof byte[]) {
    		return evaluate((byte[]) source);
    	} else {
    		return evaluate((String) source);
    	}
    }

    public Expression evaluate(byte[] source) throws IOException, ParseException {
    	Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
        String encoding = template.getEncoding();
    	return evaluate(encoding == null ? new String(source) : new String(source, encoding));
    }

    public Expression evaluate(String expr) throws ParseException {
    	Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
    	return engine.getExpression(expr, template.getParameterTypes());
    }

    public Template render(Object source) throws IOException, ParseException {
    	if (source instanceof byte[]) {
    		return render((byte[]) source);
    	} else if (source instanceof Resource) {
    		return render((Resource) source);
    	} else {
    		return render((String) source);
    	}
    }

    public Template render(Resource resource) throws IOException, ParseException {
    	return render(IOUtils.readToString(resource.getReader()));
    }

    public Template render(byte[] source) throws IOException, ParseException {
    	Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
        String encoding = template.getEncoding();
    	return render(encoding == null ? new String(source) : new String(source, encoding));
    }

    public Template render(String source) throws IOException, ParseException {
        Template template = Context.getContext().getTemplate();
        if (template == null) {
            throw new IllegalArgumentException("display context template == null");
        }
        String name = template.getName() + "$" + Digest.getMD5(source);
        if (! engine.hasResource(name)) {
        	engine.addResource(name, source);
        }
        return engine.getTemplate(name);
    }

    public Template include(String name) throws IOException, ParseException {
        return include(name, (String) null);
    }

    public Template include(String name, String encoding) throws IOException, ParseException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("include template name == null");
        }
        String macro = null;
		int i = name.indexOf('#');
        if (i > 0) {
        	macro = name.substring(i + 1);
        	name = name.substring(0, i);
        }
        Template template = Context.getContext().getTemplate();
        if (template != null) {
            if (encoding == null || encoding.length() == 0) {
                encoding = template.getEncoding();
            }
            name = UrlUtils.relativeUrl(name, template.getName());
        }
        template = engine.getTemplate(name, encoding);
        if (macro != null && macro.length() > 0) {
			return template.getMacros().get(macro);
		}
        return template;
    }

    public Template include(String name, Map<String, Object> parameters) throws IOException, ParseException {
    	return include(name, null, parameters);
    }
    
    public Template include(String name, String encoding, Map<String, Object> parameters) throws IOException, ParseException {
    	if (parameters != null) {
    		Map<String, Object> contextParameters = Context.getContext().getParameters();
    		contextParameters.putAll(parameters);
    	}
    	return include(name, encoding);
    }

    public Resource read(String name) throws IOException, ParseException {
        return read(name, null);
    }

    public Resource read(String name, String encoding) throws IOException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("display template name == null");
        }
        Template template = Context.getContext().getTemplate();
        if (template != null) {
            if (encoding == null || encoding.length() == 0) {
                encoding = template.getEncoding();
            }
            name = UrlUtils.relativeUrl(name, template.getName());
        }
        return engine.getResource(name, encoding);
    }

}