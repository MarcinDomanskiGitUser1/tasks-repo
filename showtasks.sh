#!/usr/bin/env bash

export RUNCRUD_HOME=/Users/marcindomanski/Documents/Development/Projects/tasks


start_runcrud()
{
    $RUNCRUD_HOME/runcrud.sh start
}


open_url(){
  open http://localhost:8181/crud/v1/task/getTasks
}

fail() {
  echo "runcrud.sh is stopped"
}

end() {
  echo "Run this script is successed"
}

if start_runcrud; then
  open_url
   end
else
   fail
fi
