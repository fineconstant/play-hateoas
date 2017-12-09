#!/usr/bin/env bash

#git tag -a <tag> <commit-hash> -m "message here"
#git push origin <tag>

git add .
git commit -m "Version 0.1"
git tag v0.1 # or any other text
git push origin master # push the commit
git push --tags origin # push the tags