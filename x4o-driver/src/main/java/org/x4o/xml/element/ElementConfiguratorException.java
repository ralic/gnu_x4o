/*
 * Copyright (c) 2004-2014, Willem Cazander
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *   following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *   the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package	org.x4o.xml.element;


/**
 * ElementConfiguratorException.<br>
 * 
 * @author Willem Cazander
 * @version 1.0 Aug 28, 2008
 */
@SuppressWarnings("serial")
public class ElementConfiguratorException extends ElementException {
	
	private ElementConfigurator elementConfigurator = null;
	
	/**
	 * Creates an configurator exception.
	 * @param config	The ElementConfigurator.
	 * @param message	The error message.
	 */
	public ElementConfiguratorException(ElementConfigurator config,String message) {
		super(message);
		this.elementConfigurator=config;
	}
	
	/**
	 * Creates an configurator exception.
	 * @param config	The ElementConfigurator.
	 * @param message	The error message.
	 * @param exception	The error exception.
	 */
	public ElementConfiguratorException(ElementConfigurator config,String message,Exception exception) {
		super(message,exception);
		this.elementConfigurator=config;
	}
	
	/**
	 * Creates an configurator exception.
	 * @param config	The ElementConfigurator.
	 * @param message	The error message.
	 * @param exception	The wrapped element error exception.
	 */
	public ElementConfiguratorException(ElementConfigurator config,String message,ElementException exception) {
		super(message,exception);
		this.elementConfigurator=config;
	}
	
	/**
	 * Gets the ElementConfigurator which has thrown this exception.
	 * @return	The ElementConfigurator.
	 */
	public ElementConfigurator getElementConfigurator() {
		return elementConfigurator;
	}
}
