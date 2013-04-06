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

package org.x4o.xml.eld;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.x4o.xml.conv.text.ClassConverter;
import org.x4o.xml.core.config.X4OLanguage;
import org.x4o.xml.core.config.X4OLanguageClassLoader;
import org.x4o.xml.core.config.X4OLanguageLocal;
import org.x4o.xml.eld.lang.BeanElement;
import org.x4o.xml.eld.lang.DescriptionElement;
import org.x4o.xml.eld.lang.ElementClassAddParentElement;
import org.x4o.xml.eld.lang.ElementClassAttributeBindingHandler;
import org.x4o.xml.eld.lang.ElementClassBindingHandler;
import org.x4o.xml.eld.lang.ElementInterfaceBindingHandler;
import org.x4o.xml.eld.lang.ElementModuleBindingHandler;
import org.x4o.xml.eld.lang.ElementNamespaceContextBindingHandler;
import org.x4o.xml.eld.lang.ModuleElement;
import org.x4o.xml.element.ElementBindingHandler;
import org.x4o.xml.element.ElementClass;
import org.x4o.xml.element.ElementClassAttribute;
import org.x4o.xml.element.ElementLanguageModule;
import org.x4o.xml.element.ElementLanguageModuleLoader;
import org.x4o.xml.element.ElementLanguageModuleLoaderException;
import org.x4o.xml.element.ElementNamespaceContext;
import org.x4o.xml.element.ElementNamespaceInstanceProvider;
import org.x4o.xml.element.ElementNamespaceInstanceProviderException;
import org.x4o.xml.impl.DefaultElementClass;

/** 
 * EldModuleLoaderCore provides a few basic elements for the core eld x4o language.
 * 
 * @author Willem Cazander
 * @version 1.0 Jan 11, 2009
 */
public class EldModuleLoaderCore implements ElementLanguageModuleLoader {

	private Logger logger = null;
	private static final String PP_CEL_PROVIDER = "cel.x4o.org";
	private static final String PP_CEL_XMLNS = "http://"+PP_CEL_PROVIDER+"/xml/ns/";
	private static final String PP_CEL_XSD_FILE = "-1.0.xsd";
	private static final String CEL_CORE = "cel-core";
	private static final String CEL_ROOT = "cel-root";
	private static final String CEL_CORE_URI = PP_CEL_XMLNS+CEL_CORE;
	private static final String CEL_ROOT_URI = PP_CEL_XMLNS+CEL_ROOT;
	private static final String CEL_CORE_XSD_URI = CEL_CORE_URI+PP_CEL_XSD_FILE;
	private static final String CEL_ROOT_XSD_URI = CEL_ROOT_URI+PP_CEL_XSD_FILE;
	private static final String CEL_CORE_XSD_FILE = CEL_CORE+PP_CEL_XSD_FILE;
	private static final String CEL_ROOT_XSD_FILE = CEL_ROOT+PP_CEL_XSD_FILE;
	
	/**
	 * Creates the CEL module loader.
	 */
	public EldModuleLoaderCore() {
		logger = Logger.getLogger(EldModuleLoaderCore.class.getName());
	}
	
