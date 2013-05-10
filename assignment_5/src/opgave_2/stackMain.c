// stackMain.c

#include <stdio.h>
#include "stack.h"

/*

Udskrift:

popped: 4444
popped: 99
popped: 123

*/

int main() {
  stack_t * myStack = newStack();
  push(myStack, 123);
  push(myStack, 99);
  push(myStack, 4444);
  while (!empty(myStack)) {
    int value;
    value = pop(myStack);
    printf("popped: %d\n", value );
  }
  return 0;
}
