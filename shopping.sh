#/bin/bash
PORT=${1:-8080}
while true; do sleep 1; curl http://localhost:$PORT/produto/1; echo -e; done
