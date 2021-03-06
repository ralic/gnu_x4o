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
package org.x4o.xml.element;

import java.util.logging.Logger;

import org.x4o.xml.lang.X4OLanguageSession;
import org.x4o.xml.lang.X4OLanguage;
import org.x4o.xml.lang.X4OLanguageClassLoader;

/**
 * DefaultElementNamespaceInstanceProvider creates and configures an Element instance.
 * 
 * @author Willem Cazander
 * @version 1.0 Aug 17, 2005
 */
public class DefaultElementNamespaceInstanceProvider implements ElementNamespaceInstanceProvider {

	private Logger logger = null;
	private ElementNamespace elementNamespace = null;
	
	/**
	 * Creates new DefaultElementNamespaceInstanceProvider.
	 */
	public DefaultElementNamespaceInstanceProvider() {
		logger = Logger.getLogger(DefaultElementNamespaceInstanceProvider.class.getName());
	}

	/**
	 * @param	language	The elementLanguage of this provider.
	 * @param	elementNamespace	The elementNamespace for this provider.
	 * @see org.x4o.xml.element.ElementNamespaceInstanceProvider#start(org.x4o.xml.lang.X4OLanguage, org.x4o.xml.element.ElementNamespace)
	 */
	public void start(X4OLanguage language,ElementNamespace elementNamespace) {
		this.elementNamespace=elementNamespace;
		logger.finer("Starting DefaultElementNamespaceInstanceProvider for: "+elementNamespace.getUri());
	}

	/**
	 * @param	languageSession The language context for which we create the Element instance.
	 * @param	tag	The xml tag to create an Element instance for.
	 * @return	The Element to handle the given tag.
	 * @throws ElementNamespaceInstanceProviderException 
	 * @see org.x4o.xml.element.ElementNamespaceInstanceProvider#createElementInstance(org.x4o.xml.lang.X4OLanguageSession,java.lang.String)
	 */
	public Element createElementInstance(X4OLanguageSession languageSession,String tag) throws ElementNamespaceInstanceProviderException {
		ElementClass	elementClass	= elementNamespace.getElementClass(tag);
		Element 		element			= null;
		
		if (elementClass==null) {
			throw new ElementNamespaceInstanceProviderException(this,"Tag: " + tag + " unknown in: " + elementNamespace.getUri());
		}
		
		try {
			if (elementClass.getElementClass()!=null) {
				Object obj = X4OLanguageClassLoader.newInstance(elementClass.getElementClass());
				if (obj instanceof Element) {
					element = (Element) obj;
				} else {
					throw new ElementNamespaceInstanceProviderException(this,"Provided elementClassName is not an Element: "+obj.getClass());
				}
			} else {
				element = (Element)X4OLanguageClassLoader.newInstance((languageSession.getLanguage().getLanguageConfiguration().getDefaultElement()));
			}
			
			if (elementClass.getObjectClass()!=null) {
				element.setElementObject(X4OLanguageClassLoader.newInstance(elementClass.getObjectClass()));
			}
		} catch (InstantiationException e) {
			throw new ElementNamespaceInstanceProviderException(this,"Error while providing Element: "+e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementNamespaceInstanceProviderException(this,"Error while providing Element: "+e.getMessage(),e);
		} /*catch (ClassNotFoundException e) {
			throw new ElementNamespaceInstanceProviderException(this,"Error while providing Element: "+e.getMessage(),e);
		} */
		element.setElementClass(elementClass);
		element.setLanguageSession(languageSession);
		return element;
	}
}
