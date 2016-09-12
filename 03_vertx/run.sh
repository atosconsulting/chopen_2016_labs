#!/usr/bin/env bash
gradle shadowJar && java -jar build/libs/inventory-1.0.0-SNAPSHOT-fat.jar -conf inventory-conf.json
