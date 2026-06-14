#!/usr/bin/env bash
#
# run.sh - Build & run helper for the stempy-erp multi-module Maven project
#
# Usage:
#   ./run.sh build       Full build (mvn clean install) of all modules
#   ./run.sh run         Run the application (stempy-application module)
#   ./run.sh dev         Quick start: build (skip tests) + run
#   ./run.sh reload      Recompile changed modules (no full clean/tests) + run
#   ./run.sh watch       Run app, press 'r'+Enter to recompile & restart
#   ./run.sh test        Run tests for all modules
#   ./run.sh clean       mvn clean across all modules
#
set -euo pipefail
set -m  # enable job control so background app runs in its own process group

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

compile_only() {
    echo ">> Compiling ${APP_MODULE} and dependencies"
    mvn -pl "${APP_MODULE}" -am compile -DskipTests -q
}

# Start the app in the background, return its PID via global APP_PID
start_app() {
    echo ">> Launching ${APP_MODULE}"
    mvn -pl "${APP_MODULE}" exec:java -DskipTests &
    APP_PID=$!
}

# Stop the background app process (and its child JVM)
stop_app() {
    if [[ -n "${APP_PID:-}" ]] && kill -0 "${APP_PID}" 2>/dev/null; then
        echo ">> Stopping app (pid ${APP_PID})"
        kill -TERM -- "-${APP_PID}" 2>/dev/null || kill "${APP_PID}" 2>/dev/null
        wait "${APP_PID}" 2>/dev/null || true
    fi
}

watch_mode() {
    compile_only
    start_app

    trap 'stop_app; exit 0' INT TERM

    echo
    echo "Watch mode active. Press 'r' + Enter to recompile & restart, 'q' + Enter to quit."

    while true; do
        if read -r line; then
            case "${line}" in
                r|R)
                    stop_app
                    compile_only
                    start_app
                    echo
                    echo "Watch mode active. Press 'r' + Enter to recompile & restart, 'q' + Enter to quit."
                    ;;
                q|Q)
                    stop_app
                    break
                    ;;
                *)
                    echo "Unknown command '${line}'. Press 'r' to reload, 'q' to quit."
                    ;;
            esac
        fi
    done
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
    watch)
        watch_mode
        ;;
    test)
        run_tests
        ;;
    clean)
        clean_all
        ;;
    *)
        echo "Usage: $0 {build|dev|reload|run|watch|test|clean}"
        echo
        echo "  build   - Full clean install (with tests)"
        echo "  dev     - Quick build (skip tests) + run, for first start"
        echo "  reload  - Incremental recompile (skip tests, offline) + run"
        echo "  run     - Run without rebuilding"
        echo "  watch   - Run in background; press 'r'+Enter to recompile & restart"
        echo "  test    - Run test suite"
        echo "  clean   - mvn clean"
        exit 1
        ;;
esac