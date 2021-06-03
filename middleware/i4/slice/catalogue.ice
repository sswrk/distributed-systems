#ifndef CATALOGUE_ICE
#define CATALOGUE_ICE

module catalogue
{

  interface Car
  {
    idempotent float getHorsepower();
	idempotent float getPrice();
  };

  interface SuperCar extends Car
  {
    idempotent int getSeats();
	idempotent float getFuelUse();
  };

  interface OffroadCar extends Car
  {
    idempotent int getExtraWheels();
  };

  interface ElectricCar
  {
    idempotent int getBatteries();
	idempotent float getElectricityUse();
  };

  interface Motorbike
  {
    idempotent float getAcceleration();
    idempotent int getWheels();
  };

};

#endif