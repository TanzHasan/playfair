.PHONY: run clean
ARGS=

run: playfair.class
	java playfair $(ARGS)

playfair.class:
	javac playfair.java

clean:
	rm *.class
