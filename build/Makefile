all:
	javac -d ../lib ../src/*.java
	javac -cp ../lib -d ../target ../test/*/*

doc:
	javadoc -d ../doc ../src/*

test:
	java -cp ../target:../lib TestCustomTree
	# Add Unit Testing: java -cp ../target:../lib [TestName]

clean:
	rm -f *.class
	rm -f -R ../doc/*
	rm -f -R ../lib/*
	rm -f -R ../target/*
