#!/usr/bin/env bash
#
# run.sh - Build & run helper for the stempy-erp multi-module Maven project
#
# Usage:
#   ./run.sh build       Full build (mvn clean install) of all modules
#   ./run.sh run         Run the application (stempy-application module)
#   ./run.sh dev         Quick start: build (skip tests) + run
#   ./run.sh reload      Recompile changed modules (no full clean/tests) + run
#   ./run.sh test        Run tests for all modules
#   ./run.sh clean       mvn clean across all modules
#
set -euo pipefail

APP_MODULE="stempy-application"

cd "$(dirname "$0")"

build_full() {
    echo ">> Full build (clean install, with tests)"
    mvn clean install
}

build_quick() {
    echo ">> Quick build (install, skip tests)"
    mvn install -DskipTests
}

recompile() {
    echo ">> Recompiling changed modules (incremental, skip tests)"
    mvn install -DskipTests -o
}

run_app() {
    echo ">> Compiling ${APP_MODULE} and dependencies"
    mvn -pl "${APP_MODULE}" -am compile -DskipTests -q

    echo ">> Launching ${APP_MODULE}"
    mvn -pl "${APP_MODULE}" exec:java -DskipTests
}

run_tests() {
    echo ">> Running tests"
    mvn test
}

clean_all() {
    echo ">> Cleaning all modules"
    mvn clean
}

case "${1:-}" in
    build)
        build_full
        ;;
    dev)
        build_quick
        run_app
        ;;
    reload)
        recompile
        run_app
        ;;
    run)
        run_app
        ;;
    test)
        run_tests
        ;;
    clean)
        clean_all
        ;;
    *)
        echo "Usage: $0 {build|dev|reload|run|test|clean}"
        echo
        echo "  build   - Full clean install (with tests)"
        echo "  dev     - Quick build (skip tests) + run, for first start"
        echo "  reload  - Incremental recompile (skip tests, offline) + run"
        echo "  run     - Run without rebuilding"
        echo "  test    - Run test suite"
        echo "  clean   - mvn clean"
        exit 1
        ;;
esac