#!/bin/bash

set -e
set -u

if [ -n "$POSTGRES_DB" ]; then
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --password "$POSTGRES_PASSWORD" -d "$POSTGRES_DB" <<-EOSQL
fi
