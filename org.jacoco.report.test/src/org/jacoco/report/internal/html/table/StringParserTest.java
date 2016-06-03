/*******************************************************************************
 * Copyright (c) 2009, 2016 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 *******************************************************************************/
package org.jacoco.report.internal.html.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringParserTest {

	private StringParser parser;

	@Test
	public void hasNext_shouldReturnTrue_whenMoreCharactersAvailable() {
		parser = new StringParser("abc");
		assertTrue(parser.hasNext());
	}

	@Test
	public void hasNext_shouldReturnFalse_whenNoMoreCharactersAvailable() {
		parser = new StringParser("");
		assertFalse(parser.hasNext());
	}

	@Test
	public void isNext_shouldReturnTrueAndConsumeCharacter_whenCharacterMatches() {
		parser = new StringParser("x");
		assertTrue(parser.isNext('x'));
		assertFalse(parser.hasNext());
	}

	@Test
	public void isNext_shouldReturnFalseAndLeaveCharacter_whenNoMatch() {
		parser = new StringParser("x");
		assertFalse(parser.isNext('y'));
		assertTrue(parser.hasNext());
	}

	@Test
	public void isNext_shouldReturnFalse_whenNoMoreCharactersAvailable() {
		parser = new StringParser("");
		assertFalse(parser.isNext('x'));
	}

	@Test
	public void getNext_shouldReturnAndConsumeNextCharacter_whenMoreCharactersAvailable() {
		parser = new StringParser("abc");
		assertEquals('a', parser.getNext());
		assertEquals('b', parser.getNext());
		assertEquals('c', parser.getNext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNext_shouldThrowException_whenNoMoreCharactersAvailable() {
		parser = new StringParser("");
		parser.getNext();
	}

	@Test
	public void expectNext_shouldReturn_whenExpectedCharactedIsPresent() {
		parser = new StringParser("?");
		parser.expectNext('?');
	}

	@Test(expected = IllegalArgumentException.class)
	public void expectNext_shouldReturn_whenExpectedCharactedIsNotPresent() {
		parser = new StringParser("?");
		parser.expectNext('!');
	}

	@Test
	public void read_shouldReturnAllCharactersUntilLimiter_whenLimiterIsProvided() {
		parser = new StringParser("abc>x");
		assertEquals("abc", parser.read('>'));
		assertEquals('x', parser.getNext());
	}

	@Test
	public void read_shouldReadEmptyString_whenLimiterFollowsDirectly() {
		parser = new StringParser(">x");
		assertEquals("", parser.read('>'));
		assertEquals('x', parser.getNext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void read_shouldThrowException_whenLimiterIsMissing() {
		parser = new StringParser("abc");
		parser.read('>');
	}

}
