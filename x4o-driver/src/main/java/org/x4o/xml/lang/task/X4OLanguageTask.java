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
package	org.x4o.xml.lang.task;

import org.x4o.xml.io.sax.ext.PropertyConfig;

/**
 * X4OLanguageTask runs a task for a language.
 * 
 * @author Willem Cazander
 * @version 1.0 Aug 24, 2013
 */
public interface X4OLanguageTask {

	/**
	 * @return	Returns the task id.
	 */
	String getId();
	
	/**
	 * @return	Returns the task name.
	 */
	String getName();
	
	/**
	 * @return	Returns the task description.
	 */
	String getDescription();
	
	/**
	 * @return	Returns newly created PropertyConfig for configuring this task. 
	 */
	PropertyConfig createTaskConfig();
	
	/**
	 * @param config	The config with which the task will be runned.
	 * @return	The task executor for running the task.
	 */
	X4OLanguageTaskExecutor createTaskExecutor(PropertyConfig config);
}
