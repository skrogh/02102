// bits.c

#include <stdio.h>

/*

Udskrift:

86 = 01010110_2

*/

int main() {
  int d = 86;
  int b0 = d % 2;
  int b1 = (d / 2) % 2;
  int b2 = (d / 4) % 2;
  int b3 = (d / 8) % 2;
  int b4 = (d / 16) % 2;
  int b5 = (d / 32) % 2;
  int b6 = (d / 64) % 2;
  int b7 = (d / 128) % 2;
  printf("%d = %d%d%d%d%d%d%d%d_2",
      d, b7, b6, b5, b4, b3, b2, b1, b0);
  return 0;
}
