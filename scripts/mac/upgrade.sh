version=$(<version.txt)
info=$(base64 --decode -i info.txt)
current_jar=diy-workshop-$version-jar-with-dependencies.jar
echo $version
echo $info
echo $current_jar

rm -rf upgrade-version.txt
curl https://github.com/rajajeswanth-capstone/my-diy-workshop/raw/main/src/main/resources/upgrade-version.txt -H 'Authorization: Bearer $info' -L -O
upgrade_version=$(<upgrade-version.txt)

if [ "$upgrade_version" = "" ];
then
	echo "Already up-to-date"
fi

if [ "$upgrade_version" = "$version" ];
then
	echo "Already up-to-date"
else
	echo "Upgrading to $upgrade_version"
	upgrade_directory=diy-workshop-$upgrade_version-release
	upgrade_file=diy-workshop-$upgrade_version-release.zip
	upgrade_jar=diy-workshop-$upgrade_version-jar-with-dependencies.jar
	echo $upgrade_directory
	echo $upgrade_file
	rm -rf $upgrade_directory
	upgrade_url=https://maven.pkg.github.com/rajajeswanth-capstone/my-diy-workshop/com/doityourself/workshop/diy-workshop/$upgrade_version/diy-workshop-$upgrade_version-release.zip
	echo $upgrade_url
	curl '$upgrade_url' -H "Authorization: Bearer $info" -L -O
	unzip $upgrade_file
	cp $upgrade_directory/$upgrade_jar .
	rm -rf $current_jar
	rm -rf $upgrade_directory
	rm -rf version.txt
	mv upgrade-version.txt version.txt
	echo "My DIY Workshop upgraded successfully to $upgrade_version"
fi