	/**
	 * Loads the CEL language into the module.
	 * @param elementLanguage The langauge to load for.
	 * @param elementLanguageModule The module to load it in.
	 * @see org.x4o.xml.element.ElementLanguageModuleLoader#loadLanguageModule(org.x4o.xml.element.ElementLanguage, org.x4o.xml.element.ElementLanguageModule)
	 */
	public void loadLanguageModule(X4OLanguageLocal language,ElementLanguageModule elementLanguageModule) throws ElementLanguageModuleLoaderException {
		
		elementLanguageModule.setId("cel-module");
		elementLanguageModule.setName("Core Element Languag Module");
		elementLanguageModule.setProviderName(PP_CEL_PROVIDER);
		
		List<ElementClass> elementClassList = new ArrayList<ElementClass>(10);
		elementClassList.add(new DefaultElementClass("attribute",language.getLanguageConfiguration().getDefaultElementClassAttribute()));
		elementClassList.add(new DefaultElementClass("classConverter",ClassConverter.class));
		
		createElementClasses(elementClassList,language); // adds all meta info
		
		ElementClassAttribute attr;
		
		DefaultElementClass ns = new DefaultElementClass("namespace",language.getLanguageConfiguration().getDefaultElementNamespaceContext());
		attr = newElementClassAttribute(language);
		attr.setName("uri");
		attr.setRequired(true);
		ns.addElementClassAttribute(attr);
		elementClassList.add(ns);
		
		DefaultElementClass dec = new DefaultElementClass("element",language.getLanguageConfiguration().getDefaultElementClass());
		attr = newElementClassAttribute(language);
		attr.setName("objectClass");
		attr.setObjectConverter(new ClassConverter());
		dec.addElementClassAttribute(attr);
		
		attr = newElementClassAttribute(language);
		attr.setName("elementClass");
		attr.setObjectConverter(new ClassConverter());
		dec.addElementClassAttribute(attr);
		elementClassList.add(dec);
		
		DefaultElementClass ec = new DefaultElementClass("elementInterface",language.getLanguageConfiguration().getDefaultElementInterface());
		attr = newElementClassAttribute(language);
		attr.setName("interfaceClass");
		attr.setObjectConverter(new ClassConverter());
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
		
		logger.finer("Creating eldcore namespace.");
		ElementNamespaceContext namespace;
		try {
			namespace = (ElementNamespaceContext)X4OLanguageClassLoader.newInstance(language.getLanguageConfiguration().getDefaultElementNamespaceContext());
		} catch (InstantiationException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} 
		try {
			namespace.setElementNamespaceInstanceProvider((ElementNamespaceInstanceProvider)
				X4OLanguageClassLoader.newInstance(language.getLanguageConfiguration().getDefaultElementNamespaceInstanceProvider())
				);
		} catch (InstantiationException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} 
		
		namespace.setId(CEL_CORE);
		namespace.setUri(CEL_CORE_URI);
		namespace.setSchemaUri(CEL_CORE_XSD_URI);
		namespace.setSchemaResource(CEL_CORE_XSD_FILE);
		namespace.setSchemaPrefix(CEL_CORE);
		
		logger.finer("Loading elementClassList size: "+elementClassList.size());
		for (ElementClass ecL:elementClassList) {
			namespace.addElementClass(ecL);
		}
		
		addBindingHandler("cel-class-bind",new ElementClassBindingHandler(),elementLanguageModule);
		addBindingHandler("cel-module-bind",new ElementModuleBindingHandler(),elementLanguageModule);
		addBindingHandler("cel-class-attr-bind",new ElementClassAttributeBindingHandler(),elementLanguageModule);
		addBindingHandler("cel-interface-bind",new ElementInterfaceBindingHandler(),elementLanguageModule);
		addBindingHandler("cel-namespace-bind",new ElementNamespaceContextBindingHandler(),elementLanguageModule);
		
		try {
			namespace.getElementNamespaceInstanceProvider().start(language, namespace);
		} catch (ElementNamespaceInstanceProviderException e) {
			throw new ElementLanguageModuleLoaderException(this,"Error starting instance provider: "+e.getMessage(),e);
		}
		
		elementLanguageModule.addElementNamespaceContext(namespace);
		
		
		// And define root 
		try {
			namespace = (ElementNamespaceContext)X4OLanguageClassLoader.newInstance(language.getLanguageConfiguration().getDefaultElementNamespaceContext());
		} catch (InstantiationException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} 
		try {
			namespace.setElementNamespaceInstanceProvider((ElementNamespaceInstanceProvider)
				X4OLanguageClassLoader.newInstance(language.getLanguageConfiguration().getDefaultElementNamespaceInstanceProvider())
				);
		} catch (InstantiationException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} 
		
		namespace.setId(CEL_ROOT);
		namespace.setUri(CEL_ROOT_URI);
		namespace.setSchemaUri(CEL_ROOT_XSD_URI);
		namespace.setSchemaResource(CEL_ROOT_XSD_FILE);
		namespace.setSchemaPrefix(CEL_ROOT);
		namespace.addElementClass(new DefaultElementClass("module",language.getLanguageConfiguration().getDefaultElementLanguageModule(),ModuleElement.class));
		namespace.setLanguageRoot(true); // Only define single language root so xsd is (mostly) not cicle import. 
		try {
			namespace.getElementNamespaceInstanceProvider().start(language, namespace);
		} catch (ElementNamespaceInstanceProviderException e) {
			throw new ElementLanguageModuleLoaderException(this,"Error starting instance provider: "+e.getMessage(),e);
		}
		elementLanguageModule.addElementNamespaceContext(namespace);
	}
	
