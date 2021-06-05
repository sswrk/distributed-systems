import demo


class CalcI(demo.Calc):

    MAX_INT = 2147483647
    MIN_INT = -2147483648

    def add(self, a, b, current):
        result = a + b
        print(str(a) + " + " + str(b) + " = " + str(result))
        if result > self.MAX_INT or result < self.MIN_INT:
            print("Out of bounds!")
            raise demo.OutOfBounds
        return result

    def subtract(self, a, b, current):
        result = a - b
        print(str(a) + " - " + str(b) + " = " + str(result))
        if result > self.MAX_INT or result < self.MIN_INT:
            print("Out of bounds!")
            raise demo.OutOfBounds
        return result

    def op(self, a1, b1, current):
        if a1.d == 'multiply':
            a1.a *= b1
            a1.d = ''
        if a1.a > self.MAX_INT or a1.a < self.MIN_INT:
            print("Out of bounds!")
            raise demo.OutOfBounds
        return a1
