#!/bin/bash

if [ "$1" = '' ]; then
    /usr/lib/tyra/bin/tyra.sh
fi

exec "$@"
