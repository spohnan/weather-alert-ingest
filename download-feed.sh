#!/bin/bash

curl http://alerts.weather.gov/cap/us.php?x=0 > $(date -u +"%Y-%m-%dT%H-%M-%SZ".xml)
