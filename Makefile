all: run

clean:
	rm -f out/Main.jar out/KaratsubaParallel.jar

out/Main.jar: out/parcs.jar src/Main.java src/KaratsubaParallel.java src/Karatsuba.java
	@javac -cp out/parcs.jar src/Main.java src/KaratsubaParallel.java src/Karatsuba.java
	@jar cf out/Main.jar -C src Main.class -C src KaratsubaParallel.class -C src Karatsuba.class
	@rm -f src/Main.class src/KaratsubaParallel.class src/Karatsuba.class

out/KaratsubaParallel.jar: out/parcs.jar src/KaratsubaParallel.java src/Karatsuba.java
	@javac -cp out/parcs.jar src/KaratsubaParallel.java src/Karatsuba.java
	@jar cf out/KaratsubaParallel.jar -C src KaratsubaParallel.class -C src Karatsuba.class
	@rm -f src/KaratsubaParallel.class src/Karatsuba.class

build: out/Main.jar out/KaratsubaParallel.jar

run: out/Main.jar out/KaratsubaParallel.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main