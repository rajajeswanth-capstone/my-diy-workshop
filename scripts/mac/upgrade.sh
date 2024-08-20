version=0
for file in *.jar; do
    file="${file%-jar-with-dependencies.jar}"
    version="${file:17}"
done
upgrade_version=0.0.$(($version + 1))
echo $upgrade_version
upgrade_directory=diy-workshop-$upgrade_version-release
upgrade_file=diy-workshop-$upgrade_version-release.zip
echo $upgrade_directory
url=https://github.com/rajajeswanth-capstone/my-diy-workshop/blob/main/releases/diy-workshop-$upgrade_version-release.zip
if curl --output /dev/null --silent --head --fail "$url"; then
  curl -O $url
  unzip $upgrade_file
  rm -rf *.jar
  cp $upgrade_directory/$upgrade_file .
  rm -rf $upgrade_directory
else
  echo "Already up-to-date"
fi