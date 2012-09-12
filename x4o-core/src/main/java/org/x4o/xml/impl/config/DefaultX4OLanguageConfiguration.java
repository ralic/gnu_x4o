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

package org.x4o.xml.impl.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ExpressionFactory;

import org.x4o.xml.core.X4ODebugWriter;
import org.x4o.xml.core.config.X4OLanguageClassLoader;
import org.x4o.xml.core.config.X4OLanguageProperty;
import org.x4o.xml.core.config.X4OLanguageConfiguration;
import org.x4o.xml.eld.EldParser;
import org.x4o.xml.element.ElementAttributeValueParser;
import org.x4o.xml.element.ElementLanguage;
import org.x4o.xml.element.ElementLanguageLocal;
import org.x4o.xml.element.ElementObjectPropertyValue;
import org.x4o.xml.impl.DefaultElementBodyCharacters;
import org.x4o.xml.impl.DefaultElementBodyComment;
import org.x4o.xml.impl.DefaultElement;
import org.x4o.xml.impl.DefaultElementAttributeValueParser;
import org.x4o.xml.impl.DefaultElementClass;
import org.x4o.xml.impl.DefaultElementClassAttribute;
import org.x4o.xml.impl.DefaultElementLanguage;
import org.x4o.xml.impl.DefaultElementInterface;
import org.x4o.xml.impl.DefaultElementLanguageModule;
import org.x4o.xml.impl.DefaultElementNamespaceContext;
import org.x4o.xml.impl.DefaultElementNamespaceInstanceProvider;
import org.x4o.xml.impl.DefaultElementObjectPropertyValue;
import org.x4o.xml.impl.DefaultGlobalAttributeHandlerComparator;
import org.x4o.xml.impl.DefaultElementBodyWhitespace;
import org.x4o.xml.impl.el.X4OELContext;


/**
 * Provides all implementions of the different parts of the language parser.
 * 
 * @author Willem Cazander
 * @version 1.0 27 Oct 2009
 */
public class DefaultX4OLanguageConfiguration implements X4OLanguageConfiguration {
	
	private X4ODebugWriter debugWriter;
	final private Map<X4OLanguageProperty,Object> languageProperties;
	
