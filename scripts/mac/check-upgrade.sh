version=$(<version.txt)
echo $version

rm -rf upgrade-version.txt
curl https://github.com/rajajeswanth-capstone/my-diy-workshop/raw/main/src/main/resources/upgrade-version.txt -L -O
upgrade_version=$(<upgrade-version.txt)

if [ "$upgrade_version" = "$version" ];
then
	echo "Already up-to-date"
else
	echo "Current version is $version"
	echo "Upgrade available to $upgrade_version"
	echo "Follow the instructions on <LINK> to upgrade to $upgrade_version"
fi