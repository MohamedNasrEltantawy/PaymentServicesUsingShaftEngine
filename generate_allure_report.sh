#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd '/Users/tantawy/.m2/repository/allure/allure-2.25.0/bin/'
bash allure serve $parent_path'/allure-results' -h localhost
exit