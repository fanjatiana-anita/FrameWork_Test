CLASSPATH="Test/lib/fw.jar:Test/lib/servlet-api.jar"
javac -cp "$CLASSPATH" -d Test/build/WEB-INF/classes $(find Test/src/main/java -name "*.java")


java -cp "Test/build/WEB-INF/classes:$CLASSPATH" main.java.annotations.Main
