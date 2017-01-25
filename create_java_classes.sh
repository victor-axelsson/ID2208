#!/bin/bash

#This piece of crap doesn't work

##xjc -p companyInfo.xml -d src companyInfo.xsd

cp instances/companyInfo.xml ./
cp schemas/companyInfo.xsd ./
xjc -d src companyInfo.xsd
rm companyInfo.xsd
rm companyInfo.xml