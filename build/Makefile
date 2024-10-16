all:
	javac -d ../lib ../src/*.java
	javac -cp ../lib:../sourcelib -d ../target ../test/*

doc:
	javadoc -d ../doc ../src/*

test:
	java -cp ../target:../lib:../sourcelib TestCustomTree

clean:
	rm -f *.class
	rm -rf ../doc/*
	rm -rf ../lib/*
	rm -rf ../target/*
