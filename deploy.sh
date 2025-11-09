#!/bin/bash
# =============================================
# DEPLOY.SH — COMPILATION UNIQUE + JAVAC GÈRE LES PACKAGES
# =============================================

TOMCAT_WEBAPPS="/home/fanjatiana/apache-tomcat-10.1.28/webapps"
TEST_DIR="Test"
APP_NAME="test_app"
TEST_BUILD="$TEST_DIR/build"
TEST_LIB="$TEST_DIR/lib"
TEST_WEB="$TEST_DIR/src/main/webapp"

CLASSPATH=$(echo $TEST_LIB/*.jar | tr ' ' ':')

echo "=== NETTOYAGE ==="
rm -rf "$TEST_BUILD/WEB-INF/classes" && mkdir -p "$TEST_BUILD/WEB-INF/classes"

echo "=== COMPILATION UNIQUE (javac gère les packages) ==="

# Trouve TOUS les .java (sauf build/lib)
SOURCES=$(find "$TEST_DIR" -name "*.java" ! -path "*/build/*" ! -path "*/lib/*" | tr '\n' ' ')

if [ -z "$SOURCES" ]; then
    echo "Aucun fichier .java trouvé"
    exit 1
fi

# javac compile TOUT en une fois → crée les bons dossiers selon le package
javac -cp "$CLASSPATH" -d "$TEST_BUILD/WEB-INF/classes" $SOURCES

if [ $? -ne 0 ]; then
    echo "ERREUR COMPILATION"
    exit 1
fi

echo "TOUS les .class sont dans les bons dossiers selon leur package"

# WAR
cp -r "$TEST_WEB"/* "$TEST_BUILD/" 2>/dev/null || true
mkdir -p "$TEST_BUILD/WEB-INF/lib"
cp "$TEST_LIB"/*.jar "$TEST_BUILD/WEB-INF/lib/"

cd "$TEST_BUILD"
jar -cvf "$APP_NAME.war" * >/dev/null
cd ../..
cp -f "$TEST_BUILD/$APP_NAME.war" "$TOMCAT_WEBAPPS/"

echo ""
echo "DÉPLOIEMENT RÉUSSI !"
echo "http://localhost:8080/$APP_NAME/"
echo ""