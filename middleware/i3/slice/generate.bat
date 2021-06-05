@echo off
slice2py --output-dir ../server calculator.ice
slice2java --output-dir ../client/generated calculator.ice