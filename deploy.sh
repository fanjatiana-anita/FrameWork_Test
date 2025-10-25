        #!/bin/bash

        # ============================
        # Script global de déploiement
        # ============================

        # === Variables communes ===
        TOMCAT_WEBAPPS="/home/fanjatiana/apache-tomcat-10.1.28/webapps"

        # === Variables FrameWork (framework) ===
        FW_DIR="FrameWork"
        FW_SRC="$FW_DIR/src/main/java"
        FW_BUILD="$FW_DIR/build"
        FW_JAR="fw.jar"

        # === Variables Test (application) ===
        TEST_DIR="Test"
        APP_NAME="test_app"
        TEST_SRC="$TEST_DIR/src/main/java"
        TEST_WEB="$TEST_DIR/src/main/webapp"
        TEST_BUILD="$TEST_DIR/build"
        TEST_LIB="$TEST_DIR/lib"

        # Construire le classpath pour FrameWork (juste servlet-api)
        FW_CLASSPATH="$TEST_LIB/servlet-api.jar"

        # Construire le classpath pour Test (tous les jars dans lib)
        CLASSPATH=$(echo $TEST_LIB/*.jar | tr ' ' ':')

        echo "=== Étape 1 : Compilation du Framework (FrameWork) ==="

        # Nettoyage et compilation FrameWork
        rm -rf "$FW_BUILD"
        mkdir -p "$FW_BUILD"
        javac -cp "$FW_CLASSPATH" -d "$FW_BUILD" $(find "$FW_SRC" -name "*.java")

        if [ $? -ne 0 ]; then
            echo "Erreur de compilation FrameWork"
            exit 1
        fi

        # Génération du jar FrameWork
        cd "$FW_BUILD" || exit
        jar cvf "$FW_JAR" $(find . -name "*.class")
        cd ../..

        # Copier fw.jar dans Test/lib
        mv "$FW_BUILD/$FW_JAR" "$TEST_LIB/"
        echo " Framework compilé et copié dans $TEST_LIB/$FW_JAR"

        # Supprimer le build FrameWork (on garde seulement src dans FrameWork)
        rm -rf "$FW_BUILD"
        echo "Dossier build supprimé dans FrameWork (FrameWork ne contient que le code source)"

        echo "=== Étape 2 : Compilation du projet Test ==="

        # Nettoyage et compilation Test
        rm -rf "$TEST_BUILD"
        mkdir -p "$TEST_BUILD/WEB-INF/classes"

        if [ -d "$TEST_SRC" ]; then
            find "$TEST_SRC" -name "*.java" > sources.txt
            if [ -s sources.txt ]; then
                javac -cp "$CLASSPATH" -d "$TEST_BUILD/WEB-INF/classes" @sources.txt
            fi
            rm sources.txt
        fi

        # Copier les fichiers web (web.xml, JSP, HTML…)
        cp -r "$TEST_WEB"/* "$TEST_BUILD/"

        # Copier les .jar dans WEB-INF/lib
        mkdir -p "$TEST_BUILD/WEB-INF/lib"
        cp "$TEST_LIB"/*.jar "$TEST_BUILD/WEB-INF/lib/"

        echo "Application Test compilée"


        echo "=== Étape 3 : Génération du WAR et déploiement ==="

        cd "$TEST_BUILD" || exit
        jar -cvf "$APP_NAME.war" *
        cd ../..

        # Déploiement dans Tomcat
        cp -f "$TEST_BUILD/$APP_NAME.war" "$TOMCAT_WEBAPPS/"

        echo ""
        echo "Déploiement terminé → $TOMCAT_WEBAPPS/$APP_NAME.war"
        echo "Accédez à : http://localhost:8080/$APP_NAME/"
        echo ""
