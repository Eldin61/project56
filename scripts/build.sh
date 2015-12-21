#!/bin/sh

cd ..
cd ..

sudo rm -rf /var/www/project56
sudo cp project56 /var/www/project56

sudo service apache2 restart