#!/bin/sh -x

XPATHBIN=$(which xpath)

# Chdir to root directory
cd `dirname $(readlink -f $0)`/../

PROJECT=`${XPATHBIN} -q -e "/project/artifactId/text()" pom.xml`
VERSION=`${XPATHBIN} -q -e "/project/version/text()" pom.xml`
MAINCLASS=`${XPATHBIN} -q -e "/project/build/plugins/plugin[artifactId = 'maven-jar-plugin']/configuration/archive/manifest/mainClass/text()" pom.xml`
TARGETDIR=target/${PROJECT}-${VERSION}

mvn clean install assembly:single

# Remove pre-existing target dir
if [ -d "${TARGETDIR}" ]; then
	rm -rf ${TARGETDIR}
fi

mkdir ${TARGETDIR}
for d in changes.xml; do
	cp -r ${d} ${TARGETDIR}/
done

# Create changing data directories
mkdir -p ${TARGETDIR}/bin ${TARGETDIR}/lib

# Copy main jar
cp target/${PROJECT}-${VERSION}-jar-with-dependencies.jar ${TARGETDIR}/lib/

# Create run script
cp bin/jxmllint-dist ${TARGETDIR}/bin/${PROJECT}
chmod a+x ${TARGETDIR}/bin/${PROJECT}
sed -i -e "s/@PROJECT@/${PROJECT}/g" ${TARGETDIR}/bin/${PROJECT}
sed -i -e "s/@VERSION@/${VERSION}/g" ${TARGETDIR}/bin/${PROJECT}
