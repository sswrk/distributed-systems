import sys

import Ice
from calc_impl import CalcI


if __name__ == '__main__':
    with Ice.initialize(sys.argv) as communicator:
        adapter = communicator.createObjectAdapterWithEndpoints(
            "Adapter2", "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z")
        calc_servant_1 = CalcI()
        adapter.add(calc_servant_1, communicator.stringToIdentity("calc/calc11"))
        adapter.activate()
        print("Entering event processing loop...")
        communicator.waitForShutdown()

