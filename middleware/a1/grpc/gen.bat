@echo off
protoc.exe -I=. --java_out=../Client/generated --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-1.37.0-windows-x86_64.exe --grpc-java_out=../Client/generated creator_platform.proto
python -m grpc_tools.protoc -I. --python_out=../Server/generated --grpc_python_out=../Server/generated creator_platform.proto