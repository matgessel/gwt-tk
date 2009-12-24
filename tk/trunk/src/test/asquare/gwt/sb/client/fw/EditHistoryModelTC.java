/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package asquare.gwt.sb.client.fw;

import java.util.EmptyStackException;
import java.util.List;

import asquare.gwt.sb.client.util.UndoableCommand;
import asquare.gwt.sb.client.util.UndoableCommandStub;

import com.google.gwt.junit.client.GWTTestCase;

public class EditHistoryModelTC extends GWTTestCase
{
	private EditHistoryModel m_list;
	private UndoableCommandStub m_a;
	private UndoableCommandStub m_b;
	private UndoableCommandStub m_c;
	private UndoableCommandStub m_d;
	
    @Override
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_list = new EditHistoryModel();
		m_a = new UndoableCommandStub("a");
		m_b = new UndoableCommandStub("b");
		m_c = new UndoableCommandStub("c");
		m_d = new UndoableCommandStub("d");
	}
	
	public void testIsValid()
	{
		setupImpl();
		
		assertFalse(m_list.isValid(-1));
		assertFalse(m_list.isValid(0));
		assertFalse(m_list.isValid(1));
		
		m_list.push(m_a);
		assertFalse(m_list.isValid(-2));
		assertTrue(m_list.isValid(-1));
		assertFalse(m_list.isValid(1));
		assertFalse(m_list.isValid(2));
		
		m_list.push(m_b);
		assertTrue(m_list.isValid(-2));
		assertTrue(m_list.isValid(-1));
		assertFalse(m_list.isValid(1));
		
		m_list.undo();
		assertFalse(m_list.isValid(-2));
		assertTrue(m_list.isValid(-1));
		assertTrue(m_list.isValid(1));
		assertFalse(m_list.isValid(2));
	}
	
	public void testPush()
	{
		setupImpl();
		
		// basics
		m_list.push(m_a);
		assertEquals(m_a, m_list.get(-1));
		m_list.push(m_b);
		assertEquals(m_b, m_list.get(-1));
		assertEquals(m_a, m_list.get(-2));
		
		// clears redo stack
		m_list.undo();
		m_list.push(m_c);
		assertEquals(m_c, m_list.get(-1));
		assertNull(m_list.get(1));

		// max size
		m_list = new EditHistoryModel(2);
		m_list.push(m_a);
		m_list.push(m_b);
		m_list.push(m_c);
		m_list.push(m_d);
		assertEquals(m_d, m_list.get(-1));
		assertEquals(m_c, m_list.get(-2));
		assertEquals(null, m_list.get(-3));
		assertEquals(null, m_list.get(-4));
	}
	
	public void testUndo()
	{
		setupImpl();
		
		// empty stack
		try
		{
			m_list.undo();
			fail();
		}
		catch (EmptyStackException e)
		{
			// EXPECTED
		}
		
		// basic test
		m_list.push(m_a);
		m_list.undo();
		assertEquals(m_a, m_list.get(1));
		m_list.push(m_b);
		m_list.undo();
		assertEquals(m_b, m_list.get(1));
		
		// multiple undo
		m_list.push(m_c);
		m_list.push(m_d);
		m_list.undo();
		m_list.undo();
		assertEquals(m_d, m_list.get(1));
		assertEquals(m_c, m_list.get(2));
	}
	
	public void testRedo()
	{
		setupImpl();
		
		// empty stack
		try
		{
			m_list.redo();
			fail();
		}
		catch (EmptyStackException e)
		{
			// EXPECTED
		}
		
		// basic test
		m_list.push(m_a);
		m_list.undo();
		m_list.redo();
		assertEquals(m_a, m_list.get(-1));
		
		// multiple redo
		m_list.push(m_b);
		m_list.push(m_c); // abc|
		m_list.undo();
		m_list.undo(); // a|bc
		m_list.redo(); // ab|c
		m_list.undo(); // a|bc
		m_list.undo(); // |abc
		m_list.redo(); // a|bc
		m_list.redo(); // ab|c
		m_list.redo(); // abc|
		assertEquals(m_a, m_list.get(-3));
		assertEquals(m_b, m_list.get(-2));
		assertEquals(m_c, m_list.get(-1));
	}
	
