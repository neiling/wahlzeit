/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.webparts;

import org.wahlzeit.utils.PatternInstance;

import java.io.*;

/**
 * A Writable can write a representation of itself to a Writer.
 */
@PatternInstance(
		patternName = "Composite",
		participants = "Component"
)
public interface Writable {
	
	/**
	 * 
	 */
	public void writeOn(Writer writer) throws IOException;
	
}
