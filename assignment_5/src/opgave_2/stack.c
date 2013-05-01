// stack.c

#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

/*

Tilføj funktionerne newStack, pop, push, top og empty.

*/

int pop( stack_t * stack_p ) {
    return stack_p -> array[ --stack_p -> size ];
}

void push( stack_t * stack_p, int value ) {
    if ( stack_p -> size >= stack_p -> capacity ) {
        stack_p -> array = realloc( stack_p -> array, 2 * stack_p -> capacity * sizeof ( int ) );

        stack_p -> capacity *= 2;
        stack_p -> array[ stack_p -> size++ ] = value;
    } else {
        stack_p -> array[ stack_p -> size++ ] = value;
    }
}

int top( stack_t * stack_p ) {
    return stack_p -> array[ stack_p -> size - 1 ];
}

stack_t * newStack( void ) {
    stack_t * stack_p = malloc( sizeof ( stack_t ) );
    stack_p -> capacity = 1;
    stack_p -> size = 0;
    stack_p -> array = malloc( sizeof ( int ) );

    return stack_p;
}

int empty( stack_t * stack_p ) {
    return ( stack_p -> size ) <= 0;
}