	/**
	 * Adds only Element class beans which need extra meta info for schema.
	 * 
	 * @param elementClassList	The list to fill.
	 * @throws ElementLanguageModuleLoaderException 
	 */
	private void createElementClasses(List<ElementClass> elementClassList,X4OLanguage language) throws ElementLanguageModuleLoaderException {
		ElementClass ec = null;
		ElementClassAttribute attr = null;
		
		ec = new DefaultElementClass("bindingHandler",null,BeanElement.class);
		ec.addElementParent(CEL_ROOT_URI, "module");
		ec.addElementParent(CEL_CORE_URI, "elementInterface");
		attr = newElementClassAttribute(language);
		attr.setName("id");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		attr = newElementClassAttribute(language);
		attr.setName("bean.class");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
		
		ec = new DefaultElementClass("attributeHandler",null,BeanElement.class);
		ec.addElementParent(CEL_ROOT_URI, "module");
		attr = newElementClassAttribute(language);
		attr.setName("bean.class");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
		
		ec = new DefaultElementClass("configurator",null,BeanElement.class);
		ec.addElementParent(CEL_CORE_URI, "elementInterface");
		ec.addElementParent(CEL_CORE_URI, "element");
		attr = newElementClassAttribute(language);
		attr.setName("bean.class");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		attr = newElementClassAttribute(language);
		attr.setName("configAction");
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
		
		ec = new DefaultElementClass("configuratorGlobal",null,BeanElement.class);
		ec.addElementParent(CEL_ROOT_URI, "module");
		attr = newElementClassAttribute(language);
		attr.setName("bean.class");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		attr = newElementClassAttribute(language);
		attr.setName("configAction");
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
		
		ec = new DefaultElementClass("description",null,DescriptionElement.class);
		ec.setSchemaContentBase("string");
		ec.addElementParent(CEL_ROOT_URI, "module");
		ec.addElementParent(CEL_CORE_URI, "namespace");
		ec.addElementParent(CEL_CORE_URI, "attributeHandler");
		ec.addElementParent(CEL_CORE_URI, "bindingHandler");
		ec.addElementParent(CEL_CORE_URI, "configurator");
		ec.addElementParent(CEL_CORE_URI, "configuratorGlobal");
		ec.addElementParent(CEL_CORE_URI, "elementInterface");
		ec.addElementParent(CEL_CORE_URI, "element");
		ec.addElementParent(CEL_CORE_URI, "attribute");
		elementClassList.add(ec);
		
		ec = new DefaultElementClass("elementParent",null,ElementClassAddParentElement.class);
		ec.addElementParent(CEL_CORE_URI, "element");
		ec.addElementParent(CEL_CORE_URI, "elementInterface");
		attr = newElementClassAttribute(language);
		attr.setName("tag");
		attr.setRequired(true);
		ec.addElementClassAttribute(attr);
		attr = newElementClassAttribute(language);
		attr.setName("uri");
		ec.addElementClassAttribute(attr);
		elementClassList.add(ec);
	}
	
	/**
	 * Creates new ElementClassAttribute instance.
	 * @param elementLanguage	The ElementLanguage to create from.
	 * @return	The new ElementClassAttribute instance.
	 * @throws ElementLanguageModuleLoaderException	When class could not be created.
	 */
	private ElementClassAttribute newElementClassAttribute(X4OLanguage language) throws ElementLanguageModuleLoaderException {
		try {
			return (ElementClassAttribute)X4OLanguageClassLoader.newInstance(language.getLanguageConfiguration().getDefaultElementClassAttribute());
		} catch (InstantiationException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new ElementLanguageModuleLoaderException(this,e.getMessage(),e);
		}
	}
	
	/**
	 * Adds binding handler to module.
	 * @param id The id to set on the handler.
	 * @param handler The handler to add the the module.
	 * @param elementLanguageModule	The module to add the handler to.
	 */
	private void addBindingHandler(String id,ElementBindingHandler handler,ElementLanguageModule elementLanguageModule) {
		handler.setId(id);
		elementLanguageModule.addElementBindingHandler(handler);
	}
}
