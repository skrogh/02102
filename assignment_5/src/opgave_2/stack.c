//=========================================
// stack.c
// Implements a simple integer stack
// Stack maximum size is environment
// specific.
//=========================================
#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

#define STACK_TOO_LARGE "\nInteger stack too large, halting process\n"
#define ALLOCATION_ERROR "\ncannot allocate stack, halting process\n"

/*

Tilføj funktionerne newStack, pop, push, top og empty.

*/

enum { REALLOC_ERROR, MALLOC_ERROR };

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
        int * tmp = realloc( stack_p -> array, 2 * stack_p -> capacity * sizeof ( int ) );

        if ( tmp != NULL ) {
            stack_p -> array = tmp;
        }
        else {
            printf( STACK_TOO_LARGE );
            exit( REALLOC_ERROR );
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
    stack_t * stack_tmp = malloc( sizeof ( int ) );

    stack_t * stack_p = malloc( sizeof ( stack_t ) );

    if ( stack_tmp != NULL ) {
        stack_p = stack_tmp;
    } else {
        printf( ALLOCATION_ERROR);
        exit( MALLOC_ERROR );
    }


    stack_p -> capacity = 1;
    stack_p -> size = 0;
    int * tmp = malloc( sizeof ( int ) );

    if ( tmp != NULL ) {
        stack_p -> array = tmp;
    } else {
        printf( ALLOCATION_ERROR );
        exit( MALLOC_ERROR );
    }

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

