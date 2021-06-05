
#ifndef CALC_ICE
#define CALC_ICE

module demo
{
  enum operation { MIN, MAX, AVG };
  
  exception OutOfBounds {};

  struct A
  {
    int a;
    string d;
  }

  interface Calc
  {
    long add(int a, int b);
    long subtract(int a, int b);
    A op(A a1, int b1);
  };

};

#endif
