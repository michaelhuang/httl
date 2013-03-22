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

import httl.Node;
import httl.Visitor;

import java.text.ParseException;
import java.util.Map;

/**
 * Expression. (API, Prototype, Immutable, ThreadSafe)
 * 
 * <pre>
 * Engine engine = Engine.getEngine();
 * Expression expression = engine.getExpression("1 + 2");
 * </pre>
 * 
 * @see httl.Engine#getExpression(String)
 * @see httl.Engine#getExpression(String, Map)
 * @see httl.spi.Translator#translate(String, java.util.Map, int)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class Expression implements Node {

	private final int offset;

	public Expression(int offset) {
		this.offset = offset;
	}
	
	public Object evaluate(Object context) throws ParseException {
		// TODO
		return null;
	}

	public void accept(Visitor visitor) throws ParseException {
		visitor.visit(this);
	}

	public int getOffset() {
		return offset;
	}

}
