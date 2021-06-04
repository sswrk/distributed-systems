import Demo


class CalcI(Demo.Calc):
    def __init__(self):
        self.counter = 0

    def add(self, a, b, current):
        result = a + b
        print(str(a) + " + " + str(b) + " = " + str(result))
        return result

    def subtract(self, a, b, current):
        result = a - b
        print(str(a) + " - " + str(b) + " = " + str(result))
        return result

    def op(self, a1, b1):
        self.counter += 1
