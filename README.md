## Part A: RPN Calculator

1. Document all error conditions you determined and why they are error conditions. Do this by including the inputs that 
you used to test your program and what error conditions they exposed:

ERROR 1: Division by Zero for "/". It may be possible that a user enters 0 as the second operator and then calls 
the '/' operation on the 2 operands. In this case a Division by Zero error should be displayed and 
that should remain unchanged, as division by zero gives an undefined answer.
Input Example:
>>10 0 /
>>ERROR: Division by Zero

ERROR 1: Division by Zero for "%". It may be possible that a user enters 0 as the second operator and then calls 
the '%' operation on the 2 operands. In this case a Division by Zero error should be displayed and 
that should remain unchanged, as remainder while dividing by zero gives an undefined answer.
Input Example:
>>10 0 %
>>ERROR: Division by Zero

ERROR 3: Bad Token. In case the user enters anything expect an integer, or the arithmetic operations or
'?', '.', and '!', for example a string like 'blah', a float value like '2.0', an integer out of range, 
or a mixed expression like 'ab457821w=', then a Bad Token error should be displayed. 
Input Example:
>>1000000000000
>>ERROR: bad token
>>bleh
>>ERROR: bad token
>>2.09
>>ERROR: bad token
>>iu3h3e7tf93u0i2 4 +
>>ERROR: bad token

ERROR 4: Not Enough Arguments. An operation requires 2 operands, and so if the user enters an expression that doesn't 
satisfy that, then a Not enough arguments error should be displayed and the stack should remain unchanged.
Input Example:
>>+
>>ERROR: Not enough arguments

ERROR 5: Empty Stack. If a user enters '.' at a point where the stack is empty, then an empty stack error should be 
displayed.
Input Example:
>>.
>>ERROR: Empty Stack

## Part B: Testing Deques

1. Please give a list of the flaws you uncovered in our implementation and list the JUnit 4 tests (using the names 
you gave them) that uncovered each flaw. For each flaw you identify, please share an educated guess as to what is the 
mistake that led to the flaw.
-Flaw 1: Test: BackOnEmptyDequeThrowsException()
               This is a test to check that if the Deque is empty, then if back() is called on it, then it should throw
               an EmptyException(). The back() function in the actual program doesn't check for an empty exception and 
               hence this test fails.
-Flaw 2: Test: RemoveFrontOnEmptyDequeThrowsException()
               This is a test to check that if the Deque is empty, then remove front should throw an exception since
               it has no element to remove. The removeFront() function in the actual program doesn't check for an empty 
               exception and hence this test fails.
-Flaw 3: Test: RemoveBackOnEmptyDequeThrowsException()
               This is a test to check that if the Deque is empty, then remove back should throw an exception since
               it has no element to remove. The removeBack() function in the actual program doesn't check for an empty 
               exception and hence this test fails.
-Flaw 4: Test: testInsertBack()
               This is a test to ensure that insert back works and updates front and back properly. I used the input:
               >>stringDequeue.insertBack("One");
               >>stringDequeue.insertBack("Two");
               >>stringDequeue.insertBack("Three");
               >>stringDequeue.insertBack("Four");
               >>assertEquals("One", stringDequeue.front());
               >>assertEquals("Four",stringDequeue.back());
               This test was failing and on more careful, investigation, I noticed that "One" and "Three get added to 
               the back and "Two" doesn't. So I added another statement:
               >>stringDequeue.insertBack("Five"); 
               Now the dequeue was passing this test, and on debugging it, I realised that it is only inserting every
               alternate element at the back. Just to be sure, I added another statement:
               >>stringDequeue.insertBack("Six");
               But this time it did add Six at the back, and passed the test. So it seemed to be following a random 
               pattern. I kept adding more insertBack() statements till I found out the error, using the Java 
               Visualiser.
               On using the Java visualiser, I found out that  insertBack() works properly till the array has 
               space. The time when the array is full while inserting an element, it grows the dequeue instead of 
               inserting the element. So, for example, it inserted the first element properly.The deque probably starts 
               out with one element. When the second insertBack() is called, the deque is full, so it doubles its 
               capacity and doesn't insert the element. The 3rd insertBack() call inserts that element as the second 
               element in the deque. Then at the fourth insertBack() call, the deque is again full. So it grows the 
               dequeue instead of inserting the element. The deque is probably growing by doubling capacity. Now the 
               dequeue has 4 spaces, 2 out of which are full. So the next 2 insertBack() statements will work. The 
               3rd insertBAck() after these 2 will not work as at this time, it will grow the deque to a capacity of 
               8(i.e. double of 4). At this point, the deque has grown to 8 elements,four of the spaces filled, and 
               four empty. So the next four insertBack() statements will work and the fifth one wont and so on. 
               In conclusion, insertBack() works but not at that particular iteration where the deque grows.
          
