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
package org.x4o.xml.el;

import java.util.HashMap;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.VariableMapper;

/**
 * X4OELFunctionMapper simple EL context.
 * 
 * @author Willem Cazander
 * @version 1.0 Sep 14, 2010
 */
public class X4OELContext extends ELContext {
	
	private ELResolver elResolver = null;
	private FunctionMapper functionMapper = null;
	private VariableMapper variableMapper = null;
	
	/**
	 * Creates a X4OELContext.
	 */
	public X4OELContext() {
		
		CompositeELResolver compositeELResolver = new CompositeELResolver();
		compositeELResolver.add(new X4OELResolver(new HashMap<Object, Object>(100)));
		compositeELResolver.add(new ArrayELResolver());
		compositeELResolver.add(new ListELResolver());
		compositeELResolver.add(new BeanELResolver());
		compositeELResolver.add(new MapELResolver());
		
		elResolver     = compositeELResolver;
		functionMapper = new X4OELFunctionMapper();
		variableMapper = new X4OELVariableMapper();
	}

	/**
	 * Returns the ELResolver.
	 * @return The ELResolver.
	 * @see javax.el.ELContext#getELResolver()
	 */
	@Override
	public ELResolver getELResolver() {
		return elResolver;
	}

	/**
	 * Returns the FunctionMapper.
	 * @return The FunctionMapper.
	 * @see javax.el.ELContext#getFunctionMapper()
	 */
	@Override
	public FunctionMapper getFunctionMapper() {
		return functionMapper;
	}

	/**
	 * Returns the VariableMapper.
	 * @return The VariableMapper.
	 * @see javax.el.ELContext#getVariableMapper()
	 */
	@Override
	public VariableMapper getVariableMapper() {
		return variableMapper;
	}
}
