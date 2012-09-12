/*
 * Copyright (c) 2004-2012, Willem Cazander
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

package org.x4o.xml.core.config;

import java.util.List;
import java.util.Map;

import org.x4o.xml.core.X4ODebugWriter;
import org.x4o.xml.element.ElementLanguage;


/**
 * X4OLanguageConfiguration first used interface in x4o parser which does the hard config of the x4o xml parsing.
 * 
 * @author Willem Cazander
 * @version 1.0 27 Oct 2009
 */
public interface X4OLanguageConfiguration {
	
	/** Prefix where we load all language definitions from. */
	public static final String DEFAULT_LANG_PATH_PREFIX = "META-INF";
	
	/** The modules file to startup the language definition process. */
	public static final String DEFAULT_LANG_MODULES_FILE = "-modules.xml";
	
	/**
	 * Returns the language for which this ElementLanguage is created.
	 * @return	Returns the language.
	 */
	String getLanguage();
	
	/**
	 * @return	Returns the languageVersion of the parsing of this language.
	 */
	String getLanguageVersion();
	
	/**
	 * @return	Returns the path prefix for loading language resources.
	 */
	String getLanguageResourcePathPrefix();
	
	/**
	 * @return	Returns the filename (postfix) of the modules definition file.
	 */
	String getLanguageResourceModulesFileName();
	
	
	Object getLanguageProperty(X4OLanguageProperty property);
	void setLanguageProperty(X4OLanguageProperty property,Object object);
	boolean getLanguagePropertyBoolean(X4OLanguageProperty property);
	int getLanguagePropertyInteger(X4OLanguageProperty property);
	
	
	// Core interfaces are also in class for text reference without instance
	Class<?> getDefaultElementNamespaceContext();
	Class<?> getDefaultElementInterface();
	Class<?> getDefaultElement();
	Class<?> getDefaultElementClass();
	Class<?> getDefaultElementClassAttribute();
	
	// Other needed interfaces in class form also
	Class<?> getDefaultElementLanguageModule();
	Class<?> getDefaultElementBodyComment();
	Class<?> getDefaultElementBodyCharacters();
	Class<?> getDefaultElementBodyWhitespace();
	Class<?> getDefaultElementNamespaceInstanceProvider();
	Class<?> getDefaultElementAttributeValueParser();
	Class<?> getDefaultElementObjectPropertyValue();
	Class<?> getDefaultElementAttributeHandlerComparator();
	
	/**
	 * @return	Returns the X4OLanguageVersionFilter which filters the best version to use.
	 */
	Class<?> getDefaultX4OLanguageVersionFilter();
	
	/**
	 * @return	Returns the X4OLanguageLoader which loads languages into the element context.
	 */
	Class<?> getDefaultX4OLanguageLoader();

	/**
	 * Creates and filles the inital element language used to store the language.
	 * @return	The newly created ElementLanguage.
	 */
	ElementLanguage createElementLanguage();

	/**
	 * @return	Returns Map of SAX properties which are set.
	 */
	Map<String,Object> getSAXParserProperties();
	
	/**
	 * @return	Returns Map of SAX properties which are optional set.
	 */
	Map<String,Object> getSAXParserPropertiesOptional();
	
	/**
	 * @return	Returns Map of SAX features which are set on the xml parser.
	 */
	Map<String,Boolean> getSAXParserFeatures();
	
	/**
	 * @return	Returns Map of SAX features which are optional set.
	 */
	Map<String, Boolean> getSAXParserFeaturesOptional();
	
	/**
	 * @return	Returns List of SAX features which are required for xml parsing.
	 */
	List<String> getSAXParserFeaturesRequired();
	
	/**
	 * @return	Returns null or an X4ODebugWriter to write parsing steps and debug data to.
	 */
	X4ODebugWriter getX4ODebugWriter();
	
	/** 
	 * @return	Returns true if this config has a debug writer.
	 */
	boolean hasX4ODebugWriter();
	
	/**
	 * @param debugWriter	The debug writer to set
	 */
	void setX4ODebugWriter(X4ODebugWriter debugWriter);
}