2. Discuss the process with which you determined what tests to write. What made this process more difficult?

I started with writing small tests for each operation that can be performed on the dequeue. Since a couple of methods in 
the deque implementation throw exceptions in certain cases, I made sure to test that first. Namely, front(), back(), 
removeFront(), and removeBack() should throw an exception if called on an empty dequeue. On testing, I found out that
only front() is equipped to throw an exception in the case that the dequeue is empty, which means that there is this 
particular fault in back(), removeFront(), and removeBack(). Next, I wanted to check if empty() works properly, and I 
wrote 2 tests for that: newDequeIsEmpty() and DequeNotEmptyAfterInsert(). These 2 tests test both functionalities of 
empty(), to return true when the dequeue is empty, and to return false when the dequeue is not empty. The is also a 
small test to check the length() function which I did by inserting an element and checking if it returns the correct 
length which it does.

front() and back():
Because front() and back() are used extensively to test all other functions, I wanted to make sure that these 2 are 
working properly. I added a simple test where I insert one element at the front or at the back and test if front and 
back both return this one element, because when the deque has only one element, its front() and back() are the same.

Next I wanted to check the four main operations of the deque: insertFront(), insertBack(), removeFront(), and 
removeBack(). 

insertFront() and insertBack():
For this I used simple insertFront() and insertBack() statements, and checked if the front(), and back() results were 
consistent with my commands.I noticed that insertFront() works fine but insertBack() has some kind of bug which is 
discussed above. I made sure to add at-least 4 insertFront() and insertBack() statements in order to make
sure that I didnt miss a bug just because I didnt insert enough elements for the bug to do its job. Since removeFront(), 
and removeBack() rely on insertFront() and insertBack(), it was very important to test whether these 2 were working fine. 
One thing that I mainly wanted to check with the inserts was if it updates the front() and
back() and it updates the numElements/length() properly. So I just used 4 insertFront() and insertBack() statements and 
after each these statements, I checked if the front() and back() were correct and if the length of the dequeue was as 
expected which it was for insertFront(). So insertFront() works fine. insertBack() has evident bugs and so it also 
failed the length() test.

removeFront() and removeBack():
It was slightly difficult to test removeFront() and removeBack() properly. Initially all my removeFront() and 
removeBack() tests were failing because till that point, I has not realised that the real problem is in insertBack().
After I was aware of that, to ensure proper testing of removeFront() and removeBack(), I made sure that insertBack(), 
was doing the correct thing in that particular case, or I tried to avoid using insertBack() at all. I used mostly 
insertFront() to test both removes. What I mainly wanted to check with the removes was if it updates the front() and
back() and it updates the numElements/length() properly. So I just used 2 removeFront() and removeBack() statements and 
after each remove statement, I checked if the front() and back() were correct and if the length of the dequeue was as 
expected which it was, so both functions work fine.

Special Cases:
Since the Dequeue is a circular array implementation in case of an arrayDequeue, I wanted to check certain things that 
could go wrong. The program makes extensive use of front() and back(), and updating these values correctly while 
execution is slightly tricky. front and back both are initialised at index 0, but if we have a couple of insertFront(), 
and removeFront() statements, then front index moves forward without affecting back. In such a case, there may be a
possibility that back is not updated to be after front, which was tested with the
BackGetsUpdatedProperlyWhenFrontMovesBeyondBackWhileEmptyingStackUsingRemoveFront() test which the dequeue passes and 
hence we can conclude that the front and back are getting updated properly.

grow:
Next I wanted to check if the dequeue grows properly in case of array implementation. I wrote this test by first 
debugging many of my tests. While debugging insertBack(), I realised that grow() was working properly but insertBack() 
was not using it properly. Since insertFront() was working properly, I decided to use only insertFront() to test grow. 
I realised that grow was working perfectly fine, doubling the size of the array when needed. 

