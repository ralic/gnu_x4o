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
package	org.x4o.xml.eld.lang;

import org.x4o.xml.element.AbstractElementBindingHandler;
import org.x4o.xml.element.Element;
import org.x4o.xml.element.ElementBindingHandlerException;
import org.x4o.xml.element.ElementClass;
import org.x4o.xml.element.ElementNamespace;
import org.x4o.xml.element.ElementNamespaceAttribute;

/**
 * ElementNamespaceBindingHandler binds ElementClass into namespace.
 * 
 * @author Willem Cazander
 * @version 1.0 Aug 21, 2012
 */
public class ElementNamespaceBindingHandler extends AbstractElementBindingHandler<ElementNamespace> {

	private final static Class<?>[] CLASSES_CHILD = new Class[] {
		ElementClass.class,
		ElementNamespaceAttribute.class
	};
	
	/**
	 * @see org.x4o.xml.element.ElementBindingHandler#getBindParentClass()
	 */
	public Class<?> getBindParentClass() {
		return ElementNamespace.class;
	}

	/**
	 * @see org.x4o.xml.element.ElementBindingHandler#getBindChildClasses()
	 */
	public Class<?>[] getBindChildClasses() {
		return CLASSES_CHILD;
	}

	/**
	 * @see org.x4o.xml.element.AbstractElementBindingHandler#bindChild(org.x4o.xml.element.Element, java.lang.Object, java.lang.Object)
	 */
	public void bindChild(Element childElement,ElementNamespace parent, Object childObject) throws ElementBindingHandlerException {
		if (childObject instanceof ElementClass) {
			ElementClass elementClass = (ElementClass)childObject;
			if (elementClass.getId()==null && elementClass.getObjectClass()!=null) {
				elementClass.setId(elementClass.getObjectClass().getSimpleName()); // TODO: move to defaults layer
			}
			parent.addElementClass(elementClass);
		}
		if (childObject instanceof ElementNamespaceAttribute) {
			ElementNamespaceAttribute elementNamespaceAttribute = (ElementNamespaceAttribute)childObject;
			parent.addElementNamespaceAttribute(elementNamespaceAttribute);
			return;
		}
	}
	
	/**
	 * @see org.x4o.xml.element.AbstractElementBindingHandler#createChilderen(org.x4o.xml.element.Element, java.lang.Object)
	 */
	public void createChilderen(Element parentElement,ElementNamespace parent) throws ElementBindingHandlerException {
		for (ElementClass child:parent.getElementClasses()) {
			createChild(parentElement, child);
		}
		for (ElementNamespaceAttribute child:parent.getElementNamespaceAttributes()) {
			createChild(parentElement, child);
		}
	}
}
