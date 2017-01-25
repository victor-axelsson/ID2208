#!/bin/bash

#This piece of crap doesn't work


rm -r src/*
cp instances/companyInfo.xml ./
cp schemas/companyInfo.xsd ./
xjc -p companyInfo.xml -d src companyInfo.xsd
rm companyInfo.xsd
rm companyInfo.xml