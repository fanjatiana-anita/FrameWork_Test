CLASSPATH="Test/lib/fw.jar:Test/lib/servlet-api.jar"

javac -cp "$CLASSPATH" -d Test/build/WEB-INF/classes Test/src/main/java/annotations/*.java

java -cp "Test/build/WEB-INF/classes:$CLASSPATH" main.java.annotations.Main
