all: run

clean:
	rm -f out/Main.jar out/MarchingParallel.jar

out/Main.jar: out/parcs.jar src/Main.java src/MarchingParallel.java src/Marching.java
	@javac -cp out/parcs.jar src/Main.java src/MarchingParallel.java src/Marching.java
	@jar cf out/Main.jar -C src Main.class -C src MarchingParallel.class -C src Marching.class
	@rm -f src/Main.class src/MarchingParallel.class src/Marching.class

out/MarchingParallel.jar: out/parcs.jar src/MarchingParallel.java src/Marching.java
	@javac -cp out/parcs.jar src/MarchingParallel.java src/Marching.java
	@jar cf out/MarchingParallel.jar -C src MarchingParallel.class -C src Marching.class
	@rm -f src/MarchingParallel.class src/Marching.class

build: out/Main.jar out/MarchingParallel.jar

run: out/Main.jar out/MarchingParallel.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main