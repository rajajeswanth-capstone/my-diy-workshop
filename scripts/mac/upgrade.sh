version=0
for file in *.jar; do
    file="${file%-jar-with-dependencies.jar}"
    version="${file:17}"
done
upgrade_version=0.0.$(($version + 1))
echo $upgrade_version