	public DefaultX4OLanguageConfiguration(String language,String languageVersion) {
		if (language==null) {
			throw new NullPointerException("language may not be null");
		}
		if (language.isEmpty()) {
			throw new IllegalArgumentException("language may not be empty");
		}
		if (languageVersion==null) {
			throw new NullPointerException("languageVersion may not be null");
		}
		if (languageVersion.isEmpty()) {
			throw new IllegalArgumentException("languageVersion may not be empty");
		}
		languageProperties = new HashMap<X4OLanguageProperty,Object>(20);
		languageProperties.put(X4OLanguageProperty.LANGUAGE_NAME, language);
		languageProperties.put(X4OLanguageProperty.LANGUAGE_VERSION, languageVersion);
	}
	
	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguage()
	 */
	public String getLanguage() {
		return (String)languageProperties.get(X4OLanguageProperty.LANGUAGE_NAME);
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguageVersion()
	 */
	public String getLanguageVersion() {
		return (String)languageProperties.get(X4OLanguageProperty.LANGUAGE_VERSION);
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguageResourcePathPrefix()
	 */
	public String getLanguageResourcePathPrefix() {
		return X4OLanguageConfiguration.DEFAULT_LANG_PATH_PREFIX;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguageResourceModulesFileName()
	 */
	public String getLanguageResourceModulesFileName() {
		return X4OLanguageConfiguration.DEFAULT_LANG_MODULES_FILE;
	}
	
	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguageProperty(org.x4o.xml.core.config.X4OLanguageProperty)
	 */
	public Object getLanguageProperty(X4OLanguageProperty property) {
		return languageProperties.get(property);
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#setLanguageProperty(org.x4o.xml.core.config.X4OLanguageProperty, java.lang.Object)
	 */
	public void setLanguageProperty(X4OLanguageProperty property, Object value) {
		if (property.isValueValid(value)==false) {
			throw new IllegalArgumentException("Now allowed to set value: "+value+" in property: "+property.name());
		}
		languageProperties.put(property, value);
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguagePropertyBoolean(org.x4o.xml.core.config.X4OLanguageProperty)
	 */
	public boolean getLanguagePropertyBoolean(X4OLanguageProperty property) {
		Object value = getLanguageProperty(property);
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		return (Boolean)property.getDefaultValue();
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getLanguagePropertyInteger(org.x4o.xml.core.config.X4OLanguageProperty)
	 */
	public int getLanguagePropertyInteger(X4OLanguageProperty property) {
		Object value = getLanguageProperty(property);
		if (value instanceof Integer) {
			return (Integer)value;
		}
		return (Integer)property.getDefaultValue();
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementNamespaceContext()
	 */
	public Class<?> getDefaultElementNamespaceContext() {
		return DefaultElementNamespaceContext.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementInterface()
	 */
	public Class<?> getDefaultElementInterface() {
		return DefaultElementInterface.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElement()
	 */
	public Class<?> getDefaultElement() {
		return DefaultElement.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementClass()
	 */
	public Class<?> getDefaultElementClass() {
		return DefaultElementClass.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementClassAttribute()
	 */
	public Class<?> getDefaultElementClassAttribute() {
		return DefaultElementClassAttribute.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementLanguageModule()
	 */
	public Class<?> getDefaultElementLanguageModule() {
		return DefaultElementLanguageModule.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementBodyComment()
	 */
	public Class<?> getDefaultElementBodyComment() {
		return DefaultElementBodyComment.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementBodyCharacters()
	 */
	public Class<?> getDefaultElementBodyCharacters() {
		return DefaultElementBodyCharacters.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementBodyWhitespace()
	 */
	public Class<?> getDefaultElementBodyWhitespace() {
		return DefaultElementBodyWhitespace.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementNamespaceInstanceProvider()
	 */
	public Class<?> getDefaultElementNamespaceInstanceProvider() {
		return DefaultElementNamespaceInstanceProvider.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementAttributeValueParser()
	 */
	public Class<?> getDefaultElementAttributeValueParser() {
		return DefaultElementAttributeValueParser.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementObjectPropertyValue()
	 */
	public Class<?> getDefaultElementObjectPropertyValue() {
		return DefaultElementObjectPropertyValue.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultElementAttributeHandlerComparator()
	 */
	public Class<?> getDefaultElementAttributeHandlerComparator() {
		return DefaultGlobalAttributeHandlerComparator.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultX4OLanguageVersionFilter()
	 */
	public Class<?> getDefaultX4OLanguageVersionFilter() {
		return DefaultX4OLanguageVersionFilter.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getDefaultX4OLanguageLoader()
	 */
	public Class<?> getDefaultX4OLanguageLoader() {
		return DefaultX4OLanguageLoader.class;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#createElementLanguage()
	 */
	public ElementLanguage createElementLanguage() {
		return configElementLanguage(new DefaultElementLanguage());
	}

	protected ElementLanguage configElementLanguage(ElementLanguage elementLanguage) {
		if ((elementLanguage instanceof ElementLanguageLocal)==false) { 
			throw new RuntimeException("Can't init ElementLanguage which has not ElementLanguageLocal interface obj: "+elementLanguage);
		}
		ElementLanguageLocal contextInit = (ElementLanguageLocal)elementLanguage; 
		contextInit.setLanguageConfiguration(this);
		
		if (contextInit.getExpressionFactory()==null) {
			contextInit.setExpressionFactory(configExpressionFactory());
		}
		if (contextInit.getELContext()==null) {
			contextInit.setELContext(new X4OELContext());
		}
		try {
			if (contextInit.getElementAttributeValueParser()==null) {
				contextInit.setElementAttributeValueParser((ElementAttributeValueParser)X4OLanguageClassLoader.newInstance(getDefaultElementAttributeValueParser()));
			}
			if (contextInit.getElementObjectPropertyValue()==null) {
				contextInit.setElementObjectPropertyValue((ElementObjectPropertyValue)X4OLanguageClassLoader.newInstance(getDefaultElementObjectPropertyValue()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return elementLanguage;
	}

	protected ExpressionFactory configExpressionFactory() {
		ExpressionFactory factory = (ExpressionFactory)getLanguageProperty(X4OLanguageProperty.EL_FACTORY_INSTANCE);
		if (factory!=null) {
			return factory;
		}
		try {
			Class<?> expressionFactoryClass = X4OLanguageClassLoader.loadClass("org.apache.el.ExpressionFactoryImpl");
			ExpressionFactory expressionFactory = (ExpressionFactory) expressionFactoryClass.newInstance();
			return expressionFactory;
		} catch (Exception e) {
			try {
				Class<?> expressionFactoryClass = X4OLanguageClassLoader.loadClass("de.odysseus.el.ExpressionFactoryImpl");
				ExpressionFactory expressionFactory = (ExpressionFactory) expressionFactoryClass.newInstance();
				return expressionFactory;
			} catch (Exception ee) {
				throw new RuntimeException("Could not load ExpressionFactory tried: org.apache.el.ExpressionFactoryImpl and de.odysseus.el.ExpressionFactoryImpl but could not load one of them.");
			}
		}
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getSAXParserProperties()
	 */
	public Map<String, Object> getSAXParserProperties() {
		Map<String,Object> saxParserProperties = new HashMap<String,Object>(1);
		return saxParserProperties;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getSAXParserPropertiesOptional()
	 */
	public Map<String, Object> getSAXParserPropertiesOptional() {
		Map<String,Object> saxParserProperties = new HashMap<String,Object>(1);
		saxParserProperties.put("http://apache.org/xml/properties/input-buffer-size",getLanguagePropertyInteger(X4OLanguageProperty.INPUT_BUFFER_SIZE));	// Increase buffer to 8KB
		return saxParserProperties;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getSAXParserFeatures()
	 */
	public Map<String, Boolean> getSAXParserFeatures() {
		
		// see example: http://xerces.apache.org/xerces2-j/features.html
		Map<String,Boolean> saxParserFeatures = new HashMap<String,Boolean>(20);
		
		// Tune Sax Parser
		saxParserFeatures.put("http://xml.org/sax/features/namespaces", 								true);	// Perform namespace processing
		saxParserFeatures.put("http://xml.org/sax/features/use-entity-resolver2", 						true);	// Use EntityResolver2 interface
		saxParserFeatures.put("http://xml.org/sax/features/lexical-handler/parameter-entities", 		true);	// Report parameter entities to a the LexicalHandler.
		
		saxParserFeatures.put("http://xml.org/sax/features/xmlns-uris", 								false); // Namespace declaration attributes are reported as having no namespace.
		saxParserFeatures.put("http://xml.org/sax/features/namespace-prefixes", 						false);	// Do not report attributes used for Namespace declarations
		
		saxParserFeatures.put("http://xml.org/sax/features/external-general-entities",					false);	// Never include the external general entries.
		saxParserFeatures.put("http://xml.org/sax/features/external-parameter-entities",				false);	// Never include the external parameter or DTD subset.
		
		saxParserFeatures.put("http://apache.org/xml/features/xinclude", 								true);	// Perform XInclude processing
		saxParserFeatures.put("http://apache.org/xml/features/xinclude/fixup-base-uris",				false);
		saxParserFeatures.put("http://apache.org/xml/features/xinclude/fixup-language",					false);
		
		boolean validation = false;
		boolean validationXsd = false;
		if (EldParser.ELD_LANGUAGE.equals(getLanguage())) {
			validation = getLanguagePropertyBoolean(X4OLanguageProperty.VALIDATION_ELD);
			validationXsd = getLanguagePropertyBoolean(X4OLanguageProperty.VALIDATION_ELD_XSD);
		} else {
			validation = getLanguagePropertyBoolean(X4OLanguageProperty.VALIDATION_INPUT);
			validationXsd = getLanguagePropertyBoolean(X4OLanguageProperty.VALIDATION_INPUT_XSD);
		}
		if (validation) {
			saxParserFeatures.put("http://xml.org/sax/features/validation", 							true);	// Validate the document and report validity errors.
			saxParserFeatures.put("http://apache.org/xml/features/validation/schema",					true);	// Insert an XML Schema validator into the pipeline.
		} else {
			saxParserFeatures.put("http://xml.org/sax/features/validation", 							false);
			saxParserFeatures.put("http://apache.org/xml/features/validation/schema",					false);
		}
		if (validation && validationXsd) {
			saxParserFeatures.put("http://apache.org/xml/features/validation/schema-full-checking",		true);	// Enable validation of the schema grammar itself for errors.
		} else {
			saxParserFeatures.put("http://apache.org/xml/features/validation/schema-full-checking",		false);	// Disable validation of the schema grammar itself for errors.
		}
		
		
		return saxParserFeatures;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getSAXParserFeaturesOptional()
	 */
	public Map<String, Boolean> getSAXParserFeaturesOptional() {
		Map<String,Boolean> saxParserFeatures = new HashMap<String,Boolean>(20);
		
		// Make Sax Impl more strict.
		saxParserFeatures.put("http://apache.org/xml/features/disallow-doctype-decl", 					true);	// Throws error if document contains a DOCTYPE declaration.
		saxParserFeatures.put("http://apache.org/xml/features/validation/schema/normalized-value",		true);	// Expose normalized values for attributes and elements.
		saxParserFeatures.put("http://apache.org/xml/features/validation/warn-on-duplicate-attdef",		true);	// Report a warning when a duplicate attribute is re-declared.
		saxParserFeatures.put("http://apache.org/xml/features/warn-on-duplicate-entitydef", 			true);	// Report a warning for duplicate entity declaration.  
		saxParserFeatures.put("http://apache.org/xml/features/validation/dynamic", 						false);	// Validation is determined by the state of the validation feature.
		saxParserFeatures.put("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",			false);	// Do not use the default DTD grammer
		saxParserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd",			false);	// Ignore the external DTD completely.
		
		// need newer version
		//saxParserFeatures.put("http://apache.org/xml/features/standard-uri-conformant", 				true);	// Requires that a URI has to be provided where a URI is expected.
		//saxParserFeatures.put("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef",	true);  // Report a warning if an element referenced in a content model is not declared.
		//saxParserFeatures.put("http://apache.org/xml/features/validation/balance-syntax-trees", 		false);	// No optimize DTD content models.
		//saxParserFeatures.put("http://apache.org/xml/features/validation/unparsed-entity-checking", 	false);	// Do not check that each value of type ENTITY in DTD.
		
		return saxParserFeatures;
	}
	
	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getSAXParserFeaturesRequired()
	 */
	public List<String> getSAXParserFeaturesRequired() {
		List<String> result = new ArrayList<String>(5);
		result.add("http://xml.org/sax/features/use-attributes2");	// Attributes objects passed by the parser are ext.Attributes2 interface.
		result.add("http://xml.org/sax/features/use-locator2");		// Locator objects passed by the parser are org.xml.sax.ext.Locator2 interface.
		result.add("http://xml.org/sax/features/xml-1.1");			// The parser supports both XML 1.0 and XML 1.1.
		return result;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#getX4ODebugWriter()
	 */
	public X4ODebugWriter getX4ODebugWriter() {
		return debugWriter;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#hasX4ODebugWriter()
	 */
	public boolean hasX4ODebugWriter() {
		return debugWriter!=null;
	}

	/**
	 * @see org.x4o.xml.core.config.X4OLanguageConfiguration#setX4ODebugWriter(org.x4o.xml.core.X4ODebugWriter)
	 */
	public void setX4ODebugWriter(X4ODebugWriter debugWriter) {
		this.debugWriter=debugWriter;
	}
}
