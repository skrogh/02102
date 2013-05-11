// stackMain.c

#include <stdio.h>
#include <time.h>
#include "stack.h"

int main() {
  //create stack for testing
  stack_t * myStack = newStack();
  //push number that will cause overflow
  push( myStack, 1313131313131 );
  //testing vars
  int j = 0;
  srand( time( NULL ) );

  //push 10 random numbers
  for( j = 0; j < 10; j++ ) {
    push( myStack, rand() % 1337 );
  }

  //pop the random numbers and continue popping
  // even though the stack is empty
  for( j = 0; j < 20; j++ ) {
    printf( "popped %d\n", pop(myStack) );
  }
  free( myStack );
  // reset the stack
  myStack = newStack();
  //test peeking at the stack
  push( myStack, 42 );
  printf( "peeking: %d \n", top( myStack ) );

  //Make the stack too large
  while (!empty(myStack)) {
    int i = 0;
    for( i=0; i< 13421772; i++ ) {
        push(myStack, i);
    }

    int value;
    value = pop(myStack);
    printf("popped: %d  %d  %d \n", value, myStack -> size, myStack -> capacity );
  }
  return 0;
}