//	public void testUndoTo()
//	{
//		setupImpl();
//		
//		// invalid command
//		try
//		{
//			m_list.undoTo(m_a);
//			fail();
//		}
//		catch (IllegalArgumentException e)
//		{
//			// EXPECTED
//		}
//		
//		// single item
//		m_list.push(m_a);
//		m_list.undoTo(m_a);
//		assertEquals(m_a, m_list.get(1));
//		
//		// basic test
//		m_list.push(m_b);
//		m_list.undo();
//		assertEquals(m_b, m_list.get(1));
//		
//		// multiple undo
//		m_list.push(m_c);
//		m_list.push(m_d);
//		m_list.undo();
//		m_list.undo();
//		assertEquals(m_d, m_list.get(1));
//		assertEquals(m_c, m_list.get(2));
//	}
	
	public void testHasUndo()
	{
		setupImpl();
		
		// empty stack
		assertFalse(m_list.hasUndo());
		
		// basic test
		m_list.push(m_a);
		assertTrue(m_list.hasUndo());
		
		// after undo
		m_list.undo();
		assertFalse(m_list.hasUndo());
		
		// after push
		m_list.push(m_b);
		assertTrue(m_list.hasUndo());
		m_list.undo();
		assertFalse(m_list.hasUndo());
	}
	
	public void testHasRedo()
	{
		setupImpl();
		
		// empty stack
		assertFalse(m_list.hasRedo());
		
		// basic test
		m_list.push(m_a);
		assertFalse(m_list.hasRedo());
		m_list.undo();
		assertTrue(m_list.hasRedo());
		
		// after push
		m_list.push(m_b);
		assertFalse(m_list.hasRedo());
		m_list.undo();
		assertTrue(m_list.hasRedo());
	}
	
	public void testPeekUndo()
	{
		setupImpl();
		
		// empty stack		
		try
		{
			m_list.peekUndo();
			fail();
		}
		catch (EmptyStackException e)
		{
			// EXPECTED
		}
		
		// basic test
		m_list.push(m_a);
		assertEquals(m_a, m_list.peekUndo());
		
		// multiple pushes
		m_list.push(m_b);
		assertEquals(m_b, m_list.peekUndo());
		
		// after undo
		m_list.undo();
		assertEquals(m_a, m_list.peekUndo());
		
		// after redo
		m_list.redo();
		assertEquals(m_b, m_list.peekUndo());
	}
	
	public void testPeekRedo()
	{
		setupImpl();
		
		// empty stack
		try
		{
			m_list.peekRedo();
			fail();
		}
		catch (EmptyStackException e)
		{
			// EXPECTED
		}
		
		// basic test
		m_list.push(m_a);
		m_list.undo();
		assertEquals(m_a, m_list.peekRedo());
		
		// multiple undo
		m_list.push(m_b);
		m_list.push(m_c);
		m_list.undo();
		assertEquals(m_c, m_list.peekRedo());
		m_list.undo();
		assertEquals(m_b, m_list.peekRedo());
		
		// after redo
		m_list.redo();
		assertEquals(m_c, m_list.peekRedo());
	}
	
	public void testGetSetMaxSize()
	{
		setupImpl();
		
		// initial value
		assertEquals(EditHistoryModel.MAXSIZE_DEFAULT, m_list.getMaxSize());
		
		// basic test
		m_list.setMaxSize(25);
		assertEquals(25, m_list.getMaxSize());
		
		// 0
		m_list.setMaxSize(0);
		assertEquals(0, m_list.getMaxSize());
		
		// bounds
		m_list.setMaxSize(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, m_list.getMaxSize());
		try
		{
			m_list.setMaxSize(-1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		try
		{
			m_list.setMaxSize(Integer.MIN_VALUE);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// truncation
		m_list.setMaxSize(50);
		m_list.push(m_a);
		m_list.push(m_b);
		m_list.push(m_c);
		m_list.push(m_d);
		m_list.setMaxSize(0);
		assertEquals(m_d, m_list.get(-1));
		assertEquals(m_c, m_list.get(-2));
		assertEquals(m_b, m_list.get(-3));
		assertEquals(m_a, m_list.get(-4));
		m_list.setMaxSize(2);
		assertEquals(m_d, m_list.get(-1));
		assertEquals(m_c, m_list.get(-2));
	}
	
	public void testNotifyListeners()
	{
		setupImpl();
		
		HistoryListenerStub l1 = new HistoryListenerStub();
		m_list.addListener(l1);
		
		// push
		l1.init();
		m_list.push(m_a);
		assertFalse(l1.m_changed);
		m_list.update();
		assertTrue(l1.m_changed);
		
		// undo
		l1.init();
		m_list.undo();
		assertFalse(l1.m_changed);
		m_list.update();
		assertTrue(l1.m_changed);
		
		// redo
		l1.init();
		m_list.redo();
		assertFalse(l1.m_changed);
		m_list.update();
		assertTrue(l1.m_changed);
		
		// set max size (increase)
		m_list.push(m_b);
		l1.init();
		m_list.setMaxSize(100);
		m_list.update();
		assertTrue(l1.m_changed);
		
		// set max size (clip)
		m_list.push(m_b);
		l1.init();
		m_list.setMaxSize(1);
		assertFalse(l1.m_changed);
		m_list.update();
		assertTrue(l1.m_changed);
		
		// remove listener
		m_list.removeListener(l1);
		l1.init();
		m_list.undo();
		m_list.update();
		assertFalse(l1.m_changed);
	}
	
	public void testUpdate()
	{
		setupImpl();
		
		HistoryListenerStub l1 = new HistoryListenerStub();
		
		// no listeners
		m_list.push(m_a);
		m_list.update();
		
		// no changes
		m_list.addListener(l1);
		m_list.update();
		assertFalse(l1.m_changed);
		
		// changed but not updated
		m_list.push(m_b);
		assertFalse(l1.m_changed);
		
		// changed & updated
		m_list.update();
		assertTrue(l1.m_changed);
	}
	
	public void testGetUndoItems()
	{
		setupImpl();
		
		// empty
		assertListEquals(new Object[] {}, m_list.getUndoItems());
		
		// one item
		m_list.push(m_a);
		assertListEquals(new Object[] {m_a}, m_list.getUndoItems());
		
		// correct ordering
		m_list.push(m_b);
		m_list.push(m_c);
		assertListEquals(new Object[] {m_c, m_b, m_a}, m_list.getUndoItems());
		
//		// iterator immutability
//		try
//		{
//			Iterator iter = m_list.getUndoIterator();
//			iter.next();
//			iter.remove();
//			fail("Iterator should be immutable");
//		}
//		catch (UnsupportedOperationException e)
//		{
//			// EXPECTED
//		}
	}
	
	public void testGetRedoItems()
	{
		setupImpl();
		
		// empty
		assertListEquals(new Object[] {}, m_list.getRedoItems());
		
		// one item
		m_list.push(m_a);
		m_list.undo();
		assertListEquals(new Object[] {m_a}, m_list.getRedoItems());
		
		// correct ordering
		m_list.push(m_a);
		m_list.push(m_b);
		m_list.push(m_c);
		m_list.undo();
		m_list.undo();
		m_list.undo();
		assertListEquals(new Object[] {m_a, m_b, m_c}, m_list.getRedoItems());
		
//		// iterator immutability
//		try
//		{
//			Iterator iter = m_list.getRedoItems();
//			iter.next();
//			iter.remove();
//			fail("Iterator should be immutable");
//		}
//		catch (UnsupportedOperationException e)
//		{
//			// EXPECTED
//		}
	}
	
	private void assertListEquals(Object[] items, List<UndoableCommand> list)
	{
		assertEquals(items.length, list.size());
		for (int i = 0; i < items.length; i++)
		{
			assertEquals(items[i], list.get(i));
		}
	}
	
	private static class HistoryListenerStub implements EditHistoryListener
	{
		public boolean m_changed = false;
//		public boolean m_redoPosted = false;
//		public boolean m_undoPosted = false;
		
		public void init()
		{
			m_changed = false;
//			m_redoPosted = false;
//			m_undoPosted = false;
		}
		
		public void updateHistory(EditHistoryModel model)
		{
			m_changed = true;
		}
//
//		public void redoPosted(EditHistoryModel model)
//		{
//			m_redoPosted = true;
//		}
//
//		public void undoPosted(EditHistoryModel model)
//		{
//			m_undoPosted = true;
//		}
	}
}
