package hw4;

import exceptions.EmptyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlawedDeque226Test {

  private Deque226<String> stringDequeue;

  @Before
  public void setupDequeue() {

    this.stringDequeue = new FlawedDeque226<String>();
  }

  @Test
  public void testLength() {
    assertEquals(0, stringDequeue.length());
    stringDequeue.insertFront("One");
    assertEquals(1, stringDequeue.length());
  }

  @Test
  public void newDequeIsEmpty() {

    assertTrue(stringDequeue.empty());
  }

  @Test(expected = EmptyException.class)
  public void FrontOnEmptyDequeThrowsException() {

    stringDequeue.front();
  }

  @Test(expected = EmptyException.class)
  public void BackOnEmptyDequeThrowsException() {

    stringDequeue.back();
  }

  @Test(expected = EmptyException.class)
  public void RemoveFrontOnEmptyDequeThrowsException() {

    stringDequeue.removeFront();
  }

  @Test(expected = EmptyException.class)
  public void RemoveBackOnEmptyDequeThrowsException() {

    stringDequeue.removeBack();
  }

  @Test
  public void DequeNotEmptyAfterInsert() {
    stringDequeue.insertFront("One");
    assertFalse(stringDequeue.empty());
  }

  @Test
  public void testInsertFront() {
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.insertFront("Three");
    stringDequeue.insertFront("Four");
    assertEquals("Four", stringDequeue.front());
    assertEquals("One", stringDequeue.back());
    assertEquals(4, stringDequeue.length());
  }

  @Test
  public void testInsertBack() {
    stringDequeue.insertBack("One");
    stringDequeue.insertBack("Two");
    stringDequeue.insertBack("Three");
    stringDequeue.insertBack("Four");
    assertEquals("One", stringDequeue.front());
    assertEquals("Four", stringDequeue.back());
    assertEquals(4, stringDequeue.length());
  }

  @Test
  public void BackAndFrontSameIfOneElementInDequeForInsertFront() {
    stringDequeue.insertFront("One");
    assertEquals("One", stringDequeue.front());
    assertEquals("One", stringDequeue.back());
  }

  @Test
  public void BackAndFrontSameIfOneElementInDequeForInsertBack() {
    stringDequeue.insertBack("One");
    assertEquals("One", stringDequeue.front());
    assertEquals("One", stringDequeue.back());
  }

  @Test
  public void testRemoveFront() {
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.removeFront();
    assertEquals(1, stringDequeue.length());
    assertEquals("One", stringDequeue.front());
    assertEquals("One", stringDequeue.back());
    stringDequeue.removeFront();
    assertTrue(stringDequeue.empty());
    assertEquals(0, stringDequeue.length());
  }

  @Test
  public void testRemoveBack() {
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.removeBack();
    assertEquals(1, stringDequeue.length());
    assertEquals("Two", stringDequeue.front());
    assertEquals("Two", stringDequeue.back());
    stringDequeue.removeBack();
    assertTrue(stringDequeue.empty());
    assertEquals(0, stringDequeue.length());
  }

  @Test
  public void removeFrontWorksWhenFrontAndBackAreSame() {
    stringDequeue.insertBack("One");
    stringDequeue.removeFront();
    assertTrue(stringDequeue.empty());
  }

  @Test
  public void removeBackWorksWhenFrontAndBackAreSame() {
    stringDequeue.insertFront("One");
    stringDequeue.removeBack();
    assertTrue(stringDequeue.empty());
  }

  @Test
  public void BackGetsUpdatedProperlyWhenFrontMovesBeyondBackWhileEmptyingStackUsingRemoveFront() {
    stringDequeue.insertFront("One");
    stringDequeue.removeFront();
    assertTrue(stringDequeue.empty());
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.insertFront("Three");
    stringDequeue.insertBack("Four");
    assertEquals("Three", stringDequeue.front());
    assertEquals("Four", stringDequeue.back());
  }

  @Test
  public void BackGetsUpdatedProperlyWhenFrontMovesBeyondBackWhileEmptyingStackUsingRemoveBack() {
    stringDequeue.insertFront("One");
    stringDequeue.removeBack();
    assertTrue(stringDequeue.empty());
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.insertFront("Three");
    stringDequeue.insertBack("Four");
    assertEquals("Three", stringDequeue.front());
    assertEquals("Four", stringDequeue.back());
  }

  @Test
  public void testIfDequeGrowsProperly() {
    stringDequeue.insertFront("One");
    stringDequeue.insertFront("Two");
    stringDequeue.insertFront("Four");
    stringDequeue.insertFront("Five");
    stringDequeue.insertFront("Six");
    stringDequeue.insertFront("Seven");
    assertEquals("One", stringDequeue.back());
    assertEquals("Seven", stringDequeue.front());
  }

}
