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

package org.x4o.xml.eld.xsd;

import java.io.File;
import java.io.FileOutputStream;

import org.x4o.xml.element.ElementClass;
import org.x4o.xml.element.ElementException;
import org.x4o.xml.element.ElementNamespaceContext;
import org.x4o.xml.io.sax.XMLWriter;
import org.x4o.xml.lang.X4OLanguageModule;
import org.x4o.xml.lang.X4OLanguage;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * EldSchemaGenerator Creates XML Schema for a namespace uri from a x4o language driver. 
 * 
 * @author Willem Cazander
 * @version 1.0 Aug 8, 2012
 */
public class EldXsdXmlGenerator {

	private X4OLanguage language = null;
	
	public EldXsdXmlGenerator(X4OLanguage language) {
		this.language=language;
		
	}
	
	private void checkNamespace(ElementNamespaceContext ns) {
		if (ns.getSchemaResource()==null) {
			throw new NullPointerException("Can't generate xsd for namespace without schemaResource uri: "+ns.getUri());
		}
		if (ns.getSchemaResource().length()==0) {
			throw new NullPointerException("Can't generate xsd for namespace with empty schemaResource uri: "+ns.getUri());
		}
	}
	
	public void writeSchema(File basePath,String namespace) throws ElementException {
		try {
			if (namespace!=null) {
				ElementNamespaceContext ns = language.findElementNamespaceContext(namespace);
				if (ns==null) {
					throw new NullPointerException("Could not find namespace: "+namespace);
				}
				checkNamespace(ns);
				FileOutputStream fio = new FileOutputStream(new File(basePath.getAbsolutePath()+File.separatorChar+ns.getSchemaResource()));
				try {
					XMLWriter out = new XMLWriter(fio);
					generateSchema(ns.getUri(), out);
				} finally {
					fio.close();
				}	
				return;
			}
			for (X4OLanguageModule mod:language.getLanguageModules()) {
				for (ElementNamespaceContext ns:mod.getElementNamespaceContexts()) {
					checkNamespace(ns);
					FileOutputStream fio = new FileOutputStream(new File(basePath.getAbsolutePath()+File.separatorChar+ns.getSchemaResource()));
					try {
						XMLWriter out = new XMLWriter(fio);
						generateSchema(ns.getUri(), out);
					} finally {
						fio.close();
					}
				}
			}
			
		} catch (Exception e) {
			throw new ElementException(e); // todo rm 
		}
	}
	
	public void generateSchema(String namespaceUri,DefaultHandler2 xmlWriter) throws SAXException  {
		
		ElementNamespaceContext ns = language.findElementNamespaceContext(namespaceUri);
		if (ns==null) {
			throw new NullPointerException("Could not find namespace: "+namespaceUri);
		}
		
		EldXsdXmlWriter xsdWriter = new EldXsdXmlWriter(xmlWriter,language);
		xsdWriter.startNamespaces(namespaceUri);
		xsdWriter.startSchema(ns);
		for (ElementClass ec:ns.getElementClasses()) {
			xsdWriter.writeElementClass(ec,ns);
		}
		for (ElementClass ec:ns.getElementClasses()) {
			xsdWriter.writeElement(ec,ns);
		}
		xsdWriter.endSchema();
	}
}