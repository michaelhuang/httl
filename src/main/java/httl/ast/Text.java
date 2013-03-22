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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.ParseException;
import java.util.Map;

/**
 * Text
 * 
 * @author @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Text extends Directive {

	private String content;

	private boolean literal;

	public Text(String content, boolean literal, int offset) {
		super(offset);
		this.content = content;
		this.literal = literal;
	}

	public void render(Map<String, Object> context, Object out) throws IOException,
			ParseException {
		if (out instanceof OutputStream) {
			((OutputStream) out).write(content.getBytes());
		} else {
			((Writer) out).write(content);
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isLiteral() {
		return literal;
	}

	public void setLiteral(boolean literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal ? "#[" + content + "]#" : content;
	}

}