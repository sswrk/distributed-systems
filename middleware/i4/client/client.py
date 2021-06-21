import Ice
import sys

import catalogue


def init_sedan(communicator):
    car_base = communicator.oProxstringTy("car/sedan:default -p 10000")
    return catalogue.CarPrx.checkedCast(car_base)


def init_wagon(communicator):
    car_base = communicator.stringToProxy("car/wagon:default -p 10000")
    return catalogue.CarPrx.checkedCast(car_base)


def init_hatchback(communicator):
    car_base = communicator.stringToProxy("car/hatchback:default -p 10000")
    return catalogue.CarPrx.checkedCast(car_base)


def init_super_car(communicator):
    car_base = communicator.stringToProxy("supercar/supercar:default -p 10000")
    return catalogue.SuperCarPrx.checkedCast(car_base)


def init_offroad_car(communicator):
    car_base = communicator.stringToProxy("offroadcar/offroadcar:default -p 10000")
    return catalogue.OffroadCarPrx.checkedCast(car_base)


def init_electric_car(communicator):
    car_base = communicator.stringToProxy("electriccar/electriccar:default -p 10000")
    return catalogue.ElectricCarPrx.checkedCast(car_base)


def init_motorbike(communicator):
    car_base = communicator.stringToProxy("motorbike/motorbike:default -p 10000")
    return catalogue.MotorbikePrx.checkedCast(car_base)


if __name__ == "__main__":
    with Ice.initialize(sys.argv) as communicator:
        sedan = wagon = hatchback = super_car = offroad_car = electric_car = motorbike = None

        commands = {
            "sedan horsepower": lambda: print(sedan.getHorsepower()),
            "sedan price": lambda: print(sedan.getPrice()),
            "wagon horsepower": lambda: print(wagon.getHorsepower()),
            "wagon price": lambda: print(wagon.getPrice()),
            "hatchback horsepower": lambda: print(hatchback.getHorsepower()),
            "hatchback price": lambda: print(hatchback.getPrice()),
            "supercar horsepower": lambda: print(super_car.getHorsepower()),
            "supercar price": lambda: print(super_car.getPrice()),
            "supercar seats": lambda: print(super_car.getSeats()),
            "supercar fuel": lambda: print(super_car.getFuelUse()),
            "offroadcar horsepower": lambda: print(offroad_car.getHorsepower()),
            "offroadcar price": lambda: print(offroad_car.getPrice()),
            "offroadcar extrawheels": lambda: print(offroad_car.getExtraWheels()),
            "electriccar batteries": lambda: print(electric_car.getBatteries()),
            "electriccar electricity": lambda: print(electric_car.getElectricityUse()),
            "motorbike acceleration": lambda: print(motorbike.getAcceleration()),
            "motorbike wheels": lambda: print(motorbike.getWheels())
        }

        while True:
            command = input(":").strip()

            if command in commands.keys():
                vehicle = command.split(' ', 1)[0]

                if vehicle == 'sedan' and sedan is None:
                    sedan = init_sedan(communicator)
                elif vehicle == 'wagon' and wagon is None:
                    wagon = init_wagon(communicator)
                elif vehicle == 'hatchback' and hatchback is None:
                    hatchback = init_hatchback(communicator)
                elif vehicle == 'supercar' and super_car is None:
                    super_car = init_super_car(communicator)
                elif vehicle == 'offroadcar' and offroad_car is None:
                    offroad_car = init_offroad_car(communicator)
                elif vehicle == 'electriccar' and electric_car is None:
                    electric_car = init_electric_car(communicator)
                elif vehicle == 'motorbike' and motorbike is None:
                    motorbike = init_motorbike(communicator)

                commands[command]()

            else:
                print("Wrong command!")
