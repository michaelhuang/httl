/*
 * Copyright 2011-2013 HTTL Team.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package httl.ast;

import httl.Engine;
import httl.Node;
import httl.Template;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Macro
 * 
 * @author @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Macro extends BlockDirective implements Template {
	
	private String name;

	public Macro() {
	}

	public Macro(String name, int offset) {
		super(offset);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getLastModified() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSource() {
		return toString();
	}

	public Reader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Engine getEngine() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object evaluate() throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object evaluate(Object context) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	public void render(Object out) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
	}

	public void render(Object context, Object out) throws IOException,
			ParseException {
		// TODO Auto-generated method stub
		
	}

	public List<Node> getNodes() {
		return (List) getChildren();
	}

	public Template getMacro(String name) {
		return getMacros().get(name);
	}
	
	private Map<String, Template> macros;

	public Map<String, Template> getMacros() {
		if (macros == null) {
			Map<String, Template> map = new HashMap<String, Template>();
			for (Node node : getNodes()) {
				if (node instanceof Macro) {
					Macro macro = (Macro) node;
					map.put(macro.getName(), macro);
				}
			}
			macros = map;
		}
		return macros;
	}

	public boolean isMacro() {
		return true;
	}

	public Class<?> getRootType() {
		return Void.class;
	}

	public Map<String, Class<?>> getVariableTypes() {
		return new HashMap<String, Class<?>>();
	}

	public Class<?> getReturnType() {
		return String.class;
	}

	public Map<String, Class<?>> getExportTypes() {
		return new HashMap<String, Class<?>>();
	}

	public String getCode() {
		return "";
	}

}
