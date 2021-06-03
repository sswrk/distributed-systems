@echo off
slice2java --output-dir ../server/generated catalogue.ice
slice2py --output-dir ../client catalogue.ice