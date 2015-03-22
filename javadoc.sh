if [ "$TRAVIS_REPO_SLUG" == "MibacTechnologies/Java-Utils" ] && \
   [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ] && \
   [ "$TRAVIS_BRANCH" == "master" ]; then
  echo -e "Publishing javadoc...\n"
  
  mvn javadoc:aggregate
  TARGET="$(pwd)/target"

  cd $HOME
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/google/truth gh-pages > /dev/null
  
  cd gh-pages
  git config --global user.email "mibac138@wp.pl"
  git config --global user.name "mibac138"
  git rm -rf api/latest 
  mv ${TARGET}/site/apidocs api/latest
  git add -A -f api/latest
  git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"
fi
