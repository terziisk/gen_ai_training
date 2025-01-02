FROM postgres:16

RUN apt-get update && \
    apt-get install -y build-essential postgresql-server-dev-all git && \
    cd /tmp && \
    git clone --branch v0.8.0 https://github.com/pgvector/pgvector.git && \
    cd pgvector && \
    make && \
    make install && \
    rm -rf /tmp/pgvector
