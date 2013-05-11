//=========================================
// stack.c
// Implements a simple integer stack
// Stack maximum size is environment
// specific.
//=========================================
#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

/*

Tilføj funktionerne newStack, pop, push, top og empty.

*/

int pop( stack_t * stack_p ) {
    return stack_p -> array[ --stack_p -> size ];
}

//=======================================
// push
// Pushes an integer to the stack, also
// handles stack resizing.
//=======================================
void push( stack_t * stack_p, int value ) {
    if ( stack_p -> size >= stack_p -> capacity ) {
        stack_t * tmp = realloc( stack_p -> array, 2 * stack_p -> capacity * sizeof ( int ) );

        if ( tmp != NULL )
            stack_p -> array = tmp;
        else {
            printf( "Integer stack too large, halting process" );
            exit(1);
        }

        stack_p -> capacity *= 2;
        stack_p -> array[ stack_p -> size++ ] = value;
    } else {
        stack_p -> array[ stack_p -> size++ ] = value;
    }
}

//============================
// top
// Peeks at the stack.
//============================
int top( stack_t * stack_p ) {
    return stack_p -> array[ stack_p -> size - 1 ];
}

//========================================
// newStack
// Returns pointer to a new stack with
// initial capacity 1.
//=======================================
stack_t * newStack( void ) {
    stack_t * stack_p = malloc( sizeof ( stack_t ) );
    stack_p -> capacity = 1;
    stack_p -> size = 0;
    stack_p -> array = malloc( sizeof ( int ) );

    return stack_p;
}

//==============================
// empty
// Returns 1 if stack_p is empty
// 0 otherwise
//==============================
int empty( stack_t * stack_p ) {
    return ( stack_p -> size ) <= 0;
}

