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
package org.x4o.xml.test;

import org.x4o.xml.X4ODriver;
import org.x4o.xml.io.X4OReader;
import org.x4o.xml.test.models.TestObjectRoot;


import junit.framework.TestCase;

/**
 * Tests a simple x4o xml language.
 * 
 * @author Willem Cazander
 * @version 1.0 Jul 24, 2006
 */
public class TagHandlerTest extends TestCase {
	
	public void setUp() throws Exception {
		//X4OTesting.initLogging();
	}
	
	private void printS(Throwable e) {
		if (e.getCause()==null) {
			e.printStackTrace();
			return;
		}
		printS(e.getCause());
	}

	public void testTagHanders() throws Exception {
		X4ODriver<TestObjectRoot> driver = TestDriver.getInstance();
		X4OReader<TestObjectRoot> reader = driver.createReader();
		/*
		File f = File.createTempFile("test-taghandlers", ".xml");
		f.deleteOnExit();
		reader.setProperty(X4OLanguagePropertyKeys.DEBUG_OUTPUT_STREAM, new FileOutputStream(f));
		try {
			reader.readResource("tests/test-taghandlers.xml");
		} catch (Exception e) {
			printS(e);
		}
		*/
	}
}
