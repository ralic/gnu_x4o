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
 * Provides an Interface to configure Element(Object).
 * 
 * 
 * @author Willem Cazanders
 * @version 1.0 Jan 18, 2007
 */
public interface ElementConfigurator extends ElementMetaBase {
	
	/**
	 * Gets called for configuring the given Element.
	 * @param element	The element to config.
	 * @throws ElementConfiguratorException	Is thrown which error is done.
	 */
	void doConfigElement(Element element) throws ElementConfiguratorException;
	
	/**
	 * Return if this ElementConfigurator is an Action.
	 * which means is is executed in the runPhase in the end.
	 * So all magic is done, and we can access the fully finnisched object and toss it around.
	 * @return	Returns true if need to run in config phase.
	 */
	boolean isConfigAction();
